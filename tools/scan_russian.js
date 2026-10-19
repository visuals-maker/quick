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

// Check for Cyrillic and Mojibake (Cp1252 interpreted as UTF-8 / double decoded)
// Standard Russian Cyrillic: \u0400-\u04FF
// Common mojibake in CFR/Fernflower: strings containing multiple 'Ð', 'Ñ', 'D', ''
const files = getAllFiles('src/main/java');
console.log('Total Java files:', files.length);

let cyrillicFiles = [];
let mojibakeFiles = [];

const cyrillicRegex = /[\u0400-\u04FF]/;
// Detect patterns of mojibake typical of UTF-8 decoded as CP1251 or CP1252:
// e.g. "D'D,D_D" or "Ð’ÐºÐ»"
const mojibakeRegex = /(?:[ÐÑ][\x80-\xBF]){2,}|(?:D[0-9a-zA-Z\?]{1,2}){3,}/;

for (const f of files) {
    const content = fs.readFileSync(f, 'utf8');
    if (cyrillicRegex.test(content)) {
        cyrillicFiles.push(f);
    }
    if (mojibakeRegex.test(content)) {
        mojibakeFiles.push(f);
    }
}

console.log('Files with Cyrillic characters:', cyrillicFiles.length);
console.log('Files with suspected Mojibake:', mojibakeFiles.length);

// Sample strings from mojibakeFiles
let samples = [];
for (const f of mojibakeFiles.slice(0, 10)) {
    const content = fs.readFileSync(f, 'utf8');
    const strMatches = content.match(/"([^"\\]|\\.)*"/g) || [];
    for (const s of strMatches) {
        if (mojibakeRegex.test(s) || cyrillicRegex.test(s)) {
            samples.push({ file: path.basename(f), str: s });
            if (samples.length >= 20) break;
        }
    }
    if (samples.length >= 20) break;
}
console.log('Samples:', JSON.stringify(samples, null, 2));
