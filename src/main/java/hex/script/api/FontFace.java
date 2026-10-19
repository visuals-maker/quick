package hex.script.api;

/**
 * Хэндл шрифта: встроенного клиентского или своего, зарегистрированного
 * скриптом. Раскладка и атлас лежат на стороне клиента, скрипт держит имя.
 *
 * <pre>{@code
 * private final FontFace title = fonts().register("title", "fonts/title");
 *
 * @Override
 * public void onRender2D(Render2D render) {
 *     if (!title.ready()) {
 *         return;
 *     }
 *     render.drawText(title.name(), "hex", 8f, 8f, 7f, 0f, 0xFF41C4C8);
 * }
 * }</pre>
 */
public final class FontFace {

    /** Ширина строки. */
    @FunctionalInterface
    public interface WidthOp {
        float run(String name, String text, float size);
    }

    /** Высота строки. */
    @FunctionalInterface
    public interface HeightOp {
        float run(String name, float size);
    }

    /** Состояние загрузки. */
    @FunctionalInterface
    public interface StateOp {
        String run(String name);
    }

    private final String name;
    private final WidthOp widthOp;
    private final HeightOp heightOp;
    private final StateOp errorOp;

    public FontFace(String name, WidthOp widthOp, HeightOp heightOp, StateOp errorOp) {
        this.name = name;
        this.widthOp = widthOp;
        this.heightOp = heightOp;
        this.errorOp = errorOp;
    }

    /**
     * @return имя, под которым шрифт известен клиенту; его же принимает отрисовка текста
     */
    public String name() {
        return name;
    }

    /**
     * @param text строка
     * @param size размер в пикселях
     * @return ширина строки в пикселях
     */
    public float width(String text, float size) {
        return widthOp.run(name, text, size);
    }

    /**
     * @param size размер в пикселях
     * @return высота строки в пикселях
     */
    public float height(float size) {
        return heightOp.run(name, size);
    }

    /**
     * @return загрузился ли шрифт
     */
    public boolean ready() {
        return errorOp.run(name) == null;
    }

    /**
     * @return текст ошибки загрузки или {@code null}, если всё в порядке
     */
    public String error() {
        return errorOp.run(name);
    }

    @Override
    public String toString() {
        return "font " + name;
    }
}
