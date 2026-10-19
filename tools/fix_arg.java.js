const fs = require('fs');
const p = 'src/main/java/hex/api/baritone/api/command/exception/CommandInvalidArgumentException.java';
const ls = fs.readFileSync(p, 'utf8').split('\n');
const vals = { 10: ' = "Unable to parse argument at position %s: %s";', 11: ' = "?";' };
for (const [k, v] of Object.entries(vals)) {
  const i = +k - 1;
  if (!/private static final String /.test(ls[i]) || /=/.test(ls[i])) { console.log('SKIP ' + k + ': ' + ls[i]); continue; }
  ls[i] = ls[i].replace(/;[ \t]*$/, '') + v;
  console.log('OK ' + k);
}
fs.writeFileSync(p, ls.join('\n'), 'utf8');
