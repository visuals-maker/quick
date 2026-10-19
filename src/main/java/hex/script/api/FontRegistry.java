package hex.script.api;

/**
 * Реестр шрифтов: встроенные шрифты клиента и свои, собранные скриптом.
 *
 * <p>Свой шрифт — это пара файлов MSDF: атлас {@code имя.png} и раскладка
 * {@code имя.json}. Скриптер кладёт обе в {@code Hex/scripts/assets} и
 * передаёт относительный путь без расширения; клиент копирует их в пак
 * скриптов и перезагружает ресурсы. Пока перезагрузка не прошла,
 * {@link FontFace#ready()} отдаёт {@code false} — рисовать таким шрифтом
 * рано.
 *
 * <pre>{@code
 * private final FontFace title = fonts().register("title", "fonts/title");
 * private final FontFace base = fonts().of("sf_rounded_semibold");
 * }</pre>
 */
public final class FontRegistry {

    /** Имена доступных шрифтов. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run();
    }

    /** Хэндл на уже известный клиенту шрифт. */
    @FunctionalInterface
    public interface HandleOp {
        FontFace run(String name);
    }

    /** Регистрация своего шрифта из папки ассетов. */
    @FunctionalInterface
    public interface RegisterOp {
        FontFace run(String name, String assetPath);
    }

    /** Состояние загрузки. */
    @FunctionalInterface
    public interface StateOp {
        String run(String name);
    }

    private final NamesOp namesOp;
    private final HandleOp handleOp;
    private final RegisterOp registerOp;
    private final StateOp errorOp;

    public FontRegistry(NamesOp namesOp, HandleOp handleOp, RegisterOp registerOp, StateOp errorOp) {
        this.namesOp = namesOp;
        this.handleOp = handleOp;
        this.registerOp = registerOp;
        this.errorOp = errorOp;
    }

    /**
     * @return имена встроенных шрифтов клиента вместе с уже зарегистрированными своими
     */
    public String[] names() {
        return namesOp.run();
    }

    /**
     * Хэндл на шрифт, который клиент уже знает: встроенный или
     * зарегистрированный этим же скриптом раньше.
     *
     * @param name имя шрифта
     * @return хэндл; у незнакомого имени {@link FontFace#ready()} даст {@code false}
     */
    public FontFace of(String name) {
        return handleOp.run(name);
    }

    /**
     * Забирает пару {@code assetPath.png} и {@code assetPath.json} из
     * {@code Hex/scripts/assets}, кладёт её в пак скриптов и отдаёт хэндл.
     * Одинаковое содержимое второй раз не переписывается.
     *
     * @param name      имя, под которым шрифт будет виден отрисовке
     * @param assetPath путь пары файлов относительно {@code scripts/assets},
     *                  расширение можно не писать
     * @return хэндл шрифта
     */
    public FontFace register(String name, String assetPath) {
        return registerOp.run(name, assetPath);
    }

    /**
     * @param name имя шрифта
     * @return почему шрифт не завёлся или {@code null}, если всё в порядке
     */
    public String error(String name) {
        return errorOp.run(name);
    }
}
