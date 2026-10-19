const fs = require('fs');

function fix(f, pairs) {
  let s = fs.readFileSync(f, 'utf8');
  for (const [from, to] of pairs) {
    const before = s;
    s = s.split(from).join(to);
    if (s === before) console.log('NO-MATCH in ' + f + ': ' + JSON.stringify(from.slice(0, 60)));
  }
  fs.writeFileSync(f, s);
  console.log('done ' + f);
}

fix('src/main/java/hex/api/baritone/api/utils/SettingsUtil.java', [
  ['var2x))(String).collect', 'var2x)).collect'],
  ['\n               (String).collect(', '\n               .collect(']
]);
fix('src/main/java/sg/ec/CommandManager.java', [
  ['catch ((String)Throwable var4)', 'catch (Throwable var4)'],
  ['(List)ChatTextUtils.method05086', 'ChatTextUtils.method05086']
]);
fix('src/main/java/hex/api/baritone/process/ExploreProcess.java', [
  ['(var0.getX(), var0.getZ()))(Goal[]).toArray', '(var0.getX(), var0.getZ())).toArray']
]);
