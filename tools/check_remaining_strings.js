const fs = require('fs');
const path = require('path');

const ROOT = path.resolve('src/main/java');
const rusStrings = [];

function walk(dir) {
  for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, item.name);
    if (item.isDirectory()) {
      walk(full);
    } else if (item.name.endsWith('.java') && item.name !== 'BetterChat.java') {
      const content = fs.readFileSync(full, 'utf8');
      const matches = content.match(/"([^"\\]*(?:\\.[^"\\]*)*)"/g);
      if (matches) {
        for (const m of matches) {
          const inner = m.slice(1, -1);
          if (/[\u0400-\u04FF]/.test(inner)) {
            rusStrings.push({ file: path.relative(ROOT, full), str: inner });
          }
        }
      }
    }
  }
}

walk(ROOT);
console.log('Total Russian string literals in Java code:', rusStrings.length);
const multi = rusStrings.filter(x => x.str.trim().length > 1);
console.log('Multi-char Russian string literals:', multi.length);
console.log('First 30 multi-char:');
console.log(multi.slice(0, 30));
