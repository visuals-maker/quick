const fs = require('fs');
const path = require('path');

const SRC = path.resolve('src/main/java');
const SG_EC = path.join(SRC, 'sg', 'ec');

// 1. Gather safe targets to relocate
const files = fs.readdirSync(SG_EC).filter(f => f.endsWith('.java'));

const commands = files.filter(f => f.endsWith('Command.java'));
const settings = files.filter(f => f.endsWith('Setting.java') && f !== 'Setting.java');

let modules = [];
for (const f of files) {
  const content = fs.readFileSync(path.join(SG_EC, f), 'utf8');
  const m = content.match(/super\(ModuleCategory\.(\w+)/);
  if (m) {
    if (f !== 'Particles.java' && f !== 'Timer.java' && f !== 'Hud.java') {
      modules.push({ file: f, category: m[1].toLowerCase() });
    }
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

console.log(`Relocating ${movedFiles.size} safe classes into structured packages...`);

// 2. Ensure directories exist
for (const info of movedFiles.values()) {
  fs.mkdirSync(path.dirname(info.newPath), { recursive: true });
}

// 3. Move files and update their package declarations
for (const [cls, info] of movedFiles.entries()) {
  let content = fs.readFileSync(info.oldPath, 'utf8');
  content = content.replace(/package\s+sg\.ec;/, `package ${info.newPkg};\n\nimport sg.ec.*;`);

  if (!content.includes('public class ' + cls) && !content.includes('public abstract class ' + cls) && !content.includes('public interface ' + cls) && !content.includes('public final class ' + cls)) {
    content = content.replace(new RegExp(`(class|interface)\\s+${cls}\\b`), `public $1 ${cls}`);
  }

  fs.writeFileSync(info.newPath, content, 'utf8');
  fs.unlinkSync(info.oldPath);
}

// 4. Update import statements only where classes are actually referenced
function updateImports(dir) {
  for (const item of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, item.name);
    if (item.isDirectory()) {
      updateImports(full);
    } else if (item.name.endsWith('.java')) {
      const rel = path.relative(SRC, full).replace(/\\/g, '/');
      if (rel.startsWith('hex/script/api/')) continue; // clean script API intact

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
console.log('Restructure applied successfully!');
