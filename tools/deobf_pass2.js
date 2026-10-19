const fs = require('fs');
const path = require('path');

const SRC = path.resolve('src/main/java');

const DEOBF_MAP = {
  // NumberSetting
  'method10834': 'setStep',
  'method10835': 'setMin',
  'method10836': 'getMin',
  'method10837': 'setValueFromFloat',
  'method10838': 'setMax',
  'method10841': 'getMax',
  'method10842': 'getStep',

  // ModeSetting
  'method05597': 'setModes',
  'method05598': 'getModes',
  'method05600': 'isMode',
  'method05601': 'addMode',
  'method05603': 'setMode',

  // MultiToggleSetting
  'method04517': 'addOption',
  'method04518': 'setOptions',
  'method04519': 'setOptionState',
  'method04520': 'isOptionSelected',
  'method04515': 'getOptionsMap',
  'method04514': 'getSelectedCount',

  // NamedOption
  'method08070': 'getDefaultValue',
  'method08073': 'setValue',
  'method08074': 'getValue',
  'method08075': 'isVisible',
  'method08076': 'visibleSupplier',
  'method08077': 'createNamedOption',
  'method08078': 'reset',
  'method08080': 'getName',

  // KeyBinding
  'method05870': 'setKeyMode',
  'method05871': 'getKeyCode',
  'method05872': 'setKeyCode',
  'method05873': 'isPressed',
  'method05874': 'getKeyMode',
  'method05875': 'setPressed',

  // KeyboardKey
  'method00360': 'getKeyName',
  'method00361': 'getKeyCode',
  'method00362': 'isKeyDown',
  'method00363': 'isMouseDown',

  // Stopwatch
  'method02438': 'hasElapsed',
  'method02439': 'resetTimer',
  'method02437': 'getElapsedTime',

  // ActiveConfigTracker
  'method03059': 'isActiveConfig',
  'method03060': 'getActiveConfigName',
  'method03061': 'resetActiveConfig',
  'method03062': 'setActiveConfig',

  // ChatTextUtils
  'method05086': 'sendQuickMessage',
  'method05101': 'sendPrefixMessage',
  'method05096': 'sendComponentMessage',
  'method05080': 'sendQueuedCommand'
};

const keys = Object.keys(DEOBF_MAP).sort((a, b) => b.length - a.length);

let totalReplaced = 0;
let modifiedFiles = 0;

function walk(dir) {
  for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, item.name);
    if (item.isDirectory()) {
      walk(full);
    } else if (item.name.endsWith('.java')) {
      let content = fs.readFileSync(full, 'utf8');
      let changed = false;

      for (const k of keys) {
        const reg = new RegExp(`\\b${k}\\b`, 'g');
        if (reg.test(content)) {
          content = content.replace(reg, DEOBF_MAP[k]);
          totalReplaced++;
          changed = true;
        }
      }

      if (changed) {
        fs.writeFileSync(full, content, 'utf8');
        modifiedFiles++;
      }
    }
  }
}

walk(SRC);
console.log(`Deobfuscation pass 2 complete! Modified ${modifiedFiles} files, replaced ${totalReplaced} tokens.`);
