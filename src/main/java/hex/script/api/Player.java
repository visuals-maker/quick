package hex.script.api;

/**
 * Локальный игрок. Тот же набор, что у {@link Entity}, плюс сытость, опыт и
 * режим игры.
 *
 * <pre>{@code
 * if (player().health() < 8f && player().inventory().count("golden_apple") > 0) {
 *     notifications().warning("little health", 1500L);
 * }
 * }</pre>
 */
public final class Player {

    static final int X = 0;
    static final int Y = 1;
    static final int Z = 2;
    static final int YAW = 3;
    static final int PITCH = 4;
    static final int HEALTH = 5;
    static final int MAX_HEALTH = 6;
    static final int ABSORPTION = 7;
    static final int FOOD = 8;
    static final int SATURATION = 9;
    static final int EXPERIENCE = 10;
    static final int LEVEL = 11;
    static final int AIR = 12;
    static final int MOTION_X = 13;
    static final int MOTION_Y = 14;
    static final int MOTION_Z = 15;
    static final int FALL = 16;
    static final int ID = 17;
    static final int USE_TICKS = 18;

    static final int PRESENT = 0;
    static final int ON_GROUND = 1;
    static final int SNEAKING = 2;
    static final int SPRINTING = 3;
    static final int IN_WATER = 4;
    static final int FLYING = 5;
    static final int CREATIVE = 6;
    static final int SPECTATOR = 7;
    static final int GLIDING = 8;
    static final int USING = 9;
    static final int MOVING = 10;
    static final int LEFT_HANDED = 11;
    static final int RIDING = 12;
    static final int USING_OFFHAND = 13;

    /** Число по запросу. */
    @FunctionalInterface
    public interface NumberOp {
        double run(int query);
    }

    /** Флаг по запросу. */
    @FunctionalInterface
    public interface FlagOp {
        boolean run(int query);
    }

    /** Имя игрока. */
    @FunctionalInterface
    public interface TextOp {
        String run();
    }

    /** Сглаженная позиция. */
    @FunctionalInterface
    public interface InterpolateOp {
        double[] run(float delta);
    }

    /** Смещение хитбокса с учётом столкновений. */
    @FunctionalInterface
    public interface CollideOp {
        double[] run(double dx, double dy, double dz);
    }

    private final NumberOp numberOp;
    private final FlagOp flagOp;
    private final TextOp textOp;
    private final InterpolateOp interpolateOp;
    private final CollideOp collideOp;
    private final Inventory inventory;
    private final Entities entities;

    public Player(NumberOp numberOp, FlagOp flagOp, TextOp textOp,
                  InterpolateOp interpolateOp, CollideOp collideOp, Inventory inventory, Entities entities) {
        this.numberOp = numberOp;
        this.flagOp = flagOp;
        this.textOp = textOp;
        this.interpolateOp = interpolateOp;
        this.collideOp = collideOp;
        this.inventory = inventory;
        this.entities = entities;
    }

    /**
     * @return есть ли игрок в мире
     */
    public boolean present() {
        return flagOp.run(PRESENT);
    }

    /**
     * @return ник
     */
    public String name() {
        String value = textOp.run();
        return value == null ? "" : value;
    }

    /**
     * Себя же как сущность — для {@link Render3D} и сравнений.
     *
     * @return обёртка сущности
     */
    public Entity entity() {
        return entities.of((int) numberOp.run(ID));
    }

    /**
     * @return инвентарь
     */
    public Inventory inventory() {
        return inventory;
    }

    /**
     * @return позиция по x
     */
    public double x() {
        return numberOp.run(X);
    }

    /**
     * @return позиция по y, ноги
     */
    public double y() {
        return numberOp.run(Y);
    }

    /**
     * @return позиция по z
     */
    public double z() {
        return numberOp.run(Z);
    }

    /**
     * @return позиция
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
        double[] value = interpolateOp.run(delta);
        return value == null ? position() : new Point(value[0], value[1], value[2]);
    }

    /**
     * @return вектор скорости
     */
    public Point motion() {
        return new Point(numberOp.run(MOTION_X), numberOp.run(MOTION_Y), numberOp.run(MOTION_Z));
    }

    /**
     * Сколько хитбокс игрока реально сдвинется из текущей позиции. Блоки,
     * граница мира и твёрдые сущности вроде лодок останавливают движение по
     * каждой оси, как в игре; на ступеньку хитбокс не поднимается.
     *
     * <pre>{@code
     * Point allowed = player().collide(0, 0, -5);
     * boolean blocked = allowed.z() != -5;
     * }</pre>
     *
     * @param dx желаемое смещение
     * @param dy желаемое смещение
     * @param dz желаемое смещение
     * @return смещение, которое останется после столкновений; нулевое без игрока
     */
    public Point collide(double dx, double dy, double dz) {
        double[] value = collideOp.run(dx, dy, dz);
        return value == null ? new Point(0, 0, 0) : new Point(value[0], value[1], value[2]);
    }

    /**
     * @return рыскание в градусах
     */
    public float yaw() {
        return (float) numberOp.run(YAW);
    }

    /**
     * @return тангаж в градусах
     */
    public float pitch() {
        return (float) numberOp.run(PITCH);
    }

    /**
     * @return здоровье
     */
    public float health() {
        return (float) numberOp.run(HEALTH);
    }

    /**
     * @return максимальное здоровье
     */
    public float maxHealth() {
        return (float) numberOp.run(MAX_HEALTH);
    }

    /**
     * @return золотые сердца
     */
    public float absorption() {
        return (float) numberOp.run(ABSORPTION);
    }

    /**
     * @return сытость от 0 до 20
     */
    public int food() {
        return (int) numberOp.run(FOOD);
    }

    /**
     * @return насыщение
     */
    public float saturation() {
        return (float) numberOp.run(SATURATION);
    }

    /**
     * @return прогресс уровня опыта от 0 до 1
     */
    public float experience() {
        return (float) numberOp.run(EXPERIENCE);
    }

    /**
     * @return уровень опыта
     */
    public int level() {
        return (int) numberOp.run(LEVEL);
    }

    /**
     * @return запас воздуха в тиках
     */
    public int air() {
        return (int) numberOp.run(AIR);
    }

    /**
     * @return высота падения в блоках
     */
    public float fallDistance() {
        return (float) numberOp.run(FALL);
    }

    /**
     * @return стоит ли на земле
     */
    public boolean onGround() {
        return flagOp.run(ON_GROUND);
    }

    /**
     * @return приседает ли
     */
    public boolean sneaking() {
        return flagOp.run(SNEAKING);
    }

    /**
     * @return бежит ли
     */
    public boolean sprinting() {
        return flagOp.run(SPRINTING);
    }

    /**
     * @return в воде ли
     */
    public boolean inWater() {
        return flagOp.run(IN_WATER);
    }

    /**
     * @return летит ли в креативе
     */
    public boolean flying() {
        return flagOp.run(FLYING);
    }

    /**
     * @return в креативе ли
     */
    public boolean creative() {
        return flagOp.run(CREATIVE);
    }

    /**
     * @return в наблюдателе ли
     */
    public boolean spectator() {
        return flagOp.run(SPECTATOR);
    }

    /**
     * @return летит ли на элитрах
     */
    public boolean gliding() {
        return flagOp.run(GLIDING);
    }

    /**
     * @return сидит ли в транспорте или верхом
     */
    public boolean riding() {
        return flagOp.run(RIDING);
    }

    /**
     * @return использует ли предмет прямо сейчас
     */
    public boolean using() {
        return flagOp.run(USING);
    }

    /**
     * @return используется ли предмет из левой руки; {@code false}, если ничего не используется
     */
    public boolean usingOffhand() {
        return flagOp.run(USING_OFFHAND);
    }

    /**
     * Сколько тиков уже используется предмет: натягивается лук, идёт еда, поднят щит.
     *
     * @return тики с начала использования; 0, если ничего не используется
     */
    public int useTicks() {
        return (int) numberOp.run(USE_TICKS);
    }

    /**
     * @return выбрана ли в настройках левая основная рука
     */
    public boolean leftHanded() {
        return flagOp.run(LEFT_HANDED);
    }

    /**
     * @return нажаты ли клавиши движения
     */
    public boolean moving() {
        return flagOp.run(MOVING);
    }

    /**
     * @return предмет в основной руке
     */
    public Item held() {
        return inventory.held();
    }

    /**
     * @return предмет в левой руке
     */
    public Item offhand() {
        return inventory.offhand();
    }

    /**
     * Надетая броня.
     *
     * @param part {@link Entity#HEAD}, {@link Entity#CHEST}, {@link Entity#LEGS}
     *             или {@link Entity#FEET}
     * @return снимок предмета
     */
    public Item armor(int part) {
        return inventory.armor(part);
    }
}
