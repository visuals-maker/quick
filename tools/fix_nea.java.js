const fs = require('fs');
const p = 'src/main/java/hex/api/baritone/api/command/exception/CommandNotEnoughArgumentsException.java';
let src = fs.readFileSync(p, 'utf8');
src = src.replace(/(private static final String [^;\r\n=]+);(\r?\n)/, '$1 = "Not enough arguments, expected at least %d";$2');
fs.writeFileSync(p, src, 'utf8');
const ls = src.split('\n');
console.log(JSON.stringify(ls[7]));
