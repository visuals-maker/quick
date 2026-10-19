const fs = require('fs');
let qc = fs.readFileSync('src/main/java/sg/ec/QuickClient.java', 'utf8');
qc = qc.replace('field01755 = "dl\u0435\u0430mixd";', 'field01755 = "wtf_g";');
fs.writeFileSync('src/main/java/sg/ec/QuickClient.java', qc, 'utf8');
console.log('Done');
