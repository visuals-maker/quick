const fs = require('fs');
const path = require('path');

const ROOT_BAK = path.resolve('src/main/java.bak');
const ROOT_SRC = path.resolve('src/main/java');
const trans = JSON.parse(fs.readFileSync('tools/translations.json', 'utf8'));

// Exact map of whole literals
const exactMap = new Map();
for (const [k, v] of Object.entries(trans)) {
  if (k.trim().length > 1 && v && v !== k) {
    exactMap.set(k, v);
  }
}

// Add manual fixes for remaining partial phrases
const overrides = {
  "Тип обводки": "Outline Type",
  "Тип эффекта": "Effect Type",
  "Цвет блюра": "Blur Color",
  "Тип цвета": "Color Type",
  "Цвет обводки": "Outline Color",
  "Цвет обводки 2": "Outline Color 2",
  "Кастомный цвет": "Custom Color",
  "Отображать на друзьях": "Display on Friends",
  "Созвездие мерцающих звёзд вокруг игрока": "Constellation of twinkling stars around the player",
  "Работает если": "Works if",
  "Менять цвет при наведении": "Change color on hover",
  "Цвет при наведении": "Hover Color",
  "Создание ключа для": "Create key for",
  "Загрузка конфига": "Loading config",
  "Создать ключ": "Create Key",
  "Ключей выгружено:": "Keys uploaded:",
  "Нет конфигов — нажми «Создать»": "No configs — press «Create»",
  "Ключей нет — поделись конфигом": "No keys — share your config",
  "Варден": "Warden",
  "варден": "warden",
  "Вардена": "Warden",
  "Вардену": "Warden",
  "Без смен": "Without shifts",
  "без смен": "without shifts",
  "сервер задал скорость": "server set speed",
  "Сессия не восстановилась, управление ботом снято": "Session did not recover, bot control removed",
  "Сессия ещё не прогрузилась": "Session has not loaded yet",
  "варден off — мозг не работает": "warden off — brain not running",
  "Нагрудник": "Chestplate",
  "Шлем": "Helmet",
  "Поножи": "Leggings",
  "Ботинки": "Boots",
  "Меч": "Sword",
  "Топор": "Axe",
  "Кирка": "Pickaxe",
  "Лопата": "Shovel",
  "Лук": "Bow",
  "Арбалет": "Crossbow",
  "Щит": "Shield",
  "Элитры": "Elytra",
  "Тотем": "Totem",
  "Золотое яблоко": "Golden Apple",
  "Зачарованное яблоко": "Enchanted Apple",
  "Зелье": "Potion",
  "Зелье исцеления": "Healing Potion",
  "Зелье исцеления 2": "Healing Potion 2",
  "Зелье Победителя": "Winner Potion",
  "Взрывная динамитная шашка": "Explosive dynamite",
  "Взрывная лестница": "Explosive ladder"
};

for (const [k, v] of Object.entries(overrides)) {
  exactMap.set(k, v);
}

// Sort exact keys by length descending
const sortedKeys = Array.from(exactMap.keys()).sort((a, b) => b.length - a.length);

let totalReplaced = 0;
let modifiedFiles = 0;

function walk(relPath = '') {
  const currentBak = path.join(ROOT_BAK, relPath);
  const currentSrc = path.join(ROOT_SRC, relPath);

  for (const item of fs.readdirSync(currentBak, { withFileTypes: true })) {
    const itemRel = relPath ? path.join(relPath, item.name) : item.name;
    if (item.isDirectory()) {
      walk(itemRel);
    } else if (item.name.endsWith('.java')) {
      if (item.name === 'BetterChat.java') {
        // Keep BetterChat intact (it has Cyrillic character tables)
        continue;
      }
      
      const bakContent = fs.readFileSync(path.join(currentBak, item.name), 'utf8');
      if (!/[\u0400-\u04FF]/.test(bakContent)) {
        continue;
      }

      let changed = false;
      const newContent = bakContent.replace(/"([^"\\]*(?:\\.[^"\\]*)*)"/g, (match, inner) => {
        if (!/[\u0400-\u04FF]/.test(inner)) return match;
        
        // 1. Direct whole-match check
        if (exactMap.has(inner)) {
          totalReplaced++;
          changed = true;
          return '"' + exactMap.get(inner) + '"';
        }

        // 2. Multi-key sub-match
        let replaced = inner;
        for (const k of sortedKeys) {
          if (replaced.includes(k)) {
            replaced = replaced.split(k).join(exactMap.get(k));
            totalReplaced++;
            changed = true;
          }
        }
        return '"' + replaced + '"';
      });

      if (changed) {
        fs.writeFileSync(path.join(currentSrc, item.name), newContent, 'utf8');
        modifiedFiles++;
      }
    }
  }
}

walk();
console.log(`Replaced clean from backup! Files modified: ${modifiedFiles}, strings replaced: ${totalReplaced}`);
