package hex.script.api;

/**
 * Снимок блока на момент запроса.
 *
 * <pre>{@code
 * Block under = world().block(player().x(), player().y() - 1, player().z());
 * if (under.air()) {
 *     log().info("underfoot is empty");
 * }
 * }</pre>
 */
public final class Block {

    private String id = "minecraft:air";
    private String key;
    private int x;
    private int y;
    private int z;
    private float hardness;
    private boolean air = true;
    private boolean solid;
    private boolean liquid;
    private boolean replaceable = true;

    /**
     * Заполняет снимок. Вызывает клиент.
     *
     * @param id          строковый id блока
     * @param x           координата
     * @param y           координата
     * @param z           координата
     * @param hardness    прочность, отрицательная у неломаемых
     * @param air         воздух ли
     * @param solid       есть ли коллизия
     * @param liquid      жидкость ли
     * @param replaceable можно ли ставить поверх
     */
    public void set(String id, int x, int y, int z, float hardness,
                    boolean air, boolean solid, boolean liquid, boolean replaceable) {
        this.id = id == null ? "minecraft:air" : id;
        this.key = null;
        this.x = x;
        this.y = y;
        this.z = z;
        this.hardness = hardness;
        this.air = air;
        this.solid = solid;
        this.liquid = liquid;
        this.replaceable = replaceable;
    }

    /**
     * @return строковый id, например {@code "minecraft:obsidian"}
     */
    public String id() {
        return id;
    }

    /**
     * @return id без пространства имён
     */
    public String key() {
        if (key == null) {
            int index = id.indexOf(':');
            key = index < 0 ? id : id.substring(index + 1);
        }
        return key;
    }

    /**
     * @return координата блока
     */
    public int x() {
        return x;
    }

    /**
     * @return координата блока
     */
    public int y() {
        return y;
    }

    /**
     * @return координата блока
     */
    public int z() {
        return z;
    }

    /**
     * @return центр блока
     */
    public Point center() {
        return new Point(x + 0.5d, y + 0.5d, z + 0.5d);
    }

    /**
     * @return прочность; отрицательная у неломаемых
     */
    public float hardness() {
        return hardness;
    }

    /**
     * @return воздух ли
     */
    public boolean air() {
        return air;
    }

    /**
     * @return есть ли коллизия
     */
    public boolean solid() {
        return solid;
    }

    /**
     * @return жидкость ли
     */
    public boolean liquid() {
        return liquid;
    }

    /**
     * @return можно ли поставить блок поверх этого
     */
    public boolean replaceable() {
        return replaceable;
    }

    /**
     * Сравнивает id. Пространство имён можно не писать.
     * Сравнение идёт по месту, без нарезки строк.
     *
     * @param value {@code "obsidian"} или {@code "minecraft:obsidian"}
     * @return совпало ли
     */
    public boolean is(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (id.equals(value)) {
            return true;
        }
        int index = id.indexOf(':');
        return index >= 0 && id.length() - index - 1 == value.length()
                && id.regionMatches(index + 1, value, 0, value.length());
    }

    @Override
    public String toString() {
        return id + " (" + x + ", " + y + ", " + z + ")";
    }
}
