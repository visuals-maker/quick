package hex.script.api;

/**
 * Снимок пакета на момент события. Ванильный пакет наружу не отдаётся, поэтому
 * это имя типа плюс плоский набор разобранных полей: числа, флаги и строки.
 *
 * <p>Снимок живёт только внутри обработчика: клиент переиспользует экземпляры,
 * так что нужное значение надо забрать сразу, а не хранить ссылку на
 * {@code PacketInfo}.
 *
 * <p>Незнакомые типы приходят с одним лишь {@link #type()} — полей у них нет,
 * и {@link #keys()} вернёт пустой массив.
 *
 * <pre>{@code
 * on("packetReceive", event -> {
 *     PacketInfo packet = (PacketInfo) event.payload();
 *     if (packet.is("SetHealth") && packet.getDouble("health", 20) < 6) {
 *         log().info("little xp");
 *     }
 * });
 *
 * on("packetSend", event -> {
 *     PacketInfo packet = (PacketInfo) event.payload();
 *     if (packet.is("MovePlayer") && !packet.getBoolean("onGround", false)) {
 *         event.cancel();
 *     }
 * });
 * }</pre>
 */
public final class PacketInfo {

    private static final int CAPACITY = 12;
    private static final String[] EMPTY = new String[0];

    private final String[] names = new String[CAPACITY];
    private final double[] numbers = new double[CAPACITY];
    private final String[] texts = new String[CAPACITY];

    private String type = "";
    private boolean incoming;
    private int size;

    /**
     * Начинает новый снимок, сбрасывая поля прошлого. Вызывает клиент.
     *
     * @param type     простое имя класса пакета
     * @param incoming пришёл ли пакет от сервера
     */
    public void set(String type, boolean incoming) {
        for (int i = 0; i < size; i++) {
            names[i] = null;
            texts[i] = null;
        }
        this.type = type == null ? "" : type;
        this.incoming = incoming;
        this.size = 0;
    }

    /**
     * Кладёт числовое поле. Вызывает клиент.
     *
     * @param key   имя поля
     * @param value значение
     */
    public void put(String key, double value) {
        int index = slot(key);
        if (index >= 0) {
            numbers[index] = value;
            texts[index] = null;
        }
    }

    /**
     * Кладёт флаг. Вызывает клиент.
     *
     * @param key   имя поля
     * @param value значение
     */
    public void put(String key, boolean value) {
        put(key, value ? 1d : 0d);
    }

    /**
     * Кладёт строковое поле. Вызывает клиент.
     *
     * @param key   имя поля
     * @param value значение, {@code null} превращается в пустую строку
     */
    public void put(String key, String value) {
        int index = slot(key);
        if (index >= 0) {
            numbers[index] = 0d;
            texts[index] = value == null ? "" : value;
        }
    }

    /**
     * Простое имя класса пакета, например {@code ClientboundSetHeldSlotPacket}.
     * У вложенных классов части разделены точкой, например
     * {@code ServerboundMovePlayerPacket.PosRot}.
     *
     * @return имя типа или пустая строка
     */
    public String type() {
        return type;
    }

    /**
     * @return пришёл ли пакет от сервера; {@code false} у отправляемых
     */
    public boolean incoming() {
        return incoming;
    }

    /**
     * @return уходит ли пакет на сервер
     */
    public boolean outgoing() {
        return !incoming;
    }

    /**
     * Проверяет тип по имени класса. Сравнение по вхождению и без учёта
     * регистра, поэтому {@code "SetHeldSlot"} совпадёт с полным именем.
     *
     * @param name часть имени класса
     * @return совпало ли
     */
    public boolean is(String name) {
        return name != null && !name.isEmpty() && type.toLowerCase().contains(name.toLowerCase());
    }

    /**
     * @param key имя поля
     * @return разобрано ли такое поле у этого пакета
     */
    public boolean has(String key) {
        return indexOf(key) >= 0;
    }

    /**
     * @return имена разобранных полей; пустой массив, если тип не разбирается
     */
    public String[] keys() {
        if (size == 0) {
            return EMPTY;
        }
        String[] copy = new String[size];
        System.arraycopy(names, 0, copy, 0, size);
        return copy;
    }

    /**
     * @param key      имя поля
     * @param fallback что вернуть, если поля нет или оно строковое
     * @return целое значение
     */
    public int getInt(String key, int fallback) {
        int index = indexOf(key);
        return index < 0 || texts[index] != null ? fallback : (int) numbers[index];
    }

    /**
     * @param key      имя поля
     * @param fallback что вернуть, если поля нет или оно строковое
     * @return дробное значение
     */
    public double getDouble(String key, double fallback) {
        int index = indexOf(key);
        return index < 0 || texts[index] != null ? fallback : numbers[index];
    }

    /**
     * @param key      имя поля
     * @param fallback что вернуть, если поля нет или оно строковое
     * @return флаг; числовое поле считается флагом по признаку «не ноль»
     */
    public boolean getBoolean(String key, boolean fallback) {
        int index = indexOf(key);
        return index < 0 || texts[index] != null ? fallback : numbers[index] != 0d;
    }

    /**
     * @param key      имя поля
     * @param fallback что вернуть, если поля нет
     * @return строковое значение; числовое поле отдаётся его записью
     */
    public String getString(String key, String fallback) {
        int index = indexOf(key);
        if (index < 0) {
            return fallback;
        }
        if (texts[index] != null) {
            return texts[index];
        }
        double value = numbers[index];
        long rounded = (long) value;
        return value == rounded ? Long.toString(rounded) : Double.toString(value);
    }

    private int slot(String key) {
        if (key == null || key.isEmpty()) {
            return -1;
        }
        int index = indexOf(key);
        if (index >= 0) {
            return index;
        }
        if (size == CAPACITY) {
            return -1;
        }
        names[size] = key;
        return size++;
    }

    private int indexOf(String key) {
        if (key == null) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (names[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(incoming ? "in " : "out ").append(type);
        for (int i = 0; i < size; i++) {
            builder.append(i == 0 ? " {" : ", ").append(names[i]).append('=').append(getString(names[i], ""));
        }
        return size == 0 ? builder.toString() : builder.append('}').toString();
    }
}
