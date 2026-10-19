package hex.script.api;

/**
 * Сущность в мире. Держит только числовой id — ванильный объект наружу не
 * отдаётся, клиент резолвит id на каждом обращении. Если сущность выгрузилась,
 * {@link #valid()} становится {@code false}, а геттеры отдают нули.
 *
 * <pre>{@code
 * Entity target = target();
 * if (target != null && target.distance() < 4.0) {
 *     interaction().attack(target);
 * }
 * }</pre>
 */
public final class Entity {

    public static final int HEAD = 0;
    public static final int CHEST = 1;
    public static final int LEGS = 2;
    public static final int FEET = 3;

    static final int X = 0;
    static final int Y = 1;
    static final int Z = 2;
    static final int YAW = 3;
    static final int PITCH = 4;
    static final int HEALTH = 5;
    static final int MAX_HEALTH = 6;
    static final int ABSORPTION = 7;
    static final int WIDTH = 8;
    static final int HEIGHT = 9;
    static final int DISTANCE = 10;
    static final int MOTION_X = 11;
    static final int MOTION_Y = 12;
    static final int MOTION_Z = 13;
    static final int HURT_TIME = 14;

    static final int VALID = 0;
    static final int ALIVE = 1;
    static final int PLAYER = 2;
    static final int LIVING = 3;
    static final int SELF = 4;
    static final int SNEAKING = 5;
    static final int SPRINTING = 6;
    static final int ON_GROUND = 7;
    static final int INVISIBLE = 8;
    static final int FRIEND = 9;
    static final int IN_WATER = 10;
    static final int GLIDING = 11;

    static final int NAME = 0;
    static final int TYPE = 1;
    static final int SKIN = 2;
    static final int UUID = 3;

    static final int DISPLAY_NAME = 0;

    private final int id;
    private final Entities ops;

    Entity(int id, Entities ops) {
        this.id = id;
        this.ops = ops;
    }

    /**
     * @return сетевой id сущности
     */
    public int id() {
        return id;
    }

    /**
     * @return есть ли сущность в мире прямо сейчас
     */
    public boolean valid() {
        return ops.flag(id, VALID);
    }

    /**
     * @return отображаемое имя без оформления
     */
    public String name() {
        return ops.text(id, NAME);
    }

    /**
     * Имя с оформлением: цвет команды, префиксы и суффиксы, жирность и прочие
     * стили сохраняются. Куски разбираются через {@link Text#parts()} и
     * {@link Text#partText(int)}, так что ник можно нарисовать посегментно.
     *
     * <p>Клиент держит одну сборку на сущность и пересобирает её, только когда
     * имя поменялось, поэтому звать метод каждый кадр дёшево. Обратная сторона —
     * сборка общая: менять её на месте нельзя, для своих правок собирай новую
     * через {@link Text#empty()}.
     *
     * <pre>{@code
     * render.drawText(font, target.displayName(), x, y, 7f, 0f, 0xFFFFFFFF);
     * }</pre>
     *
     * Куску без своего цвета {@link Text#partColor(int)} отдаёт 0 — при ручном
     * обходе подставляй вместо него цвет по умолчанию.
     *
     * @return оформленное имя; пустая сборка, если сущности нет в мире
     */
    public Text displayName() {
        return ops.styled(id, DISPLAY_NAME);
    }

    /**
     * @return тип, например {@code "minecraft:zombie"}
     */
    public String type() {
        return ops.text(id, TYPE);
    }

    /**
     * Путь текстуры скина в виде {@code namespace:path}, например
     * {@code "minecraft:skins/1a2b3c..."}. Пока скин не приехал с сервера,
     * отдаётся дефолтная текстура Стива или Алекса.
     *
     * @return путь текстуры; пустая строка, если это не игрок или его нет в мире
     */
    public String skin() {
        return ops.text(id, SKIN);
    }

    /**
     * @return uuid в каноническом виде {@code "xxxxxxxx-xxxx-..."}; пустая строка, если сущности нет
     */
    public String uuid() {
        return ops.text(id, UUID);
    }

    /**
     * @return позиция по x
     */
    public double x() {
        return ops.number(id, X);
    }

    /**
     * @return позиция по y, низ хитбокса
     */
    public double y() {
        return ops.number(id, Y);
    }

    /**
     * @return позиция по z
     */
    public double z() {
        return ops.number(id, Z);
    }

    /**
     * @return позиция как точка
     */
    public Point position() {
        return new Point(x(), y(), z());
    }

    /**
     * Позиция с учётом частичного тика — то, что видно на экране.
     *
     * @param delta частичный тик из {@link Render3D#delta()}
     * @return сглаженная позиция
     */
    public Point position(float delta) {
        double[] value = ops.interpolate(id, delta);
        return value == null ? position() : new Point(value[0], value[1], value[2]);
    }

    /**
     * @return рыскание в градусах
     */
    public float yaw() {
        return (float) ops.number(id, YAW);
    }

    /**
     * @return тангаж в градусах
     */
    public float pitch() {
        return (float) ops.number(id, PITCH);
    }

    /**
     * Здоровье так, как его видит клиент: если включён модуль чтения хп со
     * скорборда, у игроков берётся значение оттуда, а не поле сущности.
     *
     * @return здоровье; 0 у не-живых
     */
    public float health() {
        return (float) ops.number(id, HEALTH);
    }

    /**
     * @return максимальное здоровье
     */
    public float maxHealth() {
        return (float) ops.number(id, MAX_HEALTH);
    }

    /**
     * @return золотые сердца
     */
    public float absorption() {
        return (float) ops.number(id, ABSORPTION);
    }

    /**
     * @return ширина хитбокса
     */
    public float width() {
        return (float) ops.number(id, WIDTH);
    }

    /**
     * @return высота хитбокса
     */
    public float height() {
        return (float) ops.number(id, HEIGHT);
    }

    /**
     * @return расстояние до игрока в блоках
     */
    public double distance() {
        return ops.number(id, DISTANCE);
    }

    /**
     * @return вектор скорости
     */
    public Point motion() {
        return new Point(ops.number(id, MOTION_X), ops.number(id, MOTION_Y), ops.number(id, MOTION_Z));
    }

    /**
     * @return сколько тиков осталось от анимации получения урона
     */
    public int hurtTime() {
        return (int) ops.number(id, HURT_TIME);
    }

    /**
     * @return жива ли сущность
     */
    public boolean alive() {
        return ops.flag(id, ALIVE);
    }

    /**
     * @return игрок ли это
     */
    public boolean player() {
        return ops.flag(id, PLAYER);
    }

    /**
     * @return живое ли существо; у не-живых нет здоровья и брони
     */
    public boolean living() {
        return ops.flag(id, LIVING);
    }

    /**
     * @return это ли сам игрок
     */
    public boolean self() {
        return ops.flag(id, SELF);
    }

    /**
     * @return приседает ли
     */
    public boolean sneaking() {
        return ops.flag(id, SNEAKING);
    }

    /**
     * @return бежит ли
     */
    public boolean sprinting() {
        return ops.flag(id, SPRINTING);
    }

    /**
     * @return стоит ли на земле
     */
    public boolean onGround() {
        return ops.flag(id, ON_GROUND);
    }

    /**
     * @return невидима ли
     */
    public boolean invisible() {
        return ops.flag(id, INVISIBLE);
    }

    /**
     * @return в воде ли
     */
    public boolean inWater() {
        return ops.flag(id, IN_WATER);
    }

    /**
     * @return летит ли на элитрах
     */
    public boolean gliding() {
        return ops.flag(id, GLIDING);
    }

    /**
     * @return в друзьях ли клиента
     */
    public boolean friend() {
        return ops.flag(id, FRIEND);
    }

    /**
     * @return предмет в основной руке
     */
    public Item held() {
        return ops.item(id, -1);
    }

    /**
     * @return предмет в левой руке
     */
    public Item offhand() {
        return ops.item(id, -2);
    }

    /**
     * Надетая броня.
     *
     * @param part {@link #HEAD}, {@link #CHEST}, {@link #LEGS} или {@link #FEET}
     * @return снимок предмета
     */
    public Item armor(int part) {
        return ops.item(id, part);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Entity entity && entity.id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name() + "#" + id;
    }
}
