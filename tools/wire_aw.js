const fs = require('fs');
let b = fs.readFileSync('build.gradle', 'utf8');
if (!b.includes('accessWidenerPath')) {
  b = b.replace("loom {\n\tmods {", "loom {\n\taccessWidenerPath = file('src/main/resources/quickclient.accesswidener')\n\tmods {");
  fs.writeFileSync('build.gradle', b);
  console.log('build.gradle updated');
} else {
  console.log('aw already present');
}
let w = fs.readFileSync('src/main/java/sg/ec/WorldHelper.java', 'utf8');
w = w.replace('import net.minecraft.world.BossBarColor;', 'import net.minecraft.world.BossEvent.BossBarColor;');
fs.writeFileSync('src/main/java/sg/ec/WorldHelper.java', w);
console.log('WorldHelper import fixed');
