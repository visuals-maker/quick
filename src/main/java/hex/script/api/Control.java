package hex.script.api;

/**
 * Управление игроком и кусок настроек игры. Всё зовётся из клиентского
 * потока — то есть из событий и колбэков скрипта.
 * <p>
 * Бег, присед, прыжок и скорость действуют один тик: игра собирает ввод и
 * движение заново каждый тик, поэтому для постоянного эффекта зови их каждый
 * тик. Запись идёт после модулей клиента, так что явная просьба скрипта
 * побеждает.
 *
 * <pre>{@code
 * on("tick", event -> {
 *     control().sprinting(true);
 *     if (player.onGround()) {
 *         control().jump();
 *     }
 * });
 * }</pre>
 */
public final class Control {

    /** Ввод на тик: 0 — бег, 1 — присед. */
    @FunctionalInterface
    public interface FlagOp {
        boolean run(int flag, boolean value, boolean write);
    }

    /** Прыжок с земли. */
    @FunctionalInterface
    public interface JumpOp {
        boolean run();
    }

    /** Настройка: 0 — вид камеры, 1 — чувствительность, 2 — масштаб интерфейса. */
    @FunctionalInterface
    public interface ValueOp {
        double run(int query, double value, boolean write);
    }

    /** Скорость игрока на тик. */
    @FunctionalInterface
    public interface MotionOp {
        void run(double x, double y, double z);
    }

    public static final int FIRST_PERSON = 0;
    public static final int THIRD_PERSON_BACK = 1;
    public static final int THIRD_PERSON_FRONT = 2;

    private final FlagOp flagOp;
    private final JumpOp jumpOp;
    private final ValueOp valueOp;
    private final MotionOp motionOp;

    public Control(FlagOp flagOp, JumpOp jumpOp, ValueOp valueOp, MotionOp motionOp) {
        this.flagOp = flagOp;
        this.jumpOp = jumpOp;
        this.valueOp = valueOp;
        this.motionOp = motionOp;
    }

    /**
     * Держит бег в этом тике, как зажатая клавиша бега.
     *
     * @param value бежать ли
     */
    public void sprinting(boolean value) {
        flagOp.run(0, value, true);
    }

    /**
     * @return бежит ли игрок
     */
    public boolean sprinting() {
        return flagOp.run(0, false, false);
    }

    /**
     * Держит присед в этом тике, как зажатый шифт.
     *
     * @param value приседать ли
     */
    public void sneaking(boolean value) {
        flagOp.run(1, value, true);
    }

    /**
     * @return приседает ли игрок
     */
    public boolean sneaking() {
        return flagOp.run(1, false, false);
    }

    /**
     * Прыгает в этом тике. Вне земли ничего не делает.
     *
     * @return пойдёт ли прыжок
     */
    public boolean jump() {
        return jumpOp.run();
    }

    /**
     * Ставит скорость, с которой игрок сдвинется в этом тике. Применяется перед
     * движением, когда ввод уже собран: сдвиг за тик — эта скорость плюс
     * небольшое ускорение от клавиш. Гравитация и сопротивление воздуха ложатся
     * уже после сдвига, поэтому чтобы держать скорость, зови каждый тик —
     * удобнее всего из события {@code "input"}, где есть и сам ввод. Прыжок с
     * земли в том же тике перекрывает скорость по Y.
     *
     * <pre>{@code
     * on("input", event -> control().motion(0d, 0d, 0d));
     * }</pre>
     *
     * @param x скорость по X в блоках за тик
     * @param y скорость по Y в блоках за тик
     * @param z скорость по Z в блоках за тик
     */
    public void motion(double x, double y, double z) {
        motionOp.run(x, y, z);
    }

    /**
     * @return вид камеры: {@link #FIRST_PERSON}, {@link #THIRD_PERSON_BACK}
     * или {@link #THIRD_PERSON_FRONT}
     */
    public int perspective() {
        return (int) valueOp.run(0, 0d, false);
    }

    /**
     * Ставит вид камеры.
     *
     * @param value один из {@link #FIRST_PERSON}, {@link #THIRD_PERSON_BACK},
     *              {@link #THIRD_PERSON_FRONT}
     */
    public void perspective(int value) {
        valueOp.run(0, value, true);
    }

    /**
     * @return от первого ли лица
     */
    public boolean firstPerson() {
        return perspective() == FIRST_PERSON;
    }

    /**
     * @return чувствительность мыши от 0 до 1
     */
    public double sensitivity() {
        return valueOp.run(1, 0d, false);
    }

    /**
     * Ставит чувствительность мыши. Значение зажимается в 0..1 и назад при
     * выключении скрипта не возвращается.
     *
     * @param value чувствительность
     */
    public void sensitivity(double value) {
        valueOp.run(1, value, true);
    }

    /**
     * Масштаб интерфейса окна. На него делят пиксели {@link Render2D}, чтобы
     * получить координаты ванильного интерфейса.
     *
     * @return масштаб, например 2.0
     */
    public double guiScale() {
        return valueOp.run(2, 0d, false);
    }
}
