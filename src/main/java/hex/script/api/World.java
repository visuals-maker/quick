package hex.script.api;

/**
 * Мир вокруг игрока: блоки, лучи, погода и время.
 *
 * <pre>{@code
 * Block looking = world().raycastBlock(5.0);
 * if (looking != null && looking.is("chest")) {
 *     chat().print("chest on " + looking.x() + " " + looking.y() + " " + looking.z());
 * }
 * }</pre>
 *
 * <p>Для сканирования мира снимки не нужны: {@link #air(int, int, int)} и соседи
 * отвечают одним чтением блока без единой аллокации, а {@link #scan(int, int, int, int, int)}
 * и {@link #find(int, int, int, int, String)} обходят целую область за один проход
 * по секциям чанков.</p>
 */
public final class World {

    /** Запрос «воздух ли» для {@link #scan(int, int, int, int, int)}. */
    public static final int AIR = 0;

    /** Запрос «есть ли коллизия» для {@link #scan(int, int, int, int, int)}. */
    public static final int SOLID = 1;

    /** Запрос «жидкость ли» для {@link #scan(int, int, int, int, int)}. */
    public static final int LIQUID = 2;

    /** Запрос «можно ли ставить поверх» для {@link #scan(int, int, int, int, int)}. */
    public static final int REPLACEABLE = 3;

    /** Потолок радиуса {@link #scan(int, int, int, int, int)}: 65×65×65 = 274625 позиций. */
    public static final int SCAN_RADIUS = 32;

    /** Потолок радиуса {@link #find(int, int, int, int, String)}: 129×129×129 позиций. */
    public static final int FIND_RADIUS = 64;

    /** Потолок числа находок {@link #find(int, int, int, int, String)}. */
    public static final int FIND_LIMIT = 65536;

    /** Число находок {@link #find(int, int, int, int, String)} без явного предела. */
    public static final int FIND_DEFAULT = 4096;

    private static final int[] NO_POSITIONS = new int[0];

    /** Снимок блока по координатам. */
    @FunctionalInterface
    public interface BlockOp {
        Block run(int x, int y, int z);
    }

    /** Число: 0 — время суток, 1 — общее время, 2 — низ мира, 3 — верх мира. */
    @FunctionalInterface
    public interface NumberOp {
        double run(int query);
    }

    /** Флаг: 0 — мир загружен, 1 — дождь, 2 — гроза. */
    @FunctionalInterface
    public interface FlagOp {
        boolean run(int query);
    }

    /** Имя измерения. */
    @FunctionalInterface
    public interface TextOp {
        String run();
    }

    /** Луч по блокам: массив из координат и стороны, либо {@code null}. */
    @FunctionalInterface
    public interface RayOp {
        double[] run(double distance, boolean liquids);
    }

    /** Свойство блока без снимка: {@link #AIR}, {@link #SOLID}, {@link #LIQUID}, {@link #REPLACEABLE}. */
    @FunctionalInterface
    public interface QueryOp {
        boolean run(int x, int y, int z, int query);
    }

    /** Сравнение id блока без сборки снимка. */
    @FunctionalInterface
    public interface MatchOp {
        boolean run(int x, int y, int z, String id);
    }

    /** Пакетный обход куба: заполняет флаги свойства и отдаёт тот же массив. */
    @FunctionalInterface
    public interface ScanOp {
        boolean[] run(int x, int y, int z, int radius, int query, boolean[] into);
    }

    /** Поиск блоков по id: тройки координат подряд. */
    @FunctionalInterface
    public interface FindOp {
        int[] run(int x, int y, int z, int radius, String id, int limit);
    }

    /** Колонка: 0 — верхний непустой блок, 1 — загружен ли чанк. */
    @FunctionalInterface
    public interface ColumnOp {
        int run(int x, int z, int query);
    }

    private final BlockOp blockOp;
    private final NumberOp numberOp;
    private final FlagOp flagOp;
    private final TextOp textOp;
    private final RayOp rayOp;
    private final QueryOp queryOp;
    private final MatchOp matchOp;
    private final ScanOp scanOp;
    private final FindOp findOp;
    private final ColumnOp columnOp;

    public World(BlockOp blockOp, NumberOp numberOp, FlagOp flagOp, TextOp textOp, RayOp rayOp) {
        this(blockOp, numberOp, flagOp, textOp, rayOp, null, null, null, null, null);
    }

    public World(BlockOp blockOp, NumberOp numberOp, FlagOp flagOp, TextOp textOp, RayOp rayOp,
                 QueryOp queryOp, MatchOp matchOp, ScanOp scanOp, FindOp findOp, ColumnOp columnOp) {
        this.blockOp = blockOp;
        this.numberOp = numberOp;
        this.flagOp = flagOp;
        this.textOp = textOp;
        this.rayOp = rayOp;
        this.queryOp = queryOp;
        this.matchOp = matchOp;
        this.scanOp = scanOp;
        this.findOp = findOp;
        this.columnOp = columnOp;
    }

    /**
     * Блок по координатам. В незагруженном мире отдаёт воздух.
     *
     * <p>Снимок — это аллокация и разбор всех свойств блока сразу. Если нужно
     * одно свойство, дешевле {@link #air(int, int, int)}, {@link #solid(int, int, int)},
     * {@link #liquid(int, int, int)}, {@link #replaceable(int, int, int)} или
     * {@link #is(int, int, int, String)}.</p>
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return снимок блока
     */
    public Block block(int x, int y, int z) {
        Block block = blockOp.run(x, y, z);
        return block == null ? new Block() : block;
    }

    /**
     * Блок по позиции: дробные координаты округляются вниз, как в игре.
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return снимок блока
     */
    public Block block(double x, double y, double z) {
        return block(floor(x), floor(y), floor(z));
    }

    /**
     * @param point позиция
     * @return снимок блока
     */
    public Block block(Point point) {
        return point == null ? new Block() : block(point.x(), point.y(), point.z());
    }

    /**
     * Воздух ли блок. Одно чтение мира без аллокаций — годится для скана.
     *
     * <p>Незагруженный чанк читается как воздух, ровно как у {@link #block(int, int, int)}.
     * Отличить пустоту от «нет данных» можно через {@link #loaded(int, int)}.</p>
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return воздух ли
     */
    public boolean air(int x, int y, int z) {
        return queryOp == null ? block(x, y, z).air() : queryOp.run(x, y, z, AIR);
    }

    /**
     * Есть ли у блока коллизия. Одно чтение мира без аллокаций.
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return есть ли коллизия
     */
    public boolean solid(int x, int y, int z) {
        return queryOp == null ? block(x, y, z).solid() : queryOp.run(x, y, z, SOLID);
    }

    /**
     * Жидкость ли в блоке. Одно чтение мира без аллокаций.
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return жидкость ли
     */
    public boolean liquid(int x, int y, int z) {
        return queryOp == null ? block(x, y, z).liquid() : queryOp.run(x, y, z, LIQUID);
    }

    /**
     * Можно ли поставить блок поверх этого. Одно чтение мира без аллокаций.
     *
     * @param x координата
     * @param y координата
     * @param z координата
     * @return можно ли заменить
     */
    public boolean replaceable(int x, int y, int z) {
        return queryOp == null ? block(x, y, z).replaceable() : queryOp.run(x, y, z, REPLACEABLE);
    }

    /**
     * Сравнивает id блока без сборки снимка: строка разбирается один раз и
     * дальше берётся из кеша, сравнение идёт по самому блоку.
     *
     * @param x     координата
     * @param y     координата
     * @param z     координата
     * @param id    {@code "obsidian"} или {@code "minecraft:obsidian"}
     * @return совпал ли id
     */
    public boolean is(int x, int y, int z, String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return matchOp == null ? block(x, y, z).is(id) : matchOp.run(x, y, z, id);
    }

    /**
     * Сторона куба флагов для радиуса: {@code radius * 2 + 1}.
     *
     * @param radius радиус, выше {@link #SCAN_RADIUS} обрезается
     * @return сторона куба в блоках
     */
    public static int side(int radius) {
        return clamp(radius, SCAN_RADIUS) * 2 + 1;
    }

    /**
     * Длина массива флагов для радиуса.
     *
     * @param radius радиус, выше {@link #SCAN_RADIUS} обрезается
     * @return число позиций в кубе
     */
    public static int volume(int radius) {
        int side = side(radius);
        return side * side * side;
    }

    /**
     * Индекс позиции в массиве {@link #scan(int, int, int, int, int)}.
     * Радиус обрезается тем же пределом, что и в самом скане, поэтому индексы
     * всегда сходятся с выданным массивом.
     *
     * @param radius радиус скана
     * @param dx     смещение от центра, {@code -radius..radius}
     * @param dy     смещение от центра, {@code -radius..radius}
     * @param dz     смещение от центра, {@code -radius..radius}
     * @return индекс или -1, если смещение вне куба
     */
    public static int index(int radius, int dx, int dy, int dz) {
        int limited = clamp(radius, SCAN_RADIUS);
        if (dx < -limited || dx > limited || dy < -limited || dy > limited || dz < -limited || dz > limited) {
            return -1;
        }
        int side = limited * 2 + 1;
        return ((dy + limited) * side + dz + limited) * side + dx + limited;
    }

    /**
     * Флаг из массива {@link #scan(int, int, int, int, int)} по смещению от центра.
     * Вне куба отдаёт {@code false}, за границы не выходит.
     *
     * @param flags  массив от скана
     * @param radius радиус скана
     * @param dx     смещение от центра
     * @param dy     смещение от центра
     * @param dz     смещение от центра
     * @return значение флага
     */
    public static boolean at(boolean[] flags, int radius, int dx, int dy, int dz) {
        int slot = index(radius, dx, dy, dz);
        return flags != null && slot >= 0 && slot < flags.length && flags[slot];
    }

    /**
     * Свойство всех блоков куба за один проход.
     *
     * <p>Стоимость: один обход региона по секциям чанков — на позицию приходится
     * одно чтение палитры и одна запись в массив, без {@code BlockPos} и строк.
     * Целиком воздушные секции и незагруженные чанки не читаются вовсе, их
     * позиции получают ответ воздуха, поэтому на открытом небе скан почти
     * бесплатный. Это в разы дешевле того же куба через {@link #block(int, int, int)}.</p>
     *
     * <p>Раскладка массива описана в {@link #index(int, int, int, int)}, размер —
     * в {@link #volume(int)}. Радиус выше {@link #SCAN_RADIUS} обрезается.</p>
     *
     * <pre>{@code
     * boolean[] air = world().scan(x, y, z, 16, World.AIR);
     * boolean open = World.at(air, 16, 1, 0, 0) || World.at(air, 16, -1, 0, 0);
     * }</pre>
     *
     * @param x      центр куба
     * @param y      центр куба
     * @param z      центр куба
     * @param radius радиус куба в блоках
     * @param query  {@link #AIR}, {@link #SOLID}, {@link #LIQUID} или {@link #REPLACEABLE}
     * @return массив флагов длиной {@link #volume(int)}
     */
    public boolean[] scan(int x, int y, int z, int radius, int query) {
        return scan(x, y, z, radius, query, null);
    }

    /**
     * То же, что {@link #scan(int, int, int, int, int)}, но с переиспользованием массива:
     * скан ничего не аллоцирует, если переданный массив достаточно велик.
     *
     * @param x      центр куба
     * @param y      центр куба
     * @param z      центр куба
     * @param radius радиус куба в блоках
     * @param query  {@link #AIR}, {@link #SOLID}, {@link #LIQUID} или {@link #REPLACEABLE}
     * @param into   массив от прошлого скана длиной не меньше {@link #volume(int)}
     *               или {@code null}
     * @return {@code into}, если он подошёл, иначе новый массив
     */
    public boolean[] scan(int x, int y, int z, int radius, int query, boolean[] into) {
        int limited = clamp(radius, SCAN_RADIUS);
        int total = volume(limited);
        if (scanOp != null) {
            boolean[] filled = scanOp.run(x, y, z, limited, query, into);
            return filled == null ? new boolean[total] : filled;
        }

        boolean[] result = into != null && into.length >= total ? into : new boolean[total];
        int side = limited * 2 + 1;
        for (int dy = -limited; dy <= limited; dy++) {
            for (int dz = -limited; dz <= limited; dz++) {
                int row = ((dy + limited) * side + dz + limited) * side + limited;
                for (int dx = -limited; dx <= limited; dx++) {
                    result[row + dx] = single(x + dx, y + dy, z + dz, query);
                }
            }
        }
        return result;
    }

    /**
     * Позиции блоков с заданным id в кубе, до {@link #FIND_DEFAULT} штук.
     *
     * @param x      центр куба
     * @param y      центр куба
     * @param z      центр куба
     * @param radius радиус куба, выше {@link #FIND_RADIUS} обрезается
     * @param id     {@code "obsidian"} или {@code "minecraft:obsidian"}
     * @return тройки {@code x, y, z} подряд; пустой массив, если ничего не нашлось
     */
    public int[] find(int x, int y, int z, int radius, String id) {
        return find(x, y, z, radius, id, FIND_DEFAULT);
    }

    /**
     * Позиции блоков с заданным id в кубе.
     *
     * <p>Стоимость: один обход региона по секциям чанков. Id разбирается один раз
     * до цикла, внутри идёт сравнение по самому блоку; воздушные секции и
     * незагруженные чанки пропускаются целиком. Аллоцируется только результат.</p>
     *
     * <pre>{@code
     * int[] found = world().find(x, y, z, 24, "diamond_ore", 64);
     * for (int i = 0; i < found.length; i += 3) {
     *     render.box(found[i], found[i + 1], found[i + 2], 0xFF00E5FF);
     * }
     * }</pre>
     *
     * @param x      центр куба
     * @param y      центр куба
     * @param z      центр куба
     * @param radius радиус куба, выше {@link #FIND_RADIUS} обрезается
     * @param id     {@code "obsidian"} или {@code "minecraft:obsidian"}
     * @param limit  сколько находок хватит, выше {@link #FIND_LIMIT} обрезается
     * @return тройки {@code x, y, z} подряд; пустой массив, если ничего не нашлось
     */
    public int[] find(int x, int y, int z, int radius, String id, int limit) {
        if (findOp == null || id == null || id.isEmpty()) {
            return NO_POSITIONS;
        }
        int[] positions = findOp.run(x, y, z, clamp(radius, FIND_RADIUS), id, clamp(limit, FIND_LIMIT));
        return positions == null ? NO_POSITIONS : positions;
    }

    /**
     * Верхний непустой блок в колонке по карте высот — без обхода блоков.
     *
     * @param x координата
     * @param z координата
     * @return координата верхнего блока; низ мира, если чанк не загружен
     */
    public int height(int x, int z) {
        return columnOp == null ? bottom() : columnOp.run(x, z, 0);
    }

    /**
     * Загружен ли чанк с этой колонкой. Незагруженный читается как воздух,
     * так что проверять стоит до скана, а не после.
     *
     * @param x координата
     * @param z координата
     * @return загружен ли чанк
     */
    public boolean loaded(int x, int z) {
        return columnOp == null ? loaded() : columnOp.run(x, z, 1) != 0;
    }

    /**
     * @return загружен ли мир
     */
    public boolean loaded() {
        return flagOp.run(0);
    }

    /**
     * @return идёт ли дождь
     */
    public boolean raining() {
        return flagOp.run(1);
    }

    /**
     * @return идёт ли гроза
     */
    public boolean thundering() {
        return flagOp.run(2);
    }

    /**
     * @return измерение, например {@code "minecraft:overworld"}
     */
    public String dimension() {
        String value = textOp.run();
        return value == null ? "" : value;
    }

    /**
     * Время суток по игровому счётчику. В 26.2 клиент отдельного времени суток
     * не хранит, поэтому это остаток игрового времени от суток: с обычным
     * циклом совпадает, а на сервере со сдвинутым временем разойдётся.
     *
     * @return время суток в тиках, 0..24000
     */
    public long dayTime() {
        return (long) numberOp.run(0);
    }

    /**
     * @return общее время мира в тиках
     */
    public long time() {
        return (long) numberOp.run(1);
    }

    /**
     * @return нижняя граница мира
     */
    public int bottom() {
        return (int) numberOp.run(2);
    }

    /**
     * @return верхняя граница мира
     */
    public int top() {
        return (int) numberOp.run(3);
    }

    /**
     * Луч от глаз игрока до первого блока.
     *
     * @param distance максимальная дистанция в блоках
     * @return блок или {@code null}, если луч ни во что не упёрся
     */
    public Block raycastBlock(double distance) {
        return raycastBlock(distance, false);
    }

    /**
     * Луч от глаз игрока до первого блока.
     *
     * @param distance максимальная дистанция в блоках
     * @param liquids  считать ли жидкости препятствием
     * @return блок или {@code null}
     */
    public Block raycastBlock(double distance, boolean liquids) {
        double[] hit = rayOp.run(distance, liquids);
        return hit == null ? null : block((int) hit[0], (int) hit[1], (int) hit[2]);
    }

    /**
     * Сторона блока, в которую упёрся луч.
     *
     * @param distance максимальная дистанция в блоках
     * @return сторона как у {@link Packets}: 0 низ, 1 верх, 2 север, 3 юг,
     * 4 запад, 5 восток; -1 при промахе
     */
    public int raycastSide(double distance) {
        double[] hit = rayOp.run(distance, false);
        return hit == null ? -1 : (int) hit[3];
    }

    private boolean single(int x, int y, int z, int query) {
        return switch (query) {
            case SOLID -> solid(x, y, z);
            case LIQUID -> liquid(x, y, z);
            case REPLACEABLE -> replaceable(x, y, z);
            default -> air(x, y, z);
        };
    }

    private static int clamp(int value, int max) {
        if (value < 0) {
            return 0;
        }
        return Math.min(value, max);
    }

    private static int floor(double value) {
        int result = (int) value;
        return value < result ? result - 1 : result;
    }
}
