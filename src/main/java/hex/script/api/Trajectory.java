package hex.script.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Полёт снаряда: точки по тикам и место попадания.
 */
public final class Trajectory {

    private final boolean lands;
    private final Point landing;
    private final List<Point> points;

    Trajectory(double[] raw) {
        List<Point> collected = new ArrayList<>();
        boolean hit = false;
        Point at = null;

        if (raw != null && raw.length >= 4) {
            hit = raw[0] > 0.5d;
            if (hit) {
                at = new Point(raw[1], raw[2], raw[3]);
            }
            for (int i = 4; i + 3 <= raw.length; i += 3) {
                collected.add(new Point(raw[i], raw[i + 1], raw[i + 2]));
            }
        }

        this.lands = hit;
        this.landing = at;
        this.points = List.copyOf(collected);
    }

    /**
     * @return точки траектории, по одной на тик
     */
    public List<Point> points() {
        return points;
    }

    /**
     * @return попал ли снаряд во что-нибудь
     */
    public boolean lands() {
        return lands;
    }

    /**
     * @return точка попадания или {@code null}
     */
    public Point landing() {
        return landing;
    }

    /**
     * @return сколько тиков летит
     */
    public int flightTicks() {
        return Math.max(0, points.size() - 1);
    }
}
