package hex.script.api;

/**
 * Точка в мире.
 */
public final class Point {

    private final double x;
    private final double y;
    private final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return координата X
     */
    public double x() {
        return x;
    }

    /**
     * @return координата Y
     */
    public double y() {
        return y;
    }

    /**
     * @return координата Z
     */
    public double z() {
        return z;
    }

    /**
     * @param other вторая точка
     * @return расстояние между точками
     */
    public double distance(Point other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}
