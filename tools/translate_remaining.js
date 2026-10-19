const fs = require('fs');

async function main() {
  const trans = JSON.parse(fs.readFileSync('tools/translations.json', 'utf8'));
  const identical = Object.keys(trans).filter(k => trans[k] === k && k.trim().length > 1);
  console.log('Identical to translate individually:', identical.length);

  let successCount = 0;
  for (let i = 0; i < identical.length; i++) {
    const item = identical[i];
    try {
      const url = 'https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q=' + encodeURIComponent(item);
      const res = await fetch(url);
      if (res.ok) {
        const data = await res.json();
        const tr = data[0].map(x => x[0]).join('');
        if (tr && tr.trim() !== item.trim()) {
          trans[item] = tr;
          successCount++;
        }
      }
    } catch (err) {
      // ignore
    }

    if ((i + 1) % 50 === 0 || i === identical.length - 1) {
      console.log(`Progress: ${i + 1}/${identical.length} (translated: ${successCount})`);
      fs.writeFileSync('tools/translations.json', JSON.stringify(trans, null, 2), 'utf8');
    }
    // brief rate limit delay
    await new Promise(r => setTimeout(r, 60));
  }
  console.log(`Complete! Total freshly translated: ${successCount}`);
}

main().catch(console.error);
