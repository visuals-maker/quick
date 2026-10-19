package hex.script.api;

/**
 * Сборка и отправка пакетов. События {@code packetSend} и
 * {@code packetReceive} отдают ванильные пакеты как есть, так что читать их
 * можно напрямую; здесь — то, что руками собирать неудобно.
 *
 * <pre>{@code
 * on("packetReceive", event -> {
 *     if (packets().is(event.payload(), "ClientboundSetHeldSlot")) {
 *         event.cancel();
 *     }
 * });
 *
 * packets().send(packets().swing(false));
 * packets().send(packets().look(yaw, pitch, true, false));
 * }</pre>
 *
 * Отправленный пакет проходит через {@code packetSend}, как и любой другой.
 */
public final class Packets {

    /** Отправка готового пакета. */
    @FunctionalInterface
    public interface SendOp {
        boolean run(Object packet);
    }

    /** Движение: 0 — позиция, 1 — поворот, 2 — оба, 3 — только флаги. */
    @FunctionalInterface
    public interface MoveOp {
        Object run(int kind, double x, double y, double z, float yaw, float pitch, boolean onGround, boolean collision);
    }

    /** Сущность: 0 — удар, 1 — взаимодействие, 2 — взаимодействие в точку. */
    @FunctionalInterface
    public interface EntityOp {
        Object run(int kind, Object entity, double x, double y, double z, boolean offhand, boolean sneaking);
    }

    /** Остальное по числовым аргументам. */
    @FunctionalInterface
    public interface SimpleOp {
        Object run(int kind, int a, int b, int c, int d, int e);
    }

    /** Имя класса пакета. */
    @FunctionalInterface
    public interface NameOp {
        String run(Object packet);
    }

    public static final int START_DIG = 0;
    public static final int ABORT_DIG = 1;
    public static final int STOP_DIG = 2;
    public static final int DROP_STACK = 3;
    public static final int DROP_ITEM = 4;
    public static final int RELEASE_USE = 5;
    public static final int SWAP_HANDS = 6;

    public static final int SPRINT_START = 0;
    public static final int SPRINT_STOP = 1;
    public static final int ELYTRA = 2;
    public static final int STOP_SLEEPING = 3;
    public static final int OPEN_INVENTORY = 4;

    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int NORTH = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
    public static final int EAST = 5;

    private final SendOp sendOp;
    private final MoveOp moveOp;
    private final EntityOp entityOp;
    private final SimpleOp simpleOp;
    private final NameOp nameOp;

    public Packets(SendOp sendOp, MoveOp moveOp, EntityOp entityOp, SimpleOp simpleOp, NameOp nameOp) {
        this.sendOp = sendOp;
        this.moveOp = moveOp;
        this.entityOp = entityOp;
        this.simpleOp = simpleOp;
        this.nameOp = nameOp;
    }

    /**
     * Отправляет пакет серверу. Годится и для собранного здесь, и для
     * ванильного, собранного скриптом самостоятельно.
     *
     * @param packet пакет
     * @return ушёл ли; {@code false} без соединения и на {@code null}
     */
    public boolean send(Object packet) {
        return packet != null && sendOp.run(packet);
    }

    /**
     * Имя класса пакета без пакета java, например {@code ClientboundSetHeldSlotPacket}.
     *
     * @param packet пакет
     * @return имя или пустая строка
     */
    public String name(Object packet) {
        String value = nameOp.run(packet);
        return value == null ? "" : value;
    }

    /**
     * Проверяет пакет по имени класса. Сравнение по вхождению и без учёта
     * регистра, поэтому {@code "SetHeldSlot"} совпадёт с полным именем.
     *
     * @param packet пакет
     * @param name   часть имени класса
     * @return совпало ли
     */
    public boolean is(Object packet, String name) {
        return packet != null && name != null && !name.isEmpty()
                && name(packet).toLowerCase().contains(name.toLowerCase());
    }

    /**
     * @param x         позиция
     * @param y         позиция
     * @param z         позиция
     * @param onGround  стоит ли игрок на земле
     * @param collision упирается ли в стену по горизонтали
     * @return пакет позиции
     */
    public Object position(double x, double y, double z, boolean onGround, boolean collision) {
        return moveOp.run(0, x, y, z, 0f, 0f, onGround, collision);
    }

    /**
     * @param yaw       поворот головы
     * @param pitch     наклон головы
     * @param onGround  стоит ли игрок на земле
     * @param collision упирается ли в стену по горизонтали
     * @return пакет поворота
     */
    public Object look(float yaw, float pitch, boolean onGround, boolean collision) {
        return moveOp.run(1, 0d, 0d, 0d, yaw, pitch, onGround, collision);
    }

    /**
     * @param x         позиция
     * @param y         позиция
     * @param z         позиция
     * @param yaw       поворот головы
     * @param pitch     наклон головы
     * @param onGround  стоит ли игрок на земле
     * @param collision упирается ли в стену по горизонтали
     * @return пакет позиции с поворотом
     */
    public Object positionLook(double x, double y, double z, float yaw, float pitch,
                               boolean onGround, boolean collision) {
        return moveOp.run(2, x, y, z, yaw, pitch, onGround, collision);
    }

    /**
     * @param onGround  стоит ли игрок на земле
     * @param collision упирается ли в стену по горизонтали
     * @return пакет только с флагами, без координат
     */
    public Object onGround(boolean onGround, boolean collision) {
        return moveOp.run(3, 0d, 0d, 0d, 0f, 0f, onGround, collision);
    }

    /**
     * @param entity ванильная {@code Entity}
     * @return пакет удара
     */
    public Object attack(Entity entity) {
        return entityOp.run(0, entity, 0d, 0d, 0d, false, false);
    }

    /**
     * @param entity   сущность
     * @param offhand  левой рукой
     * @param sneaking с приседом
     * @return пакет взаимодействия
     */
    public Object interact(Entity entity, boolean offhand, boolean sneaking) {
        return entityOp.run(1, entity, 0d, 0d, 0d, offhand, sneaking);
    }

    public Object attack(Object entity) {
        return entityOp.run(0, entity, 0d, 0d, 0d, false, false);
    }

    /**
     * @param entity   ванильная {@code Entity}
     * @param offhand  левой рукой
     * @param sneaking с приседом
     * @return пакет взаимодействия
     */
    public Object interact(Object entity, boolean offhand, boolean sneaking) {
        return entityOp.run(1, entity, 0d, 0d, 0d, offhand, sneaking);
    }

    /**
     * @param entity   ванильная {@code Entity}
     * @param x        точка попадания относительно сущности
     * @param y        точка попадания относительно сущности
     * @param z        точка попадания относительно сущности
     * @param offhand  левой рукой
     * @param sneaking с приседом
     * @return пакет взаимодействия в точку
     */
    public Object interactAt(Object entity, double x, double y, double z, boolean offhand, boolean sneaking) {
        return entityOp.run(2, entity, x, y, z, offhand, sneaking);
    }

    /**
     * @param hotbar слот хотбара 0..8
     * @return пакет смены слота
     */
    public Object slot(int hotbar) {
        return simpleOp.run(0, hotbar, 0, 0, 0, 0);
    }

    /**
     * @param offhand левой рукой
     * @return пакет маха рукой
     */
    public Object swing(boolean offhand) {
        return simpleOp.run(1, offhand ? 1 : 0, 0, 0, 0, 0);
    }

    /**
     * Действие над блоком или предметом в руке.
     *
     * @param action один из {@link #START_DIG}, {@link #ABORT_DIG}, {@link #STOP_DIG},
     *               {@link #DROP_STACK}, {@link #DROP_ITEM}, {@link #RELEASE_USE},
     *               {@link #SWAP_HANDS}
     * @param x      координата блока
     * @param y      координата блока
     * @param z      координата блока
     * @param side   сторона: {@link #DOWN}, {@link #UP}, {@link #NORTH}, {@link #SOUTH},
     *               {@link #WEST}, {@link #EAST}
     * @return пакет действия
     */
    public Object blockAction(int action, int x, int y, int z, int side) {
        return simpleOp.run(2, action, x, y, z, side);
    }

    /**
     * Команда состояния игрока. Приседа здесь нет — с 26.2 он едет во
     * входном пакете, для него есть {@link Control#sneaking(boolean)}.
     *
     * @param action один из {@link #SPRINT_START}, {@link #SPRINT_STOP},
     *               {@link #ELYTRA}, {@link #STOP_SLEEPING}, {@link #OPEN_INVENTORY}
     * @return пакет команды
     */
    public Object playerCommand(int action) {
        return simpleOp.run(3, action, 0, 0, 0, 0);
    }

    /**
     * @param containerId номер открытого контейнера
     * @return пакет закрытия контейнера
     */
    public Object closeContainer(int containerId) {
        return simpleOp.run(4, containerId, 0, 0, 0, 0);
    }

    /**
     * @return пакет возрождения после смерти
     */
    public Object respawn() {
        return simpleOp.run(5, 0, 0, 0, 0, 0);
    }

    /**
     * Использование предмета в руке.
     *
     * @param offhand  левой рукой
     * @param sequence номер предсказания, 0 если не важен
     * @param yaw      поворот головы в момент использования
     * @param pitch    наклон головы в момент использования
     * @return пакет использования
     */
    public Object useItem(boolean offhand, int sequence, float yaw, float pitch) {
        return simpleOp.run(6, offhand ? 1 : 0, sequence,
                Float.floatToRawIntBits(yaw), Float.floatToRawIntBits(pitch), 0);
    }
}
