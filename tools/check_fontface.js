const fs = require('fs');
const src = fs.readFileSync('src/main/java/sg/ec/FontFace.java', 'utf8');
const lines = src.split('\n');
lines.forEach((l, i) => {
  if (l.includes(' = 0') || l.includes(' = null') || l.includes(' = ""')) {
    console.log((i+1) + ': ' + l.trim());
  }
});
