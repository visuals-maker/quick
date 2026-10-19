package hex.script.api;

/**
 * Одна путевая точка клиента.
 */
public final class Waypoint {

    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final boolean permanent;

    Waypoint(String name, double x, double y, double z, boolean permanent) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.permanent = permanent;
    }

    /**
     * @return имя метки
     */
    public String name() {
        return name;
    }

    /**
     * @return позиция метки
     */
    public Point position() {
        return new Point(x, y, z);
    }

    /**
     * @return сохранена ли метка в конфиг
     */
    public boolean permanent() {
        return permanent;
    }

    @Override
    public String toString() {
        return name + " " + x + ", " + y + ", " + z;
    }
}
