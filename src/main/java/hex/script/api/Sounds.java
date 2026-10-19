package hex.script.api;

/**
 * Звуки клиента. Звук задаётся строковым id из реестра игры —
 * {@code "minecraft:entity.experience_orb.pickup"}, {@code "minecraft:block.note_block.pling"}.
 * Пространство имён можно опустить: {@code "entity.player.levelup"} читается как
 * {@code "minecraft:entity.player.levelup"}.
 * <p>
 * Всё, что тут проигрывается, слышно только тебе: на сервер ничего не уходит.
 * Неизвестный id ничего не ломает — вызов просто вернёт {@code false}.
 *
 * <pre>{@code
 * if (target != null && !seen) {
 *     sounds().play("minecraft:entity.experience_orb.pickup", 1f, 1.6f);
 * }
 * }</pre>
 *
 * Вызов из чужого потока (например из обработчика {@code packetReceive})
 * откладывается на главный поток игры и возвращает {@code true} — звук уйдёт
 * в очередь, а не проиграется прямо на месте.
 */
public final class Sounds {

    /**
     * Воспроизведение звука. При {@code positioned == false} координаты
     * игнорируются и звук идёт у головы игрока.
     */
    @FunctionalInterface
    public interface PlayOp {
        boolean run(String id, double x, double y, double z, float volume, float pitch, boolean positioned);
    }

    /** Остановка звука по id; {@code null} — остановить все звуки клиента. */
    @FunctionalInterface
    public interface StopOp {
        boolean run(String id);
    }

    /** Все id, известные клиенту. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run();
    }

    private static final String[] EMPTY = new String[0];

    private final PlayOp playOp;
    private final StopOp stopOp;
    private final NamesOp namesOp;

    public Sounds(PlayOp playOp, StopOp stopOp, NamesOp namesOp) {
        this.playOp = playOp;
        this.stopOp = stopOp;
        this.namesOp = namesOp;
    }

    /**
     * Проигрывает звук у игрока с обычной громкостью и тоном.
     *
     * @param id id звука из реестра игры
     * @return {@code false}, если такого звука нет
     */
    public boolean play(String id) {
        return play(id, 1f, 1f);
    }

    /**
     * Проигрывает звук у игрока.
     *
     * @param id     id звука из реестра игры
     * @param volume громкость, {@code 1} — обычная; зажимается в {@code 0..16}
     * @param pitch  тон, {@code 1} — обычный; зажимается в {@code 0.5..2}
     * @return {@code false}, если такого звука нет
     */
    public boolean play(String id, float volume, float pitch) {
        return playOp.run(id, 0d, 0d, 0d, volume, pitch, false);
    }

    /**
     * Проигрывает звук в точке мира: слышно тише и с той стороны, где точка.
     * Без загруженного мира вернёт {@code false}.
     *
     * @param id     id звука из реестра игры
     * @param x      координата точки
     * @param y      координата точки
     * @param z      координата точки
     * @param volume громкость, {@code 1} — обычная; зажимается в {@code 0..16}
     * @param pitch  тон, {@code 1} — обычный; зажимается в {@code 0.5..2}
     * @return {@code false}, если такого звука нет или игрок вне мира
     */
    public boolean play(String id, double x, double y, double z, float volume, float pitch) {
        return playOp.run(id, x, y, z, volume, pitch, true);
    }

    /**
     * Все id звуков, какие знает клиент, отсортированные по алфавиту.
     * Их больше тысячи, и массив собирается заново на каждый вызов —
     * держи результат в поле, а не дёргай метод каждый тик.
     *
     * @return копия списка id; пустой массив, если реестр ещё не готов
     */
    public String[] names() {
        String[] value = namesOp.run();
        return value == null ? EMPTY : value;
    }

    /**
     * Глушит все играющие копии звука — например зациклённый портал или диск.
     *
     * @param id id звука из реестра игры
     * @return {@code false}, если такого звука нет
     */
    public boolean stop(String id) {
        return id != null && !id.isEmpty() && stopOp.run(id);
    }

    /**
     * Глушит вообще все звуки клиента, включая музыку и чужие.
     */
    public void stopAll() {
        stopOp.run(null);
    }
}
