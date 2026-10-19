const fs = require('fs');
const targets = [
['src/main/java/sg/ec/AttackAura.java', 977],
['src/main/java/sg/ec/BotClient.java', 237],
['src/main/java/sg/ec/BotClient.java', 455],
['src/main/java/sg/ec/BotClient.java', 482],
['src/main/java/sg/ec/BotClient.java', 681],
['src/main/java/sg/ec/BotClient.java', 697],
['src/main/java/sg/ec/BotClient.java', 759],
['src/main/java/sg/ec/BotClient.java', 1012],
['src/main/java/sg/ec/BotClient.java', 1032],
['src/main/java/sg/ec/BotClient.java', 1068],
['src/main/java/sg/ec/BotClient.java', 1074],
['src/main/java/sg/ec/BotClient.java', 1080],
['src/main/java/sg/ec/BotClient.java', 1086],
['src/main/java/sg/ec/BotClient.java', 1091],
['src/main/java/sg/ec/BotClient.java', 1105],
['src/main/java/sg/ec/BotClient.java', 1106],
['src/main/java/sg/ec/BotClient.java', 1114],
['src/main/java/sg/ec/BotClient.java', 1121],
['src/main/java/sg/ec/BotClient.java', 1200],
['src/main/java/sg/ec/BotConfigurationPacketListener.java', 133],
['src/main/java/sg/ec/BotConfigurationPacketListener.java', 144],
['src/main/java/sg/ec/BotLoginPacketListener.java', 45],
['src/main/java/sg/ec/BotManager.java', 239],
['src/main/java/sg/ec/BotSessionTransfer.java', 84],
['src/main/java/sg/ec/BotWorldState.java', 1322],
['src/main/java/sg/ec/BotWorldState.java', 1495],
['src/main/java/sg/ec/CrystalAura.java', 298],
['src/main/java/sg/ec/CrystalAura.java', 300],
['src/main/java/sg/ec/CrystalAura.java', 1028],
['src/main/java/sg/ec/CrystalAura.java', 1251],
['src/main/java/sg/ec/WardenBotController.java', 2132],
['src/main/java/sg/ec/WardenNavigationTask.java', 214]
];
const byFile = new Map();
for (const [f, n] of targets) {
  if (!byFile.has(f)) byFile.set(f, []);
  byFile.get(f).push(n);
}
for (const [f, nums] of byFile) {
  const lines = fs.readFileSync(f, 'utf8').split(/\r?\n/);
  for (const n of nums) {
    const raw = lines[n - 1];
    const m = raw.match(/^(\s*)(.*?);\s*$/);
    if (!m) { console.log('SKIP ' + f + ':' + n + ' raw=' + raw); continue; }
    const indent = m[1];
    const expr = m[2].trim();
    lines[n - 1] = indent + 'ScriptLogManager.INSTANCE.method08092(0, ' + expr + ');';
  }
  fs.writeFileSync(f, lines.join('\n'));
  console.log('fixed ' + f + ' x' + nums.length);
}
