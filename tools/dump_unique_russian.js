const fs = require('fs');
const path = require('path');

function getAllFiles(dir, exts = ['.java']) {
    let results = [];
    const list = fs.readdirSync(dir);
    for (const file of list) {
        const full = path.join(dir, file);
        const stat = fs.statSync(full);
        if (stat.isDirectory()) {
            results = results.concat(getAllFiles(full, exts));
        } else if (exts.some(ext => file.endsWith(ext))) {
            results.push(full);
        }
    }
    return results;
}

const files = getAllFiles('src/main/java');
let stringMap = new Map(); // str -> count

for (const f of files) {
    const content = fs.readFileSync(f, 'utf8');
    const re = /"([^"\\]|\\.)*"/g;
    let m;
    while ((m = re.exec(content)) !== null) {
        const str = m[0];
        if (/[\u0400-\u04FF]/.test(str)) {
            const raw = JSON.parse(str);
            stringMap.set(raw, (stringMap.get(raw) || 0) + 1);
        }
    }
}

console.log('Unique Cyrillic strings:', stringMap.size);

const sorted = Array.from(stringMap.entries()).sort((a, b) => b[1] - a[1]);
fs.writeFileSync('tools/unique_russian_strings.json', JSON.stringify(sorted, null, 2), 'utf8');
console.log('Saved unique strings to tools/unique_russian_strings.json');
console.log('Top 30 frequent strings:');
for (const [s, count] of sorted.slice(0, 30)) {
    console.log(`${count}x: "${s}"`);
}
