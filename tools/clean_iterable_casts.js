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
let n = 0;
for (const f of walk('src/main/java')) {
  const s = fs.readFileSync(f, 'utf8');
  const o = s.replace(/\(Iterable<([A-Za-z_$\][\w.$.]*(?:<[^>]*>)?) [A-Za-z_$][\w$]*>\)/g, '(Iterable<$1>)');
  if (o !== s) { fs.writeFileSync(f, o); n++; }
}
console.log('files fixed: ' + n);
