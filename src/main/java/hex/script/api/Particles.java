package hex.script.api;

/**
 * Частицы мира. Тип задаётся строковым id из реестра игры —
 * {@code "minecraft:flame"}, {@code "minecraft:end_rod"}, {@code "minecraft:crit"}.
 * Пространство имён можно опустить: {@code "flame"} читается как
 * {@code "minecraft:flame"}.
 * <p>
 * Частицы чисто клиентские: их видишь только ты, на сервер ничего не уходит.
 * <p>
 * {@link #spawn} работает с типами без параметров. Типам, которым нужны
 * данные, есть свои методы: {@link #dust} для цветной пыли заданного размера,
 * {@link #colored} для цветных частиц вроде {@code entity_effect},
 * {@link #block} и {@link #item} для осколков блока и предмета. Тип, не
 * подходящий выбранному методу, и неизвестный id дают {@code false} без
 * падения.
 *
 * <pre>{@code
 * Entity target = entities().target();
 * if (target != null) {
 *     particles().spawn("minecraft:crit", target.x(), target.y() + 1, target.z(), 8, 0.3);
 * }
 * }</pre>
 *
 * Вызов из чужого потока откладывается на главный поток игры.
 */
public final class Particles {

    /**
     * Создание частиц. {@code kind}: 0 — без параметров, 1 — цветная пыль,
     * 2 — пыль с переходом цвета, 3 — цветная частица, 4 — осколок блока,
     * 5 — осколок предмета.
     */
    @FunctionalInterface
    public interface SpawnOp {
        boolean run(String id, int kind, int first, int second, float scale, String data,
                    double x, double y, double z, double dx, double dy, double dz,
                    int count, double spread);
    }

    private static final int SIMPLE = 0;
    private static final int DUST = 1;
    private static final int DUST_FADE = 2;
    private static final int COLORED = 3;
    private static final int BLOCK = 4;
    private static final int ITEM = 5;

    private final SpawnOp spawnOp;

    public Particles(SpawnOp spawnOp) {
        this.spawnOp = spawnOp;
    }

    /**
     * Одна неподвижная частица в точке.
     *
     * @param id id типа частицы из реестра игры
     * @param x  координата точки
     * @param y  координата точки
     * @param z  координата точки
     * @return {@code false}, если тип неизвестен, требует параметров или игрок вне мира
     */
    public boolean spawn(String id, double x, double y, double z) {
        return spawnOp.run(id, SIMPLE, 0, 0, 1f, "", x, y, z, 0d, 0d, 0d, 1, 0d);
    }

    /**
     * Одна частица со стартовой скоростью. Что именно значит скорость,
     * решает сам тип частицы: у одних это смещение за тик, у других — цвет
     * или размер.
     *
     * @param id id типа частицы из реестра игры
     * @param x  координата точки
     * @param y  координата точки
     * @param z  координата точки
     * @param dx скорость по оси
     * @param dy скорость по оси
     * @param dz скорость по оси
     * @return {@code false}, если тип неизвестен, требует параметров или игрок вне мира
     */
    public boolean spawn(String id, double x, double y, double z, double dx, double dy, double dz) {
        return spawnOp.run(id, SIMPLE, 0, 0, 1f, "", x, y, z, dx, dy, dz, 1, 0d);
    }

    /**
     * Облачко неподвижных частиц вокруг точки. Смещение каждой берётся
     * по нормальному распределению, как у ванильной команды {@code /particle}.
     *
     * @param id     id типа частицы из реестра игры
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках; {@code 0} — все в одной точке
     * @return {@code false}, если тип неизвестен, требует параметров или игрок вне мира
     */
    public boolean spawn(String id, double x, double y, double z, int count, double spread) {
        return spawnOp.run(id, SIMPLE, 0, 0, 1f, "", x, y, z, 0d, 0d, 0d, count, spread);
    }

    /**
     * Облачко частиц со стартовой скоростью — самая полная форма для типов
     * без параметров.
     *
     * @param id     id типа частицы из реестра игры
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param dx     скорость по оси
     * @param dy     скорость по оси
     * @param dz     скорость по оси
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках
     * @return {@code false}, если тип неизвестен, требует параметров или игрок вне мира
     */
    public boolean spawn(String id, double x, double y, double z,
                         double dx, double dy, double dz, int count, double spread) {
        return spawnOp.run(id, SIMPLE, 0, 0, 1f, "", x, y, z, dx, dy, dz, count, spread);
    }

    /**
     * Цветная пыль заданного размера — та же частица, что от редстоуна.
     *
     * @param color цвет {@code 0xRRGGBB}
     * @param scale размер; {@code 1} — как у редстоуна
     * @param x     координата точки
     * @param y     координата точки
     * @param z     координата точки
     * @return {@code false}, если игрок вне мира
     */
    public boolean dust(int color, float scale, double x, double y, double z) {
        return dust(color, scale, x, y, z, 1, 0d);
    }

    /**
     * Облачко цветной пыли заданного размера.
     *
     * @param color  цвет {@code 0xRRGGBB}
     * @param scale  размер; {@code 1} — как у редстоуна
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках
     * @return {@code false}, если игрок вне мира
     */
    public boolean dust(int color, float scale, double x, double y, double z, int count, double spread) {
        return spawnOp.run("", DUST, color, 0, scale, "", x, y, z, 0d, 0d, 0d, count, spread);
    }

    /**
     * Пыль, перетекающая из одного цвета в другой за время жизни.
     *
     * @param from   цвет в начале {@code 0xRRGGBB}
     * @param to     цвет в конце {@code 0xRRGGBB}
     * @param scale  размер; {@code 1} — как у редстоуна
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках
     * @return {@code false}, если игрок вне мира
     */
    public boolean dust(int from, int to, float scale, double x, double y, double z, int count, double spread) {
        return spawnOp.run("", DUST_FADE, from, to, scale, "", x, y, z, 0d, 0d, 0d, count, spread);
    }

    /**
     * Частица, которой цвет задаётся снаружи: {@code entity_effect},
     * {@code tinted_leaves}, {@code flash}.
     *
     * @param id     id типа частицы из реестра игры
     * @param color  цвет {@code 0xAARRGGBB}
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках
     * @return {@code false}, если тип не принимает цвет или игрок вне мира
     */
    public boolean colored(String id, int color, double x, double y, double z, int count, double spread) {
        return spawnOp.run(id, COLORED, color, 0, 1f, "", x, y, z, 0d, 0d, 0d, count, spread);
    }

    /**
     * Осколки блока: {@code block}, {@code block_marker}, {@code falling_dust},
     * {@code dust_pillar}, {@code block_crumble}.
     *
     * @param id      id типа частицы из реестра игры
     * @param block   id блока, чью текстуру взять
     * @param x       центр облачка
     * @param y       центр облачка
     * @param z       центр облачка
     * @param count   сколько частиц, зажимается в {@code 1..512}
     * @param spread  разброс в блоках
     * @return {@code false}, если тип не принимает блок, блок неизвестен или игрок вне мира
     */
    public boolean block(String id, String block, double x, double y, double z, int count, double spread) {
        return spawnOp.run(id, BLOCK, 0, 0, 1f, block, x, y, z, 0d, 0d, 0d, count, spread);
    }

    /**
     * Осколки предмета — частица {@code item}.
     *
     * @param id     id типа частицы из реестра игры
     * @param item   id предмета, чью текстуру взять
     * @param x      центр облачка
     * @param y      центр облачка
     * @param z      центр облачка
     * @param count  сколько частиц, зажимается в {@code 1..512}
     * @param spread разброс в блоках
     * @return {@code false}, если тип не принимает предмет, предмет неизвестен или игрок вне мира
     */
    public boolean item(String id, String item, double x, double y, double z, int count, double spread) {
        return spawnOp.run(id, ITEM, 0, 0, 1f, item, x, y, z, 0d, 0d, 0d, count, spread);
    }
}
