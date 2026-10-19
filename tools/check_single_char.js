const fs = require('fs');
const path = require('path');
function getAllFiles(dir, exts = ['.java']) {
    let results = [];
    const list = fs.readdirSync(dir);
    for (const file of list) {
        const full = path.join(dir, file);
        const stat = fs.statSync(full);
        if (stat.isDirectory()) results = results.concat(getAllFiles(full, exts));
        else if (exts.some(ext => file.endsWith(ext))) results.push(full);
    }
    return results;
}
const files = getAllFiles('src/main/java');
for (const f of files) {
    const c = fs.readFileSync(f, 'utf8');
    if (c.includes('"а"') || c.includes('"л"')) {
        const lines = c.split('\n');
        for (let i = 0; i < lines.length; i++) {
            if (lines[i].includes('"а"') || lines[i].includes('"л"')) {
                console.log(path.basename(f) + ':' + (i+1) + ': ' + lines[i].trim());
            }
        }
    }
}
