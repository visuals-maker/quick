package hex.script.api;

import java.util.Arrays;

/**
 * Сборка меша. Вершины идут тройками: каждые три подряд образуют треугольник.
 * <p>
 * Короткая вершина несёт только позицию, цвет и {@code UV0}. Полная добавляет
 * ещё три набора, которые шейдер получает как {@code ivec2 UV1},
 * {@code ivec2 UV2} и {@code float LineWidth} — этого хватает, чтобы одним
 * мешем гнать MSDF-глифы или скруглённые прямоугольники с толщиной контура.
 * <p>
 * Пока меш собран только короткими вершинами, клиент сам кладёт в {@code UV1.x}
 * масштаб отрисовки, умноженный на 8, а в {@code LineWidth} — время в секундах.
 * Как только у меша появилась хоть одна полная вершина, эти подстановки
 * выключаются и в шейдер едет ровно то, что записал скрипт.
 */
public final class MeshBuilder {

    /** Передача готового набора вершин клиенту. */
    @FunctionalInterface
    public interface UploadOp {
        int run(String name, float[] data, boolean extended);
    }

    private static final int STRIDE = 11;
    private static final int CHUNK = STRIDE * 32;

    private final String name;
    private final UploadOp uploadOp;

    private float[] data = new float[CHUNK];
    private int size;
    private boolean extended;

    public MeshBuilder(String name, UploadOp uploadOp) {
        this.name = name;
        this.uploadOp = uploadOp;
    }

    /**
     * Добавляет вершину.
     *
     * @param x     позиция
     * @param y     позиция
     * @param z     позиция
     * @param color цвет {@code 0xAARRGGBB}
     * @param u     координата текстуры
     * @param v     координата текстуры
     * @return этот же сборщик
     */
    public MeshBuilder vertex(float x, float y, float z, int color, float u, float v) {
        return write(x, y, z, color, u, v, 0, 0, 0, 0, 0f);
    }

    /**
     * Добавляет вершину без текстурных координат.
     *
     * @param x     позиция
     * @param y     позиция
     * @param z     позиция
     * @param color цвет {@code 0xAARRGGBB}
     * @return этот же сборщик
     */
    public MeshBuilder vertex(float x, float y, float z, int color) {
        return vertex(x, y, z, color, 0f, 0f);
    }

    /**
     * Добавляет вершину со всеми атрибутами формата.
     *
     * @param x         позиция
     * @param y         позиция
     * @param z         позиция
     * @param color     цвет {@code 0xAARRGGBB}
     * @param u         координата текстуры
     * @param v         координата текстуры
     * @param uv1x      первое целое, приезжает как {@code UV1.x}; от -32768 до 32767
     * @param uv1y      второе целое, приезжает как {@code UV1.y}
     * @param uv2x      третье целое, приезжает как {@code UV2.x}
     * @param uv2y      четвёртое целое, приезжает как {@code UV2.y}
     * @param lineWidth число с плавающей точкой, приезжает как {@code LineWidth}
     * @return этот же сборщик
     */
    public MeshBuilder vertex(float x, float y, float z, int color, float u, float v,
                              int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth) {
        extended = true;
        return write(x, y, z, color, u, v, uv1x, uv1y, uv2x, uv2y, lineWidth);
    }

    /**
     * Добавляет треугольник тремя вершинами одного цвета.
     *
     * @param points девять чисел: три точки по x, y, z
     * @param color  цвет {@code 0xAARRGGBB}
     * @return этот же сборщик
     */
    public MeshBuilder triangle(float[] points, int color) {
        if (points != null && points.length >= 9) {
            for (int i = 0; i < 3; i++) {
                vertex(points[i * 3], points[i * 3 + 1], points[i * 3 + 2], color);
            }
        }
        return this;
    }

    /**
     * Загружает меш в клиент.
     *
     * @return готовый меш
     */
    public Mesh build() {
        return new Mesh(name, uploadOp.run(name, Arrays.copyOf(data, size), extended));
    }

    private MeshBuilder write(float x, float y, float z, int color, float u, float v,
                              int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth) {
        if (size + STRIDE > data.length) {
            data = Arrays.copyOf(data, data.length + CHUNK);
        }
        data[size] = x;
        data[size + 1] = y;
        data[size + 2] = z;
        data[size + 3] = Float.intBitsToFloat(color);
        data[size + 4] = u;
        data[size + 5] = v;
        data[size + 6] = uv1x;
        data[size + 7] = uv1y;
        data[size + 8] = uv2x;
        data[size + 9] = uv2y;
        data[size + 10] = lineWidth;
        size += STRIDE;
        return this;
    }
}
