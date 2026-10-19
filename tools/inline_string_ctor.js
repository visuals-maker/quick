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
const files = walk('src/main/java');
let changed = 0;
for (const f of files) {
  const src = fs.readFileSync(f, 'utf8');
  const out = src.replace(/new String\(\s*("(?:\\.|[^"\\])*")\s*\)/g, '$1');
  if (out !== src) {
    fs.writeFileSync(f, out);
    changed++;
  }
}
console.log('files changed: ' + changed);
