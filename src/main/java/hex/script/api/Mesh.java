package hex.script.api;

/**
 * Готовая геометрия, собранная один раз и нарисованная сколько угодно раз.
 * Вершины лежат на стороне клиента, скрипт держит только имя.
 *
 * <pre>{@code
 * private final Mesh hex = gpu().mesh("hex")
 *         .vertex(0f, 0f, 0f, -1, 0f, 0f)
 *         .vertex(1f, 0f, 0f, -1, 1f, 0f)
 *         .vertex(1f, 1f, 0f, -1, 1f, 1f)
 *         .build();
 * }</pre>
 */
public final class Mesh {

    private final String name;
    private final int vertices;

    Mesh(String name, int vertices) {
        this.name = name;
        this.vertices = vertices;
    }

    /**
     * @return имя меша
     */
    public String name() {
        return name;
    }

    /**
     * @return сколько вершин в меше
     */
    public int vertices() {
        return vertices;
    }

    /**
     * @return сколько треугольников получится
     */
    public int triangles() {
        return vertices / 3;
    }

    @Override
    public String toString() {
        return "mesh " + name + " (" + vertices + ")";
    }
}
