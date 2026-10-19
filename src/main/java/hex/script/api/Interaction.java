package hex.script.api;

/**
 * Удары, использование предметов и работа с блоками. Всё выполняется на
 * клиентском потоке, поэтому зовётся из событий и колбэков скрипта.
 *
 * <pre>{@code
 * Point point = interaction().attackPoint(entity, "multi", 4.5, false);
 * rotations().apply(rotations().lookAt(point));
 * interaction().attack(entity);
 * }</pre>
 */
public final class Interaction {

    /**
     * Действие без цели: 0 — удар по прицелу, 1 — использование по прицелу,
     * 2 — отпустить предмет, 3 — обмен рук, 4 — мах рукой,
     * 5 — начать использование, 6 — отменить ломание.
     */
    @FunctionalInterface
    public interface ActionOp {
        boolean run(int action, boolean offhand);
    }

    /** Удар по сущности. */
    @FunctionalInterface
    public interface AttackOp {
        boolean run(Object entity);
    }

    /** Работа с блоком: 0 — использовать, 1 — начать ломать, 2 — продолжить ломать. */
    @FunctionalInterface
    public interface BlockOp {
        boolean run(int action, int x, int y, int z, int side, boolean offhand);
    }

    /** Точка на хитбоксе цели. */
    @FunctionalInterface
    public interface PointOp {
        double[] run(Object entity, String mode, double maxDistance, boolean throughWalls);
    }

    /** Состояние: 0 — ломается ли блок, 1 — готовность удара. */
    @FunctionalInterface
    public interface StateOp {
        float run(int query);
    }

    private final ActionOp actionOp;
    private final AttackOp attackOp;
    private final BlockOp blockOp;
    private final PointOp pointOp;
    private final StateOp stateOp;

    public Interaction(ActionOp actionOp, AttackOp attackOp, BlockOp blockOp,
                       PointOp pointOp, StateOp stateOp) {
        this.actionOp = actionOp;
        this.attackOp = attackOp;
        this.blockOp = blockOp;
        this.pointOp = pointOp;
        this.stateOp = stateOp;
    }

    /**
     * Бьёт сущность и машет основной рукой.
     *
     * @param entity ванильная {@code Entity}
     * @return прошёл ли удар
     */
    public boolean attack(Entity entity) {
        return attackOp.run(entity);
    }

    /**
     * Точка прицеливания на хитбоксе цели.
     *
     * @param entity       сущность
     * @param mode         {@code "nearest"}, {@code "center"} или {@code "multi"}
     * @param maxDistance  максимальная дистанция в блоках
     * @param throughWalls считать ли точки за блоками
     * @return точка или {@code null}
     */
    public Point attackPoint(Entity entity, String mode, double maxDistance, boolean throughWalls) {
        double[] result = pointOp.run(entity, mode, maxDistance, throughWalls);
        return result == null || result.length < 3 ? null : new Point(result[0], result[1], result[2]);
    }

    /**
     * Ближайшая точка хитбокса цели.
     *
     * @param entity сущность
     * @return точка или {@code null}
     */
    public Point attackPoint(Entity entity) {
        return attackPoint(entity, "nearest", 6d, true);
    }

    public boolean attack(Object entity) {
        return attackOp.run(entity);
    }

    /**
     * Ванильный удар по тому, что под прицелом.
     *
     * @return прошёл ли удар
     */
    public boolean attackCrosshair() {
        return actionOp.run(0, false);
    }

    /**
     * Ванильное использование того, что под прицелом.
     *
     * @param offhand левой рукой
     * @return прошло ли действие
     */
    public boolean useCrosshair(boolean offhand) {
        return actionOp.run(1, offhand);
    }

    /**
     * Начинает использование предмета в руке.
     *
     * @param offhand левой рукой
     * @return прошло ли действие
     */
    public boolean useItem(boolean offhand) {
        return actionOp.run(5, offhand);
    }

    /**
     * Отпускает использование: выстрел из лука, бросок трезубца.
     *
     * @return прошло ли действие
     */
    public boolean stopUsingItem() {
        return actionOp.run(2, false);
    }

    /**
     * Меняет предметы в руках.
     *
     * @return прошло ли действие
     */
    public boolean swapHands() {
        return actionOp.run(3, false);
    }

    /**
     * Мах рукой с отправкой пакета.
     *
     * @param offhand левой рукой
     * @return прошло ли действие
     */
    public boolean swing(boolean offhand) {
        return actionOp.run(4, offhand);
    }

    /**
     * Взаимодействует с гранью блока.
     *
     * @param x       координата блока
     * @param y       координата блока
     * @param z       координата блока
     * @param side    сторона: 0 низ, 1 верх, 2 север, 3 юг, 4 запад, 5 восток
     * @param offhand левой рукой
     * @return прошло ли действие
     */
    public boolean useBlock(int x, int y, int z, int side, boolean offhand) {
        return blockOp.run(0, x, y, z, side, offhand);
    }

    /**
     * Начинает ломать блок.
     *
     * @param x    координата блока
     * @param y    координата блока
     * @param z    координата блока
     * @param side сторона удара
     * @return прошло ли действие
     */
    public boolean startBreaking(int x, int y, int z, int side) {
        return blockOp.run(1, x, y, z, side, false);
    }

    /**
     * Продвигает ломание блока.
     *
     * @param x    координата блока
     * @param y    координата блока
     * @param z    координата блока
     * @param side сторона удара
     * @return сломался ли блок
     */
    public boolean continueBreaking(int x, int y, int z, int side) {
        return blockOp.run(2, x, y, z, side, false);
    }

    /**
     * Отменяет прогресс ломания.
     *
     * @return прошло ли действие
     */
    public boolean stopBreaking() {
        return actionOp.run(6, false);
    }

    /**
     * @return ломается ли блок прямо сейчас
     */
    public boolean breakingBlock() {
        return stateOp.run(0) > 0.5f;
    }

    /**
     * @return готовность удара от 0 до 1
     */
    public float attackCooldown() {
        return stateOp.run(1);
    }

    /**
     * Точка прицеливания на хитбоксе цели.
     *
     * @param entity       ванильная {@code Entity}
     * @param mode         {@code "nearest"}, {@code "center"} или {@code "multi"}
     * @param maxDistance  максимальная дистанция в блоках
     * @param throughWalls считать ли точки за блоками
     * @return точка или {@code null}, если цели нет
     */
    public Point attackPoint(Object entity, String mode, double maxDistance, boolean throughWalls) {
        double[] result = pointOp.run(entity, mode, maxDistance, throughWalls);
        return result == null || result.length < 3 ? null : new Point(result[0], result[1], result[2]);
    }

    /**
     * Ближайшая точка хитбокса цели.
     *
     * @param entity ванильная {@code Entity}
     * @return точка или {@code null}
     */
    public Point attackPoint(Object entity) {
        return attackPoint(entity, "nearest", 6d, true);
    }
}
