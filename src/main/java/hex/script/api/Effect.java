package hex.script.api;

/**
 * Снимок активного эффекта зелья на момент запроса. Ванильный
 * {@code MobEffectInstance} наружу не отдаётся, поэтому это копия полей,
 * а не живая ссылка.
 *
 * <pre>{@code
 * Effect speed = client().effect("speed");
 * if (speed != null && !speed.infinite()) {
 *     log().info("speed " + speed.level() + ", Remaining " + speed.duration() + " tics");
 * }
 * }</pre>
 */
public final class Effect {

    /** Полезный эффект. */
    public static final int BENEFICIAL = 0;
    /** Вредный эффект. */
    public static final int HARMFUL = 1;
    /** Нейтральный эффект. */
    public static final int NEUTRAL = 2;

    private int category = NEUTRAL;
    private String id = "";
    private String name = "";
    private int amplifier;
    private int duration;
    private boolean infinite;
    private boolean ambient;
    private boolean visible;
    private int color;

    /**
     * Заполняет снимок. Вызывает клиент.
     *
     * @param id        строковый id эффекта
     * @param name      отображаемое имя
     * @param amplifier усиление, 0 у уровня I
     * @param duration  остаток в тиках
     * @param infinite  бесконечный ли
     * @param ambient   пришёл ли от маяка
     * @param visible   видны ли частицы
     * @param color     цвет частиц
     * @param category  {@link #BENEFICIAL}, {@link #HARMFUL} или {@link #NEUTRAL}
     */
    public void set(String id, String name, int amplifier, int duration,
                    boolean infinite, boolean ambient, boolean visible, int color, int category) {
        this.id = id == null ? "" : id;
        this.name = name == null ? "" : name;
        this.amplifier = amplifier;
        this.duration = duration;
        this.infinite = infinite;
        this.ambient = ambient;
        this.visible = visible;
        this.color = color;
        this.category = category;
    }

    /**
     * @return {@link #BENEFICIAL}, {@link #HARMFUL} или {@link #NEUTRAL}
     */
    public int category() {
        return category;
    }

    /**
     * @return вредный ли эффект
     */
    public boolean harmful() {
        return category == HARMFUL;
    }

    /**
     * @return полезный ли эффект
     */
    public boolean beneficial() {
        return category == BENEFICIAL;
    }

    /**
     * @return строковый id, например {@code "minecraft:speed"}
     */
    public String id() {
        return id;
    }

    /**
     * @return id без пространства имён, например {@code "speed"}
     */
    public String key() {
        int index = id.indexOf(':');
        return index < 0 ? id : id.substring(index + 1);
    }

    /**
     * @return отображаемое имя без оформления
     */
    public String name() {
        return name;
    }

    /**
     * @return усиление; 0 у уровня I, 1 у уровня II
     */
    public int amplifier() {
        return amplifier;
    }

    /**
     * @return уровень эффекта, то есть {@link #amplifier()} плюс один
     */
    public int level() {
        return amplifier + 1;
    }

    /**
     * Остаток эффекта в тиках. У бесконечного число ничего не значит —
     * сперва проверяй {@link #infinite()}.
     *
     * @return остаток в тиках
     */
    public int duration() {
        return duration;
    }

    /**
     * @return бесконечный ли эффект
     */
    public boolean infinite() {
        return infinite;
    }

    /**
     * @return пришёл ли эффект от маяка
     */
    public boolean ambient() {
        return ambient;
    }

    /**
     * @return видны ли частицы эффекта
     */
    public boolean visible() {
        return visible;
    }

    /**
     * Цвет частиц эффекта в виде {@code 0xRRGGBB}. Прозрачности в нём нет,
     * так что для отрисовки добавляй свою: {@code 0xFF000000 | color()}.
     *
     * @return цвет частиц
     */
    public int color() {
        return color;
    }

    /**
     * Сравнивает id. Пространство имён можно не писать.
     *
     * @param value {@code "speed"} или {@code "minecraft:speed"}
     * @return совпало ли
     */
    public boolean is(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return id.equals(value) || key().equals(value);
    }

    @Override
    public String toString() {
        return id + " " + level() + (infinite ? " (infinite)" : " (" + duration + ")");
    }
}
