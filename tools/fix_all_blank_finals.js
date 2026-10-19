const fs = require('fs'), path = require('path');

let totalFixed = 0;
let filesModified = 0;

function getDefaultValue(type) {
  switch (type) {
    case 'String': return '""';
    case 'int': return '0';
    case 'long': return '0L';
    case 'float': return '0.0F';
    case 'double': return '0.0';
    case 'boolean': return 'false';
    case 'byte': return '(byte) 0';
    case 'short': return '(short) 0';
    case 'char': return '\'\\0\'';
    default: return 'null';
  }
}

function processFile(filePath) {
  let content = fs.readFileSync(filePath, 'utf8');
  let original = content;

  // Pattern matching: [visibility] [static final | final static] <Type> <VarName>;
  // Don't match class/interface/enum declarations!
  const declRegex = /^([ \t]*(?:public|protected|private)?[ \t]*(?:static\s+final|final\s+static)[ \t]+)([\w.$<>[\]]+)[ \t]+([^\s;=]+)[ \t]*;\r?$/gm;

  let match;
  const changes = [];

  while ((match = declRegex.exec(content)) !== null) {
    const fullMatch = match[0];
    const prefix = match[1];
    const type = match[2];
    const varName = match[3];

    // Exclude class, interface, enum, record keywords
    if (['class', 'interface', 'enum', 'record'].includes(type)) continue;

    // Check if varName is assigned elsewhere in the file
    // e.g., varName = ... or ClassName.varName = ...
    // But exclude this declaration line itself!
    const escapedVar = varName.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    const assignPattern = new RegExp('\\b' + escapedVar + '\\s*=', 'g');
    
    // Test matches in content
    const assignments = content.match(assignPattern);
    if (assignments && assignments.length > 0) {
      // It's assigned somewhere (like in static {} block)
      continue;
    }

    // Replace static final with static, and add default value
    // prefix is e.g. "   private static final "
    const newPrefix = prefix.replace(/\bfinal\s+static\b/, 'static').replace(/\bstatic\s+final\b/, 'static');
    const defVal = getDefaultValue(type);
    const replacement = `${newPrefix}${type} ${varName} = ${defVal};`;

    changes.push({
      start: match.index,
      end: match.index + fullMatch.length,
      original: fullMatch,
      replacement: replacement
    });
  }

  if (changes.length > 0) {
    // Apply changes from end to start to preserve indices
    changes.sort((a, b) => b.start - a.start);
    for (const ch of changes) {
      content = content.substring(0, ch.start) + ch.replacement + content.substring(ch.end);
      totalFixed++;
    }
    fs.writeFileSync(filePath, content, 'utf8');
    filesModified++;
  }
}

function walk(dir) {
  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    const fullPath = path.join(dir, entry.name);
    if (entry.isDirectory()) {
      walk(fullPath);
    } else if (entry.name.endsWith('.java')) {
      processFile(fullPath);
    }
  }
}

walk('src/main/java');
console.log(`Finished: modified ${filesModified} files, fixed ${totalFixed} blank final fields.`);
