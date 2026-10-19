const fs = require('fs'), path = require('path');
const root = 'src/main/java';
const out = [];
function isIdentChar(c) { return c !== undefined && /[\p{L}\p{N}_$]/u.test(c); }
function walk(d) {
  for (const e of fs.readdirSync(d, { withFileTypes: true })) {
    const p = path.join(d, e.name);
    if (e.isDirectory()) walk(p);
    else if (e.name.endsWith('.java')) {
      const src = fs.readFileSync(p, 'utf8');
      const ls = src.split('\n');
      ls.forEach((l, i) => {
        const m = l.match(/^\s*(?:private|public|protected)?\s*static final ([\w.$<>[\]]+) ([^\s;=]+);\s*\r?$/);
        if (!m) return;
        const tok = m[2];
        let count = 0;
        let idx = -1;
        while ((idx = src.indexOf(tok, idx + 1)) !== -1) {
          if (!isIdentChar(src[idx - 1]) && !isIdentChar(src[idx + tok.length])) count++;
        }
        if (count > 1) out.push(p.replace(/\\/g, '/') + ':' + (i + 1) + ' :: ' + m[1] + ' ' + tok + ' :: ' + count);
      });
    }
  }
}
walk(root);
console.log(out.join('\n'));
console.log('referenced total: ' + out.length);
