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
function findFile(name) {
  for (const f of walk('src/main/java')) {
    if (path.basename(f) === name) return f;
  }
  return null;
}
const triage = fs.readFileSync('err_triage.txt', 'utf8').split(/\r?\n/).filter(Boolean);
let fixedFor = 0;
const byFile = new Map();
for (const line of triage) {
  const parts = line.split(' | ');
  if (parts.length < 3) continue;
  const msg = parts[0];
  const m = parts[1].match(/^(.+?):(\d+)$/);
  if (!m) continue;
  if (!msg.startsWith('incompatible types: Object')) continue;
  const cur = parts[2];
  if (!cur.startsWith('for (')) continue;
  const fm = cur.match(/^for \(([^:]+?)\s*:\s*(.+?)\)\s*\{?$/);
  if (!fm) continue;
  const typeAndName = fm[1].trim().split(/\s+/);
  const type = typeAndName[0];
  const expr = fm[2].trim();
  if (expr.startsWith('(Iterable')) continue;
  const target = type === 'Entry' ? 'java.util.Map.Entry' : type;
  if (!byFile.has(m[1])) byFile.set(m[1], []);
  byFile.get(m[1]).push({ n: +m[2], expr, target });
}
for (const [name, list] of byFile) {
  const fp = findFile(name);
  if (!fp) continue;
  const lines = fs.readFileSync(fp, 'utf8').split(/\r?\n/);
  let dirty = false;
  for (const it of list) {
    const idx = it.n - 1;
    const raw = lines[idx];
    const cur = raw.trim();
    const fm = cur.match(/^for \(([^:]+?)\s*:\s*(.+?)\)\s*\{?$/);
    if (!fm) continue;
    const expr = fm[2].trim();
    if (expr.startsWith('(Iterable')) continue;
    const indent = raw.match(/^\s*/)[0];
    lines[idx] = indent + cur.replace(' : ' + expr + ')', ' : (Iterable<' + it.target + '>)' + expr + ')');
    fixedFor++;
    dirty = true;
  }
  if (dirty) fs.writeFileSync(fp, lines.join('\n'));
}
console.log('for-casts fixed: ' + fixedFor);
