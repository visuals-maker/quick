package hex.script.api;

/**
 * Повороты головы. Скрипт кладёт угол в общую очередь клиента, поэтому боевые
 * модули с их приоритетами продолжают работать: приоритет скрипта зажимается
 * так, чтобы аура, кристаллы и прочее всегда были старше.
 *
 * <pre>{@code
 * on("tick", event -> {
 *     if (target() instanceof LivingEntity entity) {
 *         Angles aim = rotations().lookAt(interaction().attackPoint(entity, "multi", 4.5, false));
 *         rotations().apply(aim, 180f, 10);
 *     }
 * });
 * }</pre>
 */
public final class Rotations {

    /** Чтение одного угла: 0 — yaw, 1 — pitch, 2 — отправленный yaw, 3 — отправленный pitch. */
    @FunctionalInterface
    public interface AngleOp {
        float run(int kind);
    }

    /** Угол от глаз до точки. */
    @FunctionalInterface
    public interface LookOp {
        float[] run(double x, double y, double z);
    }

    /** Разница между текущим взглядом и точкой в градусах. */
    @FunctionalInterface
    public interface DeltaOp {
        float run(double x, double y, double z);
    }

    /** Постановка поворота в очередь. */
    @FunctionalInterface
    public interface ApplyOp {
        boolean run(float yaw, float pitch, float speed, int priority);
    }

    /** Текущий приоритет держателя поворота. */
    @FunctionalInterface
    public interface PriorityOp {
        int run();
    }

    private final AngleOp angleOp;
    private final LookOp lookOp;
    private final DeltaOp deltaOp;
    private final ApplyOp applyOp;
    private final PriorityOp priorityOp;

    public Rotations(AngleOp angleOp, LookOp lookOp, DeltaOp deltaOp, ApplyOp applyOp, PriorityOp priorityOp) {
        this.angleOp = angleOp;
        this.lookOp = lookOp;
        this.deltaOp = deltaOp;
        this.applyOp = applyOp;
        this.priorityOp = priorityOp;
    }

    /**
     * @return углы игрока с учётом подмены
     */
    public Angles player() {
        return new Angles(angleOp.run(0), angleOp.run(1));
    }

    /**
     * @return углы из последнего отправленного пакета движения
     */
    public Angles sent() {
        return new Angles(angleOp.run(2), angleOp.run(3));
    }

    /**
     * @param x точка
     * @param y точка
     * @param z точка
     * @return углы от глаз до точки
     */
    public Angles lookAt(double x, double y, double z) {
        float[] result = lookOp.run(x, y, z);
        return new Angles(result[0], result[1]);
    }

    /**
     * @param point точка
     * @return углы от глаз до точки
     */
    public Angles lookAt(Point point) {
        return lookAt(point.x(), point.y(), point.z());
    }

    /**
     * @param x точка
     * @param y точка
     * @param z точка
     * @return расхождение текущего взгляда с направлением на точку, 0..180
     */
    public float angleTo(double x, double y, double z) {
        return deltaOp.run(x, y, z);
    }

    /**
     * @param point точка
     * @return расхождение текущего взгляда с направлением на точку, 0..180
     */
    public float angleTo(Point point) {
        return angleTo(point.x(), point.y(), point.z());
    }

    /**
     * Ставит поворот в очередь клиента на этот тик.
     *
     * @param angles   куда смотреть
     * @param speed    скорость доворота в градусах за тик, 180 и больше — мгновенно
     * @param priority приоритет 0..40; боевые модули клиента всегда старше
     * @return принят ли поворот, {@code false} если его перебил кто-то старше
     */
    public boolean apply(Angles angles, float speed, int priority) {
        return applyOp.run(angles.yaw(), angles.pitch(), speed, priority);
    }

    /**
     * Ставит поворот мгновенно с приоритетом по умолчанию.
     *
     * @param angles куда смотреть
     * @return принят ли поворот
     */
    public boolean apply(Angles angles) {
        return apply(angles, 180f, 10);
    }

    /**
     * @return приоритет того, кто сейчас держит поворот
     */
    public int priority() {
        return priorityOp.run();
    }
}
