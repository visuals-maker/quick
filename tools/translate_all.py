import json
import os
import re
import time
import urllib.request
import urllib.parse

# Minecraft / HCF / Ghost / Cheat domain overrides
GLOSSARY = {
    "Себя": "Self",
    "Жители": "Villagers",
    "Мобы": "Mobs",
    "Игроки": "Players",
    "Друзья": "Friends",
    "Друзей": "Friends",
    "Гриф ": "Grief ",
    "Только криты": "Only Crits",
    "Не бить через стены": "Raytrace Only",
    "Легитный": "Legit",
    "Задержка в тиках": "Delay in Ticks",
    "Обход Matrix": "Matrix Bypass",
    "Поп тотема": "Totem Pop",
    "Баланс: ": "Balance: ",
    "Монет:": "Coins:",
    "Партиклы хит дамага": "Damage Hit Particles",
    "Менять насыщение": "Change Saturation",
    "Голые": "Naked",
    "Предметы": "Items",
    "Головы": "Heads",
    "Обсидиан": "Obsidian",
    "Кристаллы": "Crystals",
    "Якорь": "Anchor",
    "Обычный тнт": "Normal TNT",
    "Вагонетка с тнт": "TNT Minecart",
    "Расширение таба": "Tab Expansion",
    "С плавным падением": "Slow Falling",
    "Эндер жемчуг": "Ender Pearl",
    "Стрела": "Arrow",
    "С аурой": "With Aura",
    "При взмахе": "On Swing",
    "Призраки 1": "Ghosts 1",
    "Призраки 2": "Ghosts 2",
    "Призраки 3": "Ghosts 3",
    "Сундук": "Chest",
    "Лук": "Bow",
    "Арбалет": "Crossbow",
    "Небо": "Sky",
    "Облака": "Clouds",
    "Насыщение": "Saturation",
    "Дистанция": "Distance",
    "Здоровью": "Health",
    "Вскапывание": "Mining",
    "Сканирование": "Scanning",
    "Время суток": "Time of Day",
    "Линии": "Lines",
    "Квадрат": "Square",
    "Зелье": "Potion",
    "Ударе": "Hit",
    "Индивидуальный": "Custom",
    "От темы": "Theme",
    "[★] Оригинальный предмет": "[★] Original Item",
    "Одиночная игра": "Singleplayer",
    "Сетевая игра": "Multiplayer",
    "Настройки": "Options",
    "Аккаунты": "Accounts",
    "Выйти": "Quit",
    "Клиентский": "Client",
    "Свой": "Custom",
    "Обычный": "Default",
    "Градиент": "Gradient",
    "Статичный": "Static",
    "Режим": "Mode",
    "Блюр": "Blur",
    "Глянцевый": "Glossy",
    "Цвет": "Color",
    "Блоки": "Blocks",
    "Радужный": "Rainbow",
    "Туман": "Fog",
    "Прокси": "Proxy",
    "Создать": "Create",
    "Скорость": "Speed",
    "Старый": "Old",
    "Вода": "Water",
    "Коллизия": "Collision",
    "Всегда": "Always",
    "Трезубец": "Trident",
    "Менять цвет": "Change Color",
    "Тип": "Type",
    "Ник": "Username",
    "Соотношение сторон": "Aspect Ratio",
    "Сервер": "Server",
    "Свечение": "Glow",
    "Кастомный": "Custom",
    "Открыть хранилище": "Open Storage",
    "Яркость игры": "Game Brightness",
    "Вернуться": "Back",
    "toggle режим": "Toggle Mode",
    "Радиус": "Radius",
    "Логин": "Login",
    "Пароль": "Password",
    "Назад": "Back",
    "Добавить": "Add",
    "Копировать": "Copy",
    "Сохранить": "Save",
    "[Мистический сундук]": "[Mystic Chest]",
    "Имя скрипта": "Script Name",
    "аргумент ": "argument ",
    "Вы не состоите в Party.": "You are not in a Party.",
    "Вы вышли из Party.": "You left the Party.",
    "Вы исключены из Party.": "You were kicked from the Party.",
    "Party распущена.": "Party was disbanded.",
}

def translate_batch(texts):
    results = {}
    to_query = []
    
    for t in texts:
        if t in GLOSSARY:
            results[t] = GLOSSARY[t]
        elif len(t.strip()) <= 1:
            # Don't translate single letters used as keys or separators
            results[t] = t
        else:
            to_query.append(t)
            
    if not to_query:
        return results

    # Chunk to_query into groups under 400 chars
    chunks = []
    current_chunk = []
    current_len = 0
    for item in to_query:
        if current_len + len(item) > 350 or len(current_chunk) >= 15:
            chunks.append(current_chunk)
            current_chunk = [item]
            current_len = len(item)
        else:
            current_chunk.append(item)
            current_len += len(item) + 1
    if current_chunk:
        chunks.append(current_chunk)

    for idx, chunk in enumerate(chunks):
        query_text = "\n".join(chunk)
        url = "https://api.mymemory.translated.net/get?" + urllib.parse.urlencode({
            "q": query_text,
            "langpair": "ru|en"
        })
        req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"})
        success = False
        for attempt in range(3):
            try:
                with urllib.request.urlopen(req, timeout=10) as resp:
                    data = json.loads(resp.read().decode("utf-8"))
                    trans = data.get("responseData", {}).get("translatedText", "")
                    lines = trans.split("\n")
                    if len(lines) == len(chunk):
                        for orig, tr in zip(chunk, lines):
                            results[orig] = tr
                    else:
                        # Fallback individual query
                        for orig in chunk:
                            try:
                                u = "https://api.mymemory.translated.net/get?" + urllib.parse.urlencode({"q": orig, "langpair": "ru|en"})
                                r = urllib.request.Request(u, headers={"User-Agent": "Mozilla/5.0"})
                                with urllib.request.urlopen(r, timeout=6) as single_resp:
                                    d = json.loads(single_resp.read().decode("utf-8"))
                                    results[orig] = d.get("responseData", {}).get("translatedText", orig)
                            except:
                                results[orig] = orig
                    success = True
                    break
            except Exception as e:
                time.sleep(1)
        if not success:
            for orig in chunk:
                results[orig] = orig
        time.sleep(0.2)
        if (idx + 1) % 10 == 0:
            print(f"Translated {idx + 1}/{len(chunks)} chunks...")

    return results

def main():
    with open("tools/unique_russian_strings.json", "r", encoding="utf-8") as f:
        unique_list = json.load(f)

    all_keys = [item[0] for item in unique_list]
    print(f"Total unique strings to process: {len(all_keys)}")

    # Check existing translations cache
    cache = {}
    if os.path.exists("tools/translations.json"):
        with open("tools/translations.json", "r", encoding="utf-8") as f:
            cache = json.load(f)

    remaining = [k for k in all_keys if k not in cache]
    print(f"Remaining to translate: {len(remaining)}")

    if remaining:
        new_trans = translate_batch(remaining)
        cache.update(new_trans)
        with open("tools/translations.json", "w", encoding="utf-8") as f:
            json.dump(cache, f, ensure_ascii=False, indent=2)
        print("Updated tools/translations.json")

    # Apply translations to all Java source files
    print("Applying translations to src/main/java...")
    java_files = []
    for root, _, files in os.walk("src/main/java"):
        for f in files:
            if f.endswith(".java") and f != "BetterChat.java":  # keep Anti-Cyrillic dictionary
                java_files.append(os.path.join(root, f))

    # Sort dictionary keys by length descending to avoid partial substring collisions
    sorted_dict = sorted(cache.items(), key=lambda kv: len(kv[0]), reverse=True)
    # Only replace strings where translation is different and length > 1
    replacements = [(k, v) for k, v in sorted_dict if len(k.strip()) > 1 and k != v]
    print(f"Active replacement pairs: {len(replacements)}")

    changed_files = 0
    total_replacements = 0

    for path in java_files:
        with open(path, "r", encoding="utf-8", errors="ignore") as f:
            content = f.read()

        if not re.search(r"[\u0400-\u04FF]", content):
            continue

        orig = content
        # We only replace content inside string literals
        for k, v in replacements:
            # Escape for JSON string
            json_k = json.dumps(k, ensure_ascii=False)
            json_v = json.dumps(v, ensure_ascii=False)
            if json_k in content:
                content = content.replace(json_k, json_v)
                total_replacements += 1

        if content != orig:
            with open(path, "w", encoding="utf-8") as f:
                f.write(content)
            changed_files += 1

    print(f"Done! Changed {changed_files} files with {total_replacements} string replacements.")

if __name__ == "__main__":
    main()
