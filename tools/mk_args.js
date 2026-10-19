const fs = require('fs'), path = require('path');
const cpRaw = fs.readFileSync('tools/cp.txt', 'utf8').split(/\r?\n/);
const i1 = cpRaw.findIndex(l => l.trim() === 'CLASSPATH_START');
const i2 = cpRaw.findIndex(l => l.trim() === 'CLASSPATH_END');
const cp = cpRaw.slice(i1 + 1, i2).join('').trim();
const files = [];
function walk(d) {
  for (const e of fs.readdirSync(d, { withFileTypes: true })) {
    const p = path.join(d, e.name).replace(/\\/g, '/');
    if (e.isDirectory()) walk(p);
    else if (e.name.endsWith('.java')) files.push(p);
  }
}
walk('src/main/java');
const args = ['-proc:none', '-nowarn', '-Xmaxerrs', '100000', '-d', 'tools/out', '-cp', '"' + cp + '"'].concat(files);
fs.writeFileSync('tools/javac_args.txt', args.join('\n'), 'utf8');
console.log('files: ' + files.length);
