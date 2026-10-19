package hex.script.api;

/**
 * Хэндл своей GLSL-программы. Пайплайн собирается лениво, на первой отрисовке,
 * и живёт до перезагрузки скриптов.
 *
 * <pre>{@code
 * private final Shader glow = shaders().register("glow", VERTEX, FRAGMENT);
 *
 * @Override
 * public void onRender2D(Render2D render) {
 *     render.shader(glow, 8f, 8f, 160f, 40f, 0xFF41C4C8, 0.5f, 0f);
 * }
 * }</pre>
 *
 * Вершинная стадия получает от клиента: {@code Position}, {@code Color},
 * {@code UV0} — координата внутри прямоугольника от 0 до 1, {@code UV1} —
 * размер прямоугольника в пикселях, умноженный на 8, {@code UV2} — два
 * параметра отрисовки, умноженные на 256, и {@code LineWidth} — время в
 * секундах. Матрицы приходят в стандартных блоках {@code DynamicTransforms}
 * и {@code Projection}, как в ванильных шейдерах 26.2.
 */
public final class Shader {

    /** Состояние сборки. */
    @FunctionalInterface
    public interface StateOp {
        String run(String name);
    }

    private final String name;
    private final StateOp errorOp;

    public Shader(String name, StateOp errorOp) {
        this.name = name;
        this.errorOp = errorOp;
    }

    /**
     * @return имя, под которым шейдер лежит в паке скриптов
     */
    public String name() {
        return name;
    }

    /**
     * @return текст ошибки сборки или {@code null}, если всё в порядке
     */
    public String error() {
        return errorOp.run(name);
    }

    /**
     * @return собрался ли шейдер
     */
    public boolean ready() {
        return errorOp.run(name) == null;
    }

    @Override
    public String toString() {
        return "shader " + name;
    }
}
