const fs = require('fs'), path = require('path');
const root = 'src/main/java';
const out = [];
function walk(d) {
  for (const e of fs.readdirSync(d, { withFileTypes: true })) {
    const p = path.join(d, e.name);
    if (e.isDirectory()) walk(p);
    else if (e.name.endsWith('.java')) {
      const ls = fs.readFileSync(p, 'utf8').split('\n');
      ls.forEach((l, i) => {
        const m = l.match(/^\s*(?:private|public|protected)?\s*static final ([\w.$<>[\]]+) ([^\s;=]+);\s*\r?$/);
        if (m) out.push(p.replace(/\\/g, '/') + ':' + (i + 1) + ' :: ' + m[1] + ' ' + m[2]);
      });
    }
  }
}
walk(root);
console.log(out.join('\n'));
console.log('total: ' + out.length);
