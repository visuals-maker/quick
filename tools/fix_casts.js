const fs = require('fs');
const path = require('path');

const triage = fs.readFileSync('err_triage.txt', 'utf8').split(/\r?\n/).filter(Boolean);
const byFile = new Map();

for (const line of triage) {
  const parts = line.split(' | ');
  if (parts.length < 3) continue;
  const msg = parts[0];
  const loc = parts[1];
  const srcText = parts[2];
  const m = loc.match(/^(.+?):(\d+)$/);
  if (!m) continue;
  const key = m[1];
  if (!byFile.has(key)) byFile.set(key, []);
  byFile.get(key).push({ msg, n: +m[2], srcText });
}

function findFile(name) {
  const roots = ['src/main/java'];
  const stack = ['src/main/java'];
  while (stack.length) {
    const dir = stack.pop();
    for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
      const p = path.join(dir, e.name);
      if (e.isDirectory()) stack.push(p);
      else if (e.name === name) return p;
    }
  }
  return null;
}

let fixedFor = 0;
let fixedAssign = 0;
let fixedToArray = 0;
let fixedReturnCast = 0;

for (const [fileName, errs] of byFile) {
  const fp = findFile(fileName);
  if (!fp) continue;
  const lines = fs.readFileSync(fp, 'utf8').split(/\r?\n/);
  let dirty = false;
  for (const er of errs) {
    const idx = er.n - 1;
    if (idx < 0 || idx >= lines.length) continue;
    const raw = lines[idx];
    const cur = raw.trim();

    if (er.msg.startsWith('incompatible types: Object') && cur.startsWith('for (')) {
      const m = cur.match(/^for \(\s*([^:]+?)\s*:\s*(.+)\)\s*\{?\s*$/);
      if (m) {
        const type = m[1].replace(/\bvar\d+\b/g, '').trim();
        const expr = m[2].trim();
        const t = type === 'Entry' ? 'java.util.Map.Entry' : type;
        if (!expr.startsWith('(Iterable')) {
          const indent = raw.match(/^\s*/)[0];
          lines[idx] = indent + cur.replace(' : ' + expr + ')', ' : (Iterable<' + t + '>)' + expr + ')');
          fixedFor++;
          dirty = true;
        }
      }
      continue;
    }

    if (er.msg.startsWith('incompatible types:')) {
      const mm = er.msg.match(/incompatible types: (.+?) cannot be converted to (.+?)$/);
      if (mm) {
        const target = mm[2].trim();
        if (/^Object/.test(mm[1]) || /Object$/.test(mm[1])) {
          let m2 = cur.match(/^([A-Za-z_$][\w.$<>\[\], ]*?)\s+(var\d+|sx+\w*|\w+)\s*=\s*(.+);$/);
          if (m2 && m2[1].trim() === target.replace(/\s+/g, ' ').trim()) {
            const indent = raw.match(/^\s*/)[0];
            lines[idx] = indent + m2[1] + ' ' + m2[2] + ' = (' + target + ')' + m2[3] + ';';
            fixedAssign++;
            dirty = true;
            continue;
          }
          let m3 = cur.match(/^return (.+);$/);
          if (m3) {
            const indent = raw.match(/^\s*/)[0];
            lines[idx] = indent + 'return (' + target + ')' + m3[1] + ';';
            fixedReturnCast++;
            dirty = true;
            continue;
          }
        }
      }
    }

    if (er.msg.includes('Object[]') && er.msg.includes('cannot be converted to')) {
      const mm = er.msg.match(/cannot be converted to (\S+)/);
      if (mm && cur.startsWith('return ') && cur.includes('.toArray(')) {
        const indent = raw.match(/^\s*/)[0];
        lines[idx] = indent + 'return (' + mm[1] + ')' + cur.slice(7);
        fixedToArray++;
        dirty = true;
        continue;
      }
    }
  }
  if (dirty) fs.writeFileSync(fp, lines.join('\n'));
}

console.log('for-casts: ' + fixedFor);
console.log('assign-casts: ' + fixedAssign);
console.log('return-casts: ' + fixedReturnCast);
console.log('toArray-casts: ' + fixedToArray);
