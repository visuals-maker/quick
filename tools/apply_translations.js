const fs = require('fs');
const path = require('path');

const ROOT = path.resolve('src/main/java');
const trans = JSON.parse(fs.readFileSync('tools/translations.json', 'utf8'));

// Sort keys descending by length to avoid partial substring clashes
const rusKeys = Object.keys(trans)
  .filter(k => k.trim().length > 1 && trans[k] !== k)
  .sort((a, b) => b.length - a.length);

console.log('Active translation keys:', rusKeys.length);

let totalReplaced = 0;
let modifiedFiles = 0;

function walk(dir) {
  for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, item.name);
    if (item.isDirectory()) {
      walk(full);
    } else if (item.name.endsWith('.java') && item.name !== 'BetterChat.java') {
      let content = fs.readFileSync(full, 'utf8');
      if (!/[\u0400-\u04FF]/.test(content)) continue;
      
      let changed = false;
      const newContent = content.replace(/"([^"\\]*(?:\\.[^"\\]*)*)"/g, (match, inner) => {
        if (!/[\u0400-\u04FF]/.test(inner)) return match;
        let replacedInner = inner;
        for (const k of rusKeys) {
          if (replacedInner.includes(k)) {
            replacedInner = replacedInner.split(k).join(trans[k]);
            totalReplaced++;
            changed = true;
          }
        }
        return '"' + replacedInner + '"';
      });

      if (changed) {
        fs.writeFileSync(full, newContent, 'utf8');
        modifiedFiles++;
      }
    }
  }
}

walk(ROOT);
console.log(`Finished applying translations! Modified ${modifiedFiles} files with ${totalReplaced} string replacements.`);
