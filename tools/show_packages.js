const fs = require('fs');
const path = require('path');
const files = [
'src/main/java/sg/ec/AttackAura.java',
'src/main/java/sg/ec/BotClient.java',
'src/main/java/sg/ec/BotConfigurationPacketListener.java',
'src/main/java/sg/ec/BotLoginPacketListener.java',
'src/main/java/sg/ec/BotManager.java',
'src/main/java/sg/ec/BotSessionTransfer.java',
'src/main/java/sg/ec/BotWorldState.java',
'src/main/java/sg/ec/CrystalAura.java',
'src/main/java/sg/ec/WardenBotController.java',
'src/main/java/sg/ec/WardenNavigationTask.java'
];
for (const f of files) {
  const first = fs.readFileSync(f, 'utf8').split(/\r?\n/).slice(0, 5).join('\n');
  console.log('=== ' + f);
  console.log(first);
}
