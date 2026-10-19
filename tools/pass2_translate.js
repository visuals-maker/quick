const fs = require('fs');
const path = require('path');

const ROOT = path.resolve('src/main/java');

function getRemaining() {
  const map = new Map();
  function walk(dir) {
    for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
      const full = path.join(dir, item.name);
      if (item.isDirectory()) walk(full);
      else if (item.name.endsWith('.java') && item.name !== 'BetterChat.java') {
        const content = fs.readFileSync(full, 'utf8');
        const matches = content.match(/"([^"\\]*(?:\\.[^"\\]*)*)"/g);
        if (matches) {
          for (const m of matches) {
            const inner = m.slice(1, -1);
            if (/[\u0400-\u04FF]/.test(inner) && inner.trim().length > 1) {
              map.set(inner, (map.get(inner) || 0) + 1);
            }
          }
        }
      }
    }
  }
  walk(ROOT);
  return Array.from(map.keys());
}

async function main() {
  const remaining = getRemaining();
  console.log('Remaining composite Russian strings to translate:', remaining.length);
  
  const translations = {};
  for (let i = 0; i < remaining.length; i++) {
    const item = remaining[i];
    try {
      const url = 'https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q=' + encodeURIComponent(item);
      const res = await fetch(url);
      if (res.ok) {
        const data = await res.json();
        const tr = data[0].map(x => x[0]).join('');
        if (tr && tr.trim() !== item.trim()) {
          translations[item] = tr;
        }
      }
    } catch (e) {}
    await new Promise(r => setTimeout(r, 60));
  }

  console.log('Translated composite items:', Object.keys(translations).length);

  const keys = Object.keys(translations).sort((a, b) => b.length - a.length);

  let totalReplaced = 0;
  function apply(dir) {
    for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
      const full = path.join(dir, item.name);
      if (item.isDirectory()) apply(full);
      else if (item.name.endsWith('.java') && item.name !== 'BetterChat.java') {
        let content = fs.readFileSync(full, 'utf8');
        if (!/[\u0400-\u04FF]/.test(content)) continue;
        let changed = false;
        const newContent = content.replace(/"([^"\\]*(?:\\.[^"\\]*)*)"/g, (match, inner) => {
          if (!/[\u0400-\u04FF]/.test(inner)) return match;
          let r = inner;
          for (const k of keys) {
            if (r.includes(k)) {
              r = r.split(k).join(translations[k]);
              totalReplaced++;
              changed = true;
            }
          }
          return '"' + r + '"';
        });
        if (changed) {
          fs.writeFileSync(full, newContent, 'utf8');
        }
      }
    }
  }
  apply(ROOT);
  console.log('Finished pass 2. Total replaced:', totalReplaced);
}

main().catch(console.error);
