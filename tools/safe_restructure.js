const fs = require('fs');
const path = require('path');

const SRC = path.resolve('src/main/java');
const SG_EC = path.join(SRC, 'sg', 'ec');

// 1. Rename ambiguous classes across whole codebase first
const renames = {
  'Hud': 'HudModule',
  'Particles': 'ParticlesModule',
  'Timer': 'TimerModule',
  'Setting': 'ClientSetting'
};

function renameIdentifiersInAllFiles(map) {
  function walk(dir) {
    for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
      const full = path.join(dir, item.name);
      if (item.isDirectory()) {
        walk(full);
      } else if (item.name.endsWith('.java')) {
        // Don't rename inside hex.script.api
        const rel = path.relative(SRC, full).replace(/\\/g, '/');
        if (rel.startsWith('hex/script/api/')) continue;
        
        let content = fs.readFileSync(full, 'utf8');
        let changed = false;

        for (const [oldName, newName] of Object.entries(map)) {
          // If this file is the class file itself
          if (item.name === `${oldName}.java` && rel.startsWith('sg/ec/')) {
            continue;
          }
          // Replace identifier references
          const reg = new RegExp(`\\b${oldName}\\b`, 'g');
          if (reg.test(content)) {
            // Be careful not to replace hex.script.api.Setting or net.minecraft.client.DeltaTracker.Timer
            // or net.minecraft.client.gui.Hud
            content = content.replace(reg, (match, offset, str) => {
              // check prefix
              const prev20 = str.slice(Math.max(0, offset - 25), offset);
              if (prev20.includes('net.minecraft.') || prev20.includes('hex.script.api.') || prev20.includes('DeltaTracker.')) {
                return match;
              }
              return newName;
            });
            changed = true;
          }
        }

        if (changed) {
          fs.writeFileSync(full, content, 'utf8');
        }
      }
    }
  }
  walk(SRC);
}

// Rename the 4 files and their class declarations in sg/ec
for (const [oldName, newName] of Object.entries(renames)) {
  const oldFile = path.join(SG_EC, `${oldName}.java`);
  const newFile = path.join(SG_EC, `${newName}.java`);
  if (fs.existsSync(oldFile)) {
    let content = fs.readFileSync(oldFile, 'utf8');
    content = content.replace(new RegExp(`\\b${oldName}\\b`, 'g'), newName);
    fs.writeFileSync(newFile, content, 'utf8');
    fs.unlinkSync(oldFile);
  }
}

renameIdentifiersInAllFiles(renames);
console.log('Renamed ambiguous classes (HudModule, ParticlesModule, TimerModule, ClientSetting).');

// 2. Identify all targets to relocate
const files = fs.readdirSync(SG_EC).filter(f => f.endsWith('.java'));

const commands = files.filter(f => f.endsWith('Command.java'));
const settings = files.filter(f => f.endsWith('Setting.java'));

let modules = [];
for (const f of files) {
  const content = fs.readFileSync(path.join(SG_EC, f), 'utf8');
  const m = content.match(/super\(ModuleCategory\.(\w+)/);
  if (m) {
    modules.push({ file: f, category: m[1].toLowerCase() });
  }
}

const classToPkg = new Map();
const movedFiles = new Map();

commands.forEach(f => {
  const cls = f.replace('.java', '');
  classToPkg.set(cls, 'quick.command');
  movedFiles.set(cls, {
    oldPath: path.join(SG_EC, f),
    newPath: path.join(SRC, 'quick', 'command', f),
    newPkg: 'quick.command'
  });
});

settings.forEach(f => {
  const cls = f.replace('.java', '');
  classToPkg.set(cls, 'quick.setting');
  movedFiles.set(cls, {
    oldPath: path.join(SG_EC, f),
    newPath: path.join(SRC, 'quick', 'setting', f),
    newPkg: 'quick.setting'
  });
});

modules.forEach(m => {
  const cls = m.file.replace('.java', '');
  const pkg = 'quick.module.' + m.category;
  classToPkg.set(cls, pkg);
  movedFiles.set(cls, {
    oldPath: path.join(SG_EC, m.file),
    newPath: path.join(SRC, 'quick', 'module', m.category, m.file),
    newPkg: pkg
  });
});

console.log(`Relocating ${movedFiles.size} classes into quick.* subpackages...`);

// 3. Move files
for (const [cls, info] of movedFiles.entries()) {
  fs.mkdirSync(path.dirname(info.newPath), { recursive: true });
  let content = fs.readFileSync(info.oldPath, 'utf8');
  content = content.replace(/package\s+sg\.ec;/, `package ${info.newPkg};\n\nimport sg.ec.*;`);
  
  if (!content.includes('public class ' + cls) && !content.includes('public abstract class ' + cls) && !content.includes('public interface ' + cls) && !content.includes('public final class ' + cls)) {
    content = content.replace(new RegExp(`(class|interface)\\s+${cls}\\b`), `public $1 ${cls}`);
  }

  fs.writeFileSync(info.newPath, content, 'utf8');
  fs.unlinkSync(info.oldPath);
}

// 4. Update imports
function updateImports(dir) {
  for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, item.name);
    if (item.isDirectory()) {
      updateImports(full);
    } else if (item.name.endsWith('.java')) {
      const rel = path.relative(SRC, full).replace(/\\/g, '/');
      if (rel.startsWith('hex/script/api/')) continue; // leave clean script API intact

      let content = fs.readFileSync(full, 'utf8');
      const currentPkgMatch = content.match(/package\s+([\w\.]+);/);
      const currentPkg = currentPkgMatch ? currentPkgMatch[1] : '';

      const importsToAdd = new Set();
      for (const [cls, pkg] of classToPkg.entries()) {
        if (pkg !== currentPkg) {
          const regex = new RegExp(`\\b${cls}\\b`);
          if (regex.test(content) && !content.includes(`import ${pkg}.${cls};`)) {
            importsToAdd.add(`import ${pkg}.${cls};`);
          }
        }
      }

      if (importsToAdd.size > 0) {
        const importBlock = Array.from(importsToAdd).join('\n') + '\n';
        content = content.replace(/(package\s+[\w\.]+;\s*)/, `$1\n${importBlock}`);
        fs.writeFileSync(full, content, 'utf8');
      }
    }
  }
}
updateImports(SRC);
console.log('Restructure complete!');
