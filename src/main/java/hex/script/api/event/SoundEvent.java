package hex.script.api.event;

/**
 * Клиент начал проигрывать звук. Приходит на любой звук, который дошёл до
 * звукового движка: и на серверные, и на свои из {@code sounds()}.
 * Годится для детекта событий по звуку — взрыва кристалла, тотема, открытия
 * сундука рядом.
 *
 * <pre>{@code
 * on(SoundEvent.class, event -> {
 *     if (event.is("minecraft:entity.generic.explode")) {
 *         log().info("explosion in " + event.x() + " " + event.z());
 *     }
 * });
 * }</pre>
 *
 * Событие только на чтение: клиент отдаёт его неотменяемым, {@link #cancel()}
 * на нём ничего не делает, а {@link #cancellable()} возвращает {@code false}.
 * Заглушить звук отсюда нельзя — для этого есть {@code sounds().stop(id)}.
 * <p>
 * Звук, придушенный настройками громкости до нуля, до движка не доходит и
 * события не даёт.
 */
public final class SoundEvent extends Event {

    private String id = "";
    private String source = "";
    private float volume;
    private float pitch;
    private double x;
    private double y;
    private double z;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param id     id звука из реестра игры
     * @param source категория громкости
     * @param volume громкость
     * @param pitch  тон
     * @param x      точка, откуда звук
     * @param y      точка, откуда звук
     * @param z      точка, откуда звук
     */
    public void set(String id, String source, float volume, float pitch, double x, double y, double z) {
        this.id = id == null ? "" : id;
        this.source = source == null ? "" : source;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String name() {
        return "sound";
    }

    /**
     * @return id звука, например {@code "minecraft:entity.generic.explode"}
     */
    public String id() {
        return id;
    }

    /**
     * Сравнение с id. Пространство имён можно не писать:
     * {@code is("entity.generic.explode")} совпадёт с ванильным звуком.
     *
     * @param other id для сравнения
     * @return совпадает ли
     */
    public boolean is(String other) {
        if (other == null || other.isEmpty()) {
            return false;
        }
        if (id.equals(other)) {
            return true;
        }
        return other.indexOf(':') < 0 && id.equals("minecraft:" + other);
    }

    /**
     * @return категория громкости: {@code "master"}, {@code "players"},
     * {@code "blocks"}, {@code "music"} и так далее
     */
    public String source() {
        return source;
    }

    /**
     * @return громкость, с которой звук поставили в очередь
     */
    public float volume() {
        return volume;
    }

    /**
     * @return тон, {@code 1} — обычный
     */
    public float pitch() {
        return pitch;
    }

    /**
     * У звуков «у головы» — таких как интерфейс и музыка — координаты нулевые.
     *
     * @return координата источника звука
     */
    public double x() {
        return x;
    }

    /**
     * @return координата источника звука
     */
    public double y() {
        return y;
    }

    /**
     * @return координата источника звука
     */
    public double z() {
        return z;
    }
}
