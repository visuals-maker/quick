package hex.script.api;

/**
 * Реестр своих шейдеров. Исходники кладутся в пак скриптов
 * {@code Hex/scripts/resources/assets/script/shaders/core}, откуда их читает
 * игра; после записи новых исходников клиент сам перезагружает ресурсы.
 *
 * <pre>{@code
 * private final Shader glow = shaders().register("glow", VERTEX, FRAGMENT);
 * }</pre>
 */
public final class Shaders {

    /** Запись исходников и получение хэндла. */
    @FunctionalInterface
    public interface RegisterOp {
        Shader run(String name, String vertexSource, String fragmentSource);
    }

    /** Хэндл на шейдер, уже лежащий в паке. */
    @FunctionalInterface
    public interface HandleOp {
        Shader run(String name);
    }

    /** Имена зарегистрированных шейдеров. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run();
    }

    private final RegisterOp registerOp;
    private final HandleOp handleOp;
    private final NamesOp namesOp;

    public Shaders(RegisterOp registerOp, HandleOp handleOp, NamesOp namesOp) {
        this.registerOp = registerOp;
        this.handleOp = handleOp;
        this.namesOp = namesOp;
    }

    /**
     * Кладёт исходники в пак скриптов и возвращает хэндл. Если файлы уже
     * такие же, ничего не переписывается и ресурсы не перезагружаются.
     *
     * @param name           имя без расширения, только буквы, цифры и подчёркивание
     * @param vertexSource   вершинная стадия
     * @param fragmentSource фрагментная стадия
     * @return хэндл шейдера
     */
    public Shader register(String name, String vertexSource, String fragmentSource) {
        return registerOp.run(name, vertexSource, fragmentSource);
    }

    /**
     * Хэндл на шейдер, файлы которого уже лежат в паке скриптов.
     *
     * @param name имя без расширения
     * @return хэндл шейдера
     */
    public Shader handle(String name) {
        return handleOp.run(name);
    }

    /**
     * @return имена всех зарегистрированных шейдеров
     */
    public String[] names() {
        return namesOp.run();
    }
}
