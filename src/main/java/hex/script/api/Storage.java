package hex.script.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Файл с парами ключ-значение, свой у каждого скрипта. Лежит в
 * {@code Hex/scripts/.data/<скрипт>/<имя>.txt}. Значение всегда строка,
 * числа и списки разбираются на чтении.
 *
 * <pre>{@code
 * int runs = storage().getInt("runs", 0) + 1;
 * storage().put("runs", runs).save();
 * }</pre>
 */
public final class Storage {

    /** Чтение одного ключа. */
    @FunctionalInterface
    public interface GetOp {
        String run(String key);
    }

    /** Запись одного ключа; {@code null} удаляет. */
    @FunctionalInterface
    public interface PutOp {
        void run(String key, String value);
    }

    /** Список ключей. */
    @FunctionalInterface
    public interface KeysOp {
        String[] run();
    }

    /** Сброс на диск или очистка. */
    @FunctionalInterface
    public interface ActionOp {
        void run();
    }

    private final String name;
    private final GetOp getOp;
    private final PutOp putOp;
    private final KeysOp keysOp;
    private final ActionOp saveOp;
    private final ActionOp clearOp;

    public Storage(String name, GetOp getOp, PutOp putOp, KeysOp keysOp, ActionOp saveOp, ActionOp clearOp) {
        this.name = name;
        this.getOp = getOp;
        this.putOp = putOp;
        this.keysOp = keysOp;
        this.saveOp = saveOp;
        this.clearOp = clearOp;
    }

    /**
     * @return имя конфига
     */
    public String name() {
        return name;
    }

    /**
     * @param key ключ
     * @return есть ли такой ключ
     */
    public boolean has(String key) {
        return getOp.run(key) != null;
    }

    /**
     * @param key      ключ
     * @param fallback значение, если ключа нет
     * @return строка из конфига
     */
    public String get(String key, String fallback) {
        String value = getOp.run(key);
        return value == null ? fallback : value;
    }

    /**
     * @param key      ключ
     * @param fallback значение, если ключа нет или он не число
     * @return целое из конфига
     */
    public int getInt(String key, int fallback) {
        try {
            String value = getOp.run(key);
            return value == null ? fallback : Integer.parseInt(value.trim());
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    /**
     * @param key      ключ
     * @param fallback значение, если ключа нет или он не число
     * @return длинное целое из конфига
     */
    public long getLong(String key, long fallback) {
        try {
            String value = getOp.run(key);
            return value == null ? fallback : Long.parseLong(value.trim());
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    /**
     * @param key      ключ
     * @param fallback значение, если ключа нет или он не число
     * @return дробное из конфига
     */
    public double getDouble(String key, double fallback) {
        try {
            String value = getOp.run(key);
            return value == null ? fallback : Double.parseDouble(value.trim());
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    /**
     * @param key      ключ
     * @param fallback значение, если ключа нет
     * @return логическое из конфига
     */
    public boolean getBoolean(String key, boolean fallback) {
        String value = getOp.run(key);
        return value == null ? fallback : Boolean.parseBoolean(value.trim());
    }

    /**
     * @param key ключ
     * @return список, сохранённый через {@link #put(String, List)}; пустой, если ключа нет
     */
    public List<String> getList(String key) {
        String value = getOp.run(key);
        if (value == null || value.isEmpty()) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (String part : value.split("\u001f")) {
            result.add(part);
        }
        return result;
    }

    /**
     * @return все ключи конфига
     */
    public List<String> keys() {
        String[] keys = keysOp.run();
        return keys == null ? List.of() : List.copyOf(Arrays.asList(keys));
    }

    /**
     * @param key   ключ
     * @param value значение; {@code null} удаляет ключ
     * @return этот же конфиг
     */
    public Storage put(String key, String value) {
        putOp.run(key, value);
        return this;
    }

    /**
     * @param key   ключ
     * @param value число
     * @return этот же конфиг
     */
    public Storage put(String key, long value) {
        return put(key, String.valueOf(value));
    }

    /**
     * @param key   ключ
     * @param value число
     * @return этот же конфиг
     */
    public Storage put(String key, double value) {
        return put(key, String.valueOf(value));
    }

    /**
     * @param key   ключ
     * @param value флаг
     * @return этот же конфиг
     */
    public Storage put(String key, boolean value) {
        return put(key, String.valueOf(value));
    }

    /**
     * @param key    ключ
     * @param values список строк
     * @return этот же конфиг
     */
    public Storage put(String key, List<String> values) {
        return put(key, values == null ? null : String.join("\u001f", values));
    }

    /**
     * @param key ключ
     * @return этот же конфиг
     */
    public Storage remove(String key) {
        putOp.run(key, null);
        return this;
    }

    /**
     * Пишет конфиг на диск.
     *
     * @return этот же конфиг
     */
    public Storage save() {
        saveOp.run();
        return this;
    }

    /**
     * Очищает конфиг в памяти.
     *
     * @return этот же конфиг
     */
    public Storage clear() {
        clearOp.run();
        return this;
    }
}
