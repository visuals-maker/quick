const fs = require('fs');
const p = 'src/main/java/hex/api/baritone/api/command/exception/CommandTooManyArgumentsException.java';
let src = fs.readFileSync(p, 'utf8');
src = src.replace(/(private static final String [^;\r\n=]+);(\r?\n)/, '$1 = "Too many arguments, expected at most %d";$2');
fs.writeFileSync(p, src, 'utf8');
console.log(JSON.stringify(src.split('\n')[7]));
