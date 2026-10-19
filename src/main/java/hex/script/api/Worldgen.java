package hex.script.api;

/**
 * Генерация мира по сиду кодом самой игры: где встанут структуры и из каких частей соберутся. Считается
 * всё, что учитывает сервер, — размещение, биомы, рельеф и сборка, — поэтому найденное совпадает с миром.
 * Если сервер старше клиента, генерация подстраивается под его версию.
 * <p>
 * Расчёт тяжёлый и идёт в фоне. Первый вызов ставит поиск в очередь, повторные с теми же параметрами
 * отдают накопленное. Посчитанные чанки не пересчитываются, так что поиск со сдвинутым центром дешёвый;
 * новый поиск того же набора заменяет старый.
 *
 * <pre>{@code
 * StructureSearch search = worldgen().structures(seed, world().dimension(), "minecraft:end_cities", chunkX, chunkZ, 64);
 * for (FoundStructure city : search.found()) {
 *     if (city.hasTemplate("ship")) {
 *         log().info("ship at " + city.box().centerX() + " " + city.box().centerZ());
 *     }
 * }
 * }</pre>
 */
public final class Worldgen {

    /** Поиск структур набора в квадрате чанков. */
    @FunctionalInterface
    public interface SearchOp {
        StructureSearch run(long seed, String dimension, String structureSet, int centerChunkX, int centerChunkZ, int radiusChunks);
    }

    private final SearchOp searchOp;

    public Worldgen(SearchOp searchOp) {
        this.searchOp = searchOp;
    }

    /**
     * @param seed         сид мира
     * @param dimension    {@code minecraft:overworld}, {@code minecraft:the_nether} или {@code minecraft:the_end}
     * @param structureSet набор структур игры, например {@code minecraft:villages} или {@code minecraft:strongholds}
     * @param centerChunkX центр квадрата в чанках
     * @param centerChunkZ центр квадрата в чанках
     * @param radiusChunks полуширина квадрата в чанках
     * @return снимок поиска; ошибка в {@link StructureSearch#error()}, если набор или измерение неизвестны
     */
    public StructureSearch structures(long seed, String dimension, String structureSet, int centerChunkX, int centerChunkZ, int radiusChunks) {
        return searchOp.run(seed, dimension, structureSet, centerChunkX, centerChunkZ, radiusChunks);
    }
}
