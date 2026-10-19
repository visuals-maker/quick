package hex.script.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Путевые точки клиента. С длительностью метка временная, без неё —
 * постоянная и попадает в конфиг путевых точек.
 *
 * <pre>{@code
 * waypoints().add("Base", player.getX(), player.getY(), player.getZ());
 * waypoints().add("Drop", x, y, z, 20 * 60);
 * waypoints().remove("Drop");
 * }</pre>
 */
public final class Waypoints {

    /** Постановка метки; {@code ticks} меньше 1 — постоянная. */
    @FunctionalInterface
    public interface AddOp {
        void run(String name, double x, double y, double z, long ticks);
    }

    /** Удаление меток по имени; возвращает количество удалённых. */
    @FunctionalInterface
    public interface RemoveOp {
        int run(String name);
    }

    /** Снимок меток: имя и координаты подряд. */
    @FunctionalInterface
    public interface ListOp {
        Object[] run();
    }

    private final AddOp addOp;
    private final RemoveOp removeOp;
    private final ListOp listOp;

    public Waypoints(AddOp addOp, RemoveOp removeOp, ListOp listOp) {
        this.addOp = addOp;
        this.removeOp = removeOp;
        this.listOp = listOp;
    }

    /**
     * Постоянная метка.
     *
     * @param name имя метки
     * @param x    позиция
     * @param y    позиция
     * @param z    позиция
     */
    public void add(String name, double x, double y, double z) {
        addOp.run(name, x, y, z, 0L);
    }

    /**
     * Временная метка.
     *
     * @param name  имя метки
     * @param x     позиция
     * @param y     позиция
     * @param z     позиция
     * @param ticks сколько тиков держать
     */
    public void add(String name, double x, double y, double z, long ticks) {
        addOp.run(name, x, y, z, Math.max(1L, ticks));
    }

    /**
     * Убирает все метки с таким именем.
     *
     * @param name имя метки
     * @return сколько меток убрано
     */
    public int remove(String name) {
        return removeOp.run(name);
    }

    /**
     * @return снимок всех текущих меток
     */
    public List<Waypoint> all() {
        Object[] raw = listOp.run();
        if (raw == null) {
            return List.of();
        }
        List<Waypoint> result = new ArrayList<>(raw.length / 5);
        for (int i = 0; i + 5 <= raw.length; i += 5) {
            result.add(new Waypoint(String.valueOf(raw[i]),
                    number(raw[i + 1]), number(raw[i + 2]), number(raw[i + 3]),
                    Boolean.TRUE.equals(raw[i + 4])));
        }
        return result;
    }

    private double number(Object value) {
        return value instanceof Number number ? number.doubleValue() : 0d;
    }
}
