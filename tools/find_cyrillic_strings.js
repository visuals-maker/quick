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
let foundStrings = [];

for (const f of files) {
    const content = fs.readFileSync(f, 'utf8');
    const re = /"([^"\\]|\\.)*"/g;
    let m;
    while ((m = re.exec(content)) !== null) {
        const str = m[0];
        // Test for Cyrillic characters
        if (/[\u0400-\u04FF]/.test(str)) {
            foundStrings.push({ file: path.relative('.', f), str: str });
        }
    }
}

console.log('Total string literals with Cyrillic:', foundStrings.length);
console.log('Sample 20:');
for (const item of foundStrings.slice(0, 20)) {
    console.log(item.file + ': ' + item.str);
}
