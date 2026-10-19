const fs = require('fs');
const path = require('path');

function walk(dir) {
  let out = [];
  for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
    const p = path.join(dir, e.name);
    if (e.isDirectory()) out = out.concat(walk(p));
    else if (e.name.endsWith('.java')) out.push(p);
  }
  return out;
}
const all = walk('src/main/java');
const byBase = new Map();
for (const f of all) byBase.set(path.basename(f), f);

const raw = fs.readFileSync('build_err.txt', 'utf8').split(/\r?\n/);
const blocks = [];
for (let i = 0; i < raw.length; i++) {
  const m = raw[i].match(/^(?:\.\\gradlew\.bat : )?(C:\\[^:]+\.java):(\d+): error: (.*)$/);
  if (!m) continue;
  const file = m[1], line = +m[2];
  let j = i + 1;
  const cont = [];
  let srcLine = null, caret = null;
  while (j < raw.length) {
    const s = raw[j];
    if (/^(?:\.\\gradlew\.bat : )?C:\\[^:]+\.java:\d+: error:/.test(s)) break;
    if (/^\s*[\^ ]+\s*$/.test(s) && s.includes('^')) { caret = s; break; }
    cont.push(s);
    j++;
  }
  // source line is last non-empty continuation line
  for (let k = cont.length - 1; k >= 0; k--) {
    if (cont[k].trim().length) { srcLine = cont[k]; break; }
  }
  blocks.push({ file, line, msg: cont.join(' '), srcLine: srcLine || '', caret: caret || '' });
  i = j;
}

const fixable = [];
for (const b of blocks) {
  let target = null;
  let isArray = false;
  let mm = b.msg.match(/Object\[\] cannot be converted to ([\w.$<>\[\], ]+?)(?:\s{2,}|$|;)/);
  if (mm) { target = mm[1].trim(); isArray = true; }
  if (!target) {
    mm = b.msg.match(/Object cannot be converted to ([\w.$<>\[\], ]+?)(?:\s{2,}|$|;)/);
    if (mm) target = mm[1].trim();
  }
  if (!target) continue;
  if (!b.caret || !b.srcLine) continue;
  const start = b.caret.indexOf('^');
  let end = b.caret.lastIndexOf('^');
  if (start < 0) continue;
  fixable.push({ ...b, target, start, end });
}

const byFile = new Map();
for (const f of fixable) {
  if (!byFile.has(f.file)) byFile.set(f.file, []);
  byFile.get(f.file).push(f);
}

let applied = 0;
for (const [file, list] of byFile) {
  const fp = byBase.get(path.basename(file));
  if (!fp) continue;
  const lines = fs.readFileSync(fp, 'utf8').split(/\r?\n/);
  list.sort((a, b) => b.start - a.start);
  let changed = false;
  for (const f of list) {
    const idx = f.line - 1;
    if (idx < 0 || idx >= lines.length) continue;
    const text = lines[idx];
    if (f.caret[start0(f)] === undefined) { }
    let s0 = f.start <= text.length ? f.start : text.length;
    let s1 = Math.min(f.end + 1, text.length);
    // expand to token boundaries
    while (s0 > 0 && /[\w.$]/.test(text[s0 - 1])) s0--;
    while (s1 < text.length && /[\w.$]/.test(text[s1])) s1++;
    let span = text.slice(s0, s1);
    if (!span.trim()) continue;
    if (span.startsWith('(')) continue;
    const repl = '(' + f.target + ')' + span;
    lines[idx] = text.slice(0, s0) + repl + text.slice(s1);
    applied++;
    changed = true;
  }
  if (changed) fs.writeFileSync(fp, lines.join('\n'));
}
console.log('casts applied: ' + applied);

function start0() { return 'start'; }
