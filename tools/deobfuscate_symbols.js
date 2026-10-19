const fs = require('fs');
const path = require('path');

const SRC = path.resolve('src/main/java');

// High-confidence, verified deobfuscation mappings for methods and fields
const DEOBF_MAP = {
  // === ClientModule & ToggleableModule ===
  'method00382': 'toggle',
  'method00276': 'onEnable',
  'method00245': 'onDisable',
  'method03937': 'isEnabled',
  'method03939': 'setEnabled',
  'method04700': 'enable',
  'method04702': 'disable',
  'method04703': 'isEnabledCheck',
  'method03925': 'getName',
  'method03935': 'setName',
  'method03926': 'getDescription',
  'method03931': 'setDescription',
  'method03938': 'getCategory',
  'method03940': 'getSubCategory',
  'method03026': 'getSettings',
  'method03932': 'getSettingsList',
  'method03930': 'getBinding',
  'method03922': 'setBinding',
  'method03927': 'getAnimation',
  'method03928': 'isToggleable',
  'method03929': 'setToggleable',
  'method03936': 'isBindable',
  'method03933': 'setBindable',

  // === Setting base & implementations ===
  'method03022': 'getValue',
  'method03014': 'setValue',
  'method03017': 'setDefaultValue',
  'method03015': 'getDefaultValue',
  'method03023': 'setContainer',
  'method03024': 'getName',
  'method03016': 'setId',
  'method03009': 'getId',
  'method03008': 'getDisplayName',
  'method03018': 'setDescription',
  'method03012': 'getDescription',
  'method03025': 'setVisibleSupplier',
  'method03019': 'isVisible',
  'method03013': 'getVisibleSupplier',
  'method03011': 'getContainer',
  'method00869': 'reset',

  // === ToggleSetting ===
  'method05999': 'isEnabled',
  'method05996': 'toggle',
  'method05997': 'getBinding',
  'method05998': 'getAnimation',

  // === Event base ===
  'method05696': 'cancel',
  'method05695': 'setCancelled',
  'method05697': 'isCancelled',
  'method03208': 'post',

  // === EventBus ===
  'method00263': 'register',
  'method00265': 'unregister',
  'method00259': 'getListeners',

  // === AbstractCommand ===
  'method00273': 'build',
  'method01188': 'literal',
  'method01184': 'argument',
  'method01187': 'sendMessage',
  'method01189': 'getInfo',
  'method01185': 'getHelp',

  // === SmoothAnimation ===
  'method06159': 'getValue',
  'method06161': 'getTarget',
  'method06168': 'animate',
  'method06155': 'setValue',
  'method06152': 'isAnimating',
  'method06162': 'update',
  'method06173': 'getValueFloat',
  'method06169': 'getPreviousFloat',

  // === SettingComponent ===
  'method10977': 'getSetting',
  'method10979': 'getAnimation',

  // === Core Client fields ===
  'field08768': 'settings',
  'field08769': 'toggleable',
  'field08770': 'bindable',
  'field08771': 'name',
  'field08772': 'description',
  'field08773': 'enabled',
  'field08774': 'keyBinding',
  'field08775': 'subCategory',
  'field08776': 'category',
  'field08777': 'animation',
  
  // Setting fields
  'field06833': 'name',
  'field06834': 'id',
  'field06835': 'description',
  'field06836': 'container',
  'field06837': 'value',
  'field06838': 'defaultValue',
  'field06839': 'visibleSupplier',

  // Event fields
  'field12064': 'cancelled',
  'field12065': 'listenerCache',

  // Command fields
  'field02515': 'info'
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
        // Use word boundary to avoid partial replacements
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
console.log(`Deobfuscation pass complete! Modified ${modifiedFiles} files, replaced ${totalReplaced} tokens.`);
