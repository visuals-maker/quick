const fs = require('fs');
const path = require('path');
const lines = fs.readFileSync('err_triage.txt', 'utf8').split(/\r?\n/).filter(Boolean);
const byFile = new Map();
for (const line of lines) {
  const parts = line.split(' | ');
  if (parts.length < 3) continue;
  const m = parts[1].match(/^(.+?):(\d+)$/);
  if (!m) continue;
  if (!byFile.has(m[1])) byFile.set(m[1], []);
  byFile.get(m[1]).push(m[2] + ' :: ' + parts[0] + ' :: ' + parts[2]);
}
const outDir = 'tools/errs';
fs.rmSync(outDir, { recursive: true, force: true });
fs.mkdirSync(outDir, { recursive: true });
const index = [];
for (const [file, errs] of byFile) {
  const base = file.replace(/\.java$/, '');
  const name = base + '.txt';
  fs.writeFileSync(path.join(outDir, name), errs.join('\n'), 'utf8');
  index.push({ file, base, count: errs.length });
}
index.sort((a, b) => b.count - a.count);
fs.writeFileSync(path.join(outDir, '_index.txt'), index.map(i => i.count + ' ' + i.file).join('\n'), 'utf8');
console.log('files: ' + index.length + ', errors: ' + lines.length);
