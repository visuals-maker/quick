package hex.script.api;

/**
 * Доступ к сущностям мира. Отдаёт {@link Entity} — обёртку над сетевым id.
 *
 * <pre>{@code
 * for (Entity entity : entities().players(16.0)) {
 *     if (!entity.self() && !entity.friend()) {
 *         render.entityBox(entity, 0xFFFF3B30, true);
 *     }
 * }
 * }</pre>
 */
public final class Entities {

    /** Число по запросу. */
    @FunctionalInterface
    public interface NumberOp {
        double run(int id, int query);
    }

    /** Флаг по запросу. */
    @FunctionalInterface
    public interface FlagOp {
        boolean run(int id, int query);
    }

    /** Строка по запросу. */
    @FunctionalInterface
    public interface TextOp {
        String run(int id, int query);
    }

    /** Оформленный текст по запросу. */
    @FunctionalInterface
    public interface StyledOp {
        Text run(int id, int query);
    }

    /** Снимок предмета: слот -1 основная рука, -2 левая, 0..3 броня. */
    @FunctionalInterface
    public interface ItemOp {
        Item run(int id, int slot);
    }

    /** Список id: 0 — все, 1 — игроки, 2 — живые. */
    @FunctionalInterface
    public interface ListOp {
        int[] run(int query, double range);
    }

    /** Сглаженная позиция: {@code {x, y, z}} или {@code null}. */
    @FunctionalInterface
    public interface InterpolateOp {
        double[] run(int id, float delta);
    }

    private static final Entity[] EMPTY = new Entity[0];

    private final NumberOp numberOp;
    private final FlagOp flagOp;
    private final TextOp textOp;
    private final StyledOp styledOp;
    private final ItemOp itemOp;
    private final ListOp listOp;
    private final InterpolateOp interpolateOp;

    public Entities(NumberOp numberOp, FlagOp flagOp, TextOp textOp,
                    ItemOp itemOp, ListOp listOp, InterpolateOp interpolateOp) {
        this(numberOp, flagOp, textOp, null, itemOp, listOp, interpolateOp);
    }

    public Entities(NumberOp numberOp, FlagOp flagOp, TextOp textOp, StyledOp styledOp,
                    ItemOp itemOp, ListOp listOp, InterpolateOp interpolateOp) {
        this.numberOp = numberOp;
        this.flagOp = flagOp;
        this.textOp = textOp;
        this.styledOp = styledOp;
        this.itemOp = itemOp;
        this.listOp = listOp;
        this.interpolateOp = interpolateOp;
    }

    double number(int id, int query) {
        return numberOp.run(id, query);
    }

    boolean flag(int id, int query) {
        return flagOp.run(id, query);
    }

    String text(int id, int query) {
        String value = textOp.run(id, query);
        return value == null ? "" : value;
    }

    Text styled(int id, int query) {
        if (styledOp == null) {
            return Text.of(text(id, Entity.NAME));
        }
        Text value = styledOp.run(id, query);
        return value == null ? Text.empty() : value;
    }

    Item item(int id, int slot) {
        Item value = itemOp.run(id, slot);
        return value == null ? new Item() : value;
    }

    double[] interpolate(int id, float delta) {
        return interpolateOp.run(id, delta);
    }

    /**
     * Оборачивает сущность по сетевому id. Проверять существование не нужно —
     * у отсутствующей {@link Entity#valid()} вернёт {@code false}.
     *
     * @param id сетевой id
     * @return обёртка
     */
    public Entity of(int id) {
        return new Entity(id, this);
    }

    /**
     * Все сущности вокруг игрока.
     *
     * @param range радиус в блоках
     * @return сущности, ближние первыми
     */
    public Entity[] all(double range) {
        return wrap(listOp.run(0, range));
    }

    /**
     * Игроки вокруг, кроме себя.
     *
     * @param range радиус в блоках
     * @return игроки, ближние первыми
     */
    public Entity[] players(double range) {
        return wrap(listOp.run(1, range));
    }

    /**
     * Живые существа вокруг, кроме себя.
     *
     * @param range радиус в блоках
     * @return существа, ближние первыми
     */
    public Entity[] living(double range) {
        return wrap(listOp.run(2, range));
    }

    /**
     * Ближайший подходящий враг — то же, что видит боевой модуль клиента.
     *
     * @return цель или {@code null}
     */
    public Entity target() {
        int[] result = listOp.run(3, 0d);
        return result == null || result.length == 0 ? null : of(result[0]);
    }

    /**
     * Сущность под прицелом.
     *
     * @return сущность или {@code null}
     */
    public Entity crosshair() {
        int[] result = listOp.run(4, 0d);
        return result == null || result.length == 0 ? null : of(result[0]);
    }

    private Entity[] wrap(int[] ids) {
        if (ids == null || ids.length == 0) {
            return EMPTY;
        }
        Entity[] result = new Entity[ids.length];
        for (int i = 0; i < ids.length; i++) {
            result[i] = of(ids[i]);
        }
        return result;
    }
}
