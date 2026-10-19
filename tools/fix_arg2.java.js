const fs = require('fs');
const p = 'src/main/java/hex/api/baritone/api/command/exception/CommandInvalidArgumentException.java';
let src = fs.readFileSync(p, 'utf8');
src = src.split(';\r = "').join(' = "');
fs.writeFileSync(p, src, 'utf8');
const ls = src.split('\n');
console.log(JSON.stringify(ls[9])); console.log(JSON.stringify(ls[10]));
