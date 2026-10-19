package hex.script.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Предсказание движения игрока и полёта снарядов. Движение считается на
 * теневом игроке клиента, снаряды — его же баллистикой.
 *
 * <pre>{@code
 * Motion landing = prediction().after(10);
 * if (landing != null) {
 *     Point at = landing.position();
 *     render.box(at.x() - 0.3, at.y(), at.z() - 0.3,
 *             at.x() + 0.3, at.y() + 0.1, at.z() + 0.3, 0xFFFF3B30, true);
 * }
 * }</pre>
 */
public final class Prediction {

    /** Симуляция движения: на каждый тик девять чисел. */
    @FunctionalInterface
    public interface MoveOp {
        double[] run(int ticks);
    }

    /** Траектория снаряда: флаг попадания, точка попадания и точки пути. */
    @FunctionalInterface
    public interface ProjectileOp {
        double[] run(String kind, float yaw, float pitch, float power);
    }

    private static final int STRIDE = 9;

    private final MoveOp moveOp;
    private final ProjectileOp projectileOp;

    public Prediction(MoveOp moveOp, ProjectileOp projectileOp) {
        this.moveOp = moveOp;
        this.projectileOp = projectileOp;
    }

    /**
     * Состояние игрока через заданное число тиков.
     *
     * @param ticks сколько тиков симулировать, 1..100
     * @return последний тик симуляции или {@code null} вне мира
     */
    public Motion after(int ticks) {
        List<Motion> path = path(ticks);
        return path.isEmpty() ? null : path.get(path.size() - 1);
    }

    /**
     * Полный путь по тикам.
     *
     * @param ticks сколько тиков симулировать, 1..100
     * @return по записи на тик, нулевой тик не входит
     */
    public List<Motion> path(int ticks) {
        double[] raw = moveOp.run(ticks);
        if (raw == null || raw.length < STRIDE) {
            return List.of();
        }
        List<Motion> result = new ArrayList<>(raw.length / STRIDE);
        for (int i = 0; i + STRIDE <= raw.length; i += STRIDE) {
            result.add(new Motion(raw[i], raw[i + 1], raw[i + 2], raw[i + 3], raw[i + 4], raw[i + 5],
                    raw[i + 6] > 0.5d, raw[i + 7] > 0.5d, raw[i + 8] > 0.5d));
        }
        return result;
    }

    /**
     * Траектория снаряда от глаз игрока.
     *
     * @param kind  {@code "arrow"}, {@code "trident"}, {@code "pearl"}, {@code "snowball"} или {@code "potion"}
     * @param aim   куда целиться; {@code null} — последний отправленный поворот
     * @param power сила запуска, 0 — штатная для этого вида
     * @return траектория
     */
    public Trajectory projectile(String kind, Angles aim, float power) {
        double[] raw = projectileOp.run(kind,
                aim == null ? Float.NaN : aim.yaw(),
                aim == null ? Float.NaN : aim.pitch(), power);
        return new Trajectory(raw);
    }

    /**
     * Траектория снаряда по текущему взгляду и штатной силе.
     *
     * @param kind вид снаряда
     * @return траектория
     */
    public Trajectory projectile(String kind) {
        return projectile(kind, null, 0f);
    }
}
