const fs = require('fs');

const trans = JSON.parse(fs.readFileSync('tools/translations.json', 'utf8'));
let count = 0;

for (const k of Object.keys(trans)) {
  let v = trans[k];
  // Replace double quotes and guillemets / typographic quotes that break string literal syntax
  if (v.includes('"') || v.includes('«') || v.includes('»') || v.includes('“') || v.includes('”')) {
    v = v.replace(/"/g, "'");
    v = v.replace(/[«»“”]/g, "'");
    trans[k] = v;
    count++;
  }
}

console.log('Sanitized translations with quotes:', count);
fs.writeFileSync('tools/translations.json', JSON.stringify(trans, null, 2), 'utf8');
