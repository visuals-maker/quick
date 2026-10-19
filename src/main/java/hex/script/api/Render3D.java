package hex.script.api;

/**
 * Рисование в мире. Приходит в {@link Script#onRender3D(Render3D)}; координаты
 * задаются мировые, позицию камеры клиент вычитает сам.
 *
 * <pre>{@code
 * @Override
 * public void onRender3D(Render3D render) {
 *     if (target() instanceof LivingEntity entity) {
 *         render.entityBox(entity, 0xFFFF3B30, true);
 *         render.tracer(entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
 *                 0xFFFF3B30, true);
 *     }
 * }
 * }</pre>
 *
 * Последний аргумент каждого метода — {@code throughWalls}: при {@code true}
 * фигура видна сквозь блоки.
 */
public final class Render3D {

    /** Отрезок в мировых координатах. */
    @FunctionalInterface
    public interface LineOp {
        void run(double x1, double y1, double z1, double x2, double y2, double z2,
                 int color, boolean throughWalls);
    }

    /** Коробка по двум углам: рёбра или заливка. */
    @FunctionalInterface
    public interface BoxOp {
        void run(double minX, double minY, double minZ, double maxX, double maxY, double maxZ,
                 int color, boolean throughWalls, boolean filled);
    }

    /** Коробка сущности по интерполированной позиции. */
    @FunctionalInterface
    public interface EntityOp {
        void run(Object entity, int color, boolean throughWalls, boolean filled);
    }

    /** Линия от центра экрана к точке. */
    @FunctionalInterface
    public interface TracerOp {
        void run(double x, double y, double z, int color, boolean throughWalls);
    }

    /** Веер треугольников от первой точки; массив — тройки x, y, z. */
    @FunctionalInterface
    public interface PolygonOp {
        void run(double[] points, int color, boolean throughWalls);
    }

    /**
     * Своя геометрия в мире; поворот в градусах, как у взгляда игрока.
     * Пустая текстура — заливка цветом вершин.
     */
    @FunctionalInterface
    public interface MeshOp {
        void run(String mesh, String shader, String texture, double x, double y, double z, float scale,
                 float yaw, float pitch, int tint, int blend, int depth, boolean cull, boolean billboard);
    }

    /** Частичный тик текущего кадра. */
    @FunctionalInterface
    public interface DeltaOp {
        float run();
    }

    /** Переключение режима смешивания. */
    @FunctionalInterface
    public interface BlendOp {
        void run(boolean additive);
    }

    /** Позиция камеры по оси. */
    @FunctionalInterface
    public interface CameraOp {
        double run(int axis);
    }

    /** Смешивание по альфе — обычная полупрозрачность. */
    public static final int NORMAL = 0;

    /** Смешивание сложением: цвет, помноженный на свою альфу, добавляется к фону. */
    public static final int ADDITIVE = 1;

    /** Без смешивания: цвет пишется поверх вместе с альфой. */
    public static final int OVERWRITE = 2;

    /** Глубина не проверяется — видно сквозь блоки. */
    public static final int NO_DEPTH = 0;

    /** Глубина проверяется, но не пишется — за блоками не видно. */
    public static final int DEPTH = 1;

    /** Глубина проверяется и пишется — фигура перекрывает то, что нарисуют после. */
    public static final int DEPTH_WRITE = 2;

    private static final int BLEND_CURRENT = -1;

    private final LineOp lineOp;
    private final BoxOp boxOp;
    private final EntityOp entityOp;
    private final TracerOp tracerOp;
    private final PolygonOp polygonOp;
    private final MeshOp meshOp;
    private final DeltaOp deltaOp;
    private final CameraOp cameraOp;
    private final BlendOp blendOp;

    public Render3D(LineOp lineOp, BoxOp boxOp, EntityOp entityOp, TracerOp tracerOp,
                    PolygonOp polygonOp, MeshOp meshOp,
                    DeltaOp deltaOp, CameraOp cameraOp, BlendOp blendOp) {
        this.lineOp = lineOp;
        this.boxOp = boxOp;
        this.entityOp = entityOp;
        this.tracerOp = tracerOp;
        this.polygonOp = polygonOp;
        this.meshOp = meshOp;
        this.deltaOp = deltaOp;
        this.cameraOp = cameraOp;
        this.blendOp = blendOp;
    }

    /**
     * Переключает смешивание для последующих вызовов: обычное или аддитивное,
     * при котором цвета складываются и фигура светится. Складывается цвет,
     * помноженный на свою альфу, — прозрачные места не светятся, поэтому мягкое
     * свечение задают альфой вершин или альфой картинки. Сбрасывается в обычное
     * после каждого кадра.
     *
     * @param additive складывать ли цвета
     */
    public void additive(boolean additive) {
        blendOp.run(additive);
    }

    /**
     * Отрезок между двумя точками мира.
     *
     * @param x1           начало
     * @param y1           начало
     * @param z1           начало
     * @param x2           конец
     * @param y2           конец
     * @param z2           конец
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void line(double x1, double y1, double z1, double x2, double y2, double z2,
                     int color, boolean throughWalls) {
        lineOp.run(x1, y1, z1, x2, y2, z2, color, throughWalls);
    }

    /**
     * Двенадцать рёбер коробки.
     *
     * @param minX         угол
     * @param minY         угол
     * @param minZ         угол
     * @param maxX         угол
     * @param maxY         угол
     * @param maxZ         угол
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ,
                    int color, boolean throughWalls) {
        boxOp.run(minX, minY, minZ, maxX, maxY, maxZ, color, throughWalls, false);
    }

    /**
     * Залитые грани коробки.
     *
     * @param minX         угол
     * @param minY         угол
     * @param minZ         угол
     * @param maxX         угол
     * @param maxY         угол
     * @param maxZ         угол
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void filledBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ,
                          int color, boolean throughWalls) {
        boxOp.run(minX, minY, minZ, maxX, maxY, maxZ, color, throughWalls, true);
    }

    /**
     * Рёбра коробки сущности по её интерполированной позиции.
     *
     * @param entity       ванильная {@code Entity}
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void entityBox(Entity entity, int color, boolean throughWalls) {
        entityOp.run(entity, color, throughWalls, false);
    }

    /**
     * Заливка коробки сущности.
     *
     * @param entity       сущность
     * @param color        цвет в ARGB
     * @param throughWalls видно ли сквозь блоки
     */
    public void filledEntityBox(Entity entity, int color, boolean throughWalls) {
        entityOp.run(entity, color, throughWalls, true);
    }

    public void entityBox(Object entity, int color, boolean throughWalls) {
        entityOp.run(entity, color, throughWalls, false);
    }

    /**
     * Залитая коробка сущности.
     *
     * @param entity       ванильная {@code Entity}
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void filledEntityBox(Object entity, int color, boolean throughWalls) {
        entityOp.run(entity, color, throughWalls, true);
    }

    /**
     * Линия от центра экрана к точке мира.
     *
     * @param x            точка
     * @param y            точка
     * @param z            точка
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void tracer(double x, double y, double z, int color, boolean throughWalls) {
        tracerOp.run(x, y, z, color, throughWalls);
    }

    /**
     * Залитый треугольник.
     *
     * @param points       девять чисел: три точки по x, y, z
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void triangle(double[] points, int color, boolean throughWalls) {
        polygonOp.run(points, color, throughWalls);
    }

    /**
     * Веер треугольников от первой точки. Меньше трёх точек — ничего не рисуется.
     *
     * @param points       тройки x, y, z
     * @param color        цвет {@code 0xAARRGGBB}
     * @param throughWalls видно ли сквозь блоки
     */
    public void polygon(double[] points, int color, boolean throughWalls) {
        polygonOp.run(points, color, throughWalls);
    }

    /**
     * Рисует меш в мире, всегда развёрнутым к камере. То, что нужно для
     * значка над головой: сборка делается один раз, разворот считает клиент.
     *
     * <pre>{@code
     * private final Mesh marker = gpu().mesh("marker")
     *         .vertex(-0.5f, 0f, 0f, -1, 0f, 0f)
     *         .vertex(0.5f, 0f, 0f, -1, 1f, 0f)
     *         .vertex(0f, 1f, 0f, -1, 0.5f, 1f)
     *         .build();
     *
     * render.billboard(marker, x, y + 2.4, z, 0.8f, 0xFF41C4C8, true);
     * }</pre>
     *
     * Меш собирают в плоскости XY: {@code +X} вправо, {@code +Y} вверх.
     *
     * @param mesh         меш из {@link Gpu}
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void billboard(Mesh mesh, double x, double y, double z, float scale, int tint, boolean throughWalls) {
        billboard(mesh, null, x, y, z, scale, tint, throughWalls);
    }

    /**
     * Тот же билборд, но натянутый на картинку. Текстурные координаты берутся
     * из меша — {@link MeshBuilder#vertex(float, float, float, int, float, float)}.
     * Картинка ищется там же, где для {@link Render2D#drawImage}: короткое имя
     * лежит в {@code scripts/resources/assets/script/textures}.
     *
     * <pre>{@code
     * private final Mesh sprite = gpu().mesh("spark")
     *         .vertex(-0.5f, -0.5f, 0f, -1, 0f, 1f)
     *         .vertex(0.5f, -0.5f, 0f, -1, 1f, 1f)
     *         .vertex(0.5f, 0.5f, 0f, -1, 1f, 0f)
     *         .build();
     *
     * render.additive(true);
     * render.billboard(sprite, "glow.png", x, y, z, 0.4f, 0xFF41C4C8, true);
     * }</pre>
     *
     * @param mesh         меш из {@link Gpu}
     * @param texture      имя картинки; {@code null} — заливка цветом вершин
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void billboard(Mesh mesh, String texture, double x, double y, double z,
                          float scale, int tint, boolean throughWalls) {
        if (mesh != null) {
            meshOp.run(mesh.name(), null, texture, x, y, z, scale, 0f, 0f, tint,
                    BLEND_CURRENT, throughWalls ? NO_DEPTH : DEPTH, false, true);
        }
    }

    /**
     * Рисует меш в мире без разворота, вдоль осей мира.
     *
     * @param mesh         меш из {@link Gpu}
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void mesh(Mesh mesh, double x, double y, double z, float scale, int tint, boolean throughWalls) {
        mesh(mesh, null, x, y, z, scale, 0f, 0f, tint, throughWalls);
    }

    /**
     * Меш вдоль осей мира, натянутый на картинку. Про поиск картинки —
     * {@link #billboard(Mesh, String, double, double, double, float, int, boolean)}.
     *
     * @param mesh         меш из {@link Gpu}
     * @param texture      имя картинки; {@code null} — заливка цветом вершин
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void mesh(Mesh mesh, String texture, double x, double y, double z,
                     float scale, int tint, boolean throughWalls) {
        mesh(mesh, texture, x, y, z, scale, 0f, 0f, tint, throughWalls);
    }

    /**
     * Рисует меш в мире, повёрнутым как взгляд игрока: {@code yaw} вокруг
     * вертикали, {@code pitch} — наклон. При {@code yaw = cameraYaw()} и
     * {@code pitch = cameraPitch()} получается то же, что {@link #billboard}.
     *
     * @param mesh         меш из {@link Gpu}
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param yaw          рыскание в градусах
     * @param pitch        тангаж в градусах
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void mesh(Mesh mesh, double x, double y, double z, float scale,
                     float yaw, float pitch, int tint, boolean throughWalls) {
        mesh(mesh, null, x, y, z, scale, yaw, pitch, tint, throughWalls);
    }

    /**
     * Повёрнутый меш с картинкой. Про поиск картинки —
     * {@link #billboard(Mesh, String, double, double, double, float, int, boolean)}.
     *
     * @param mesh         меш из {@link Gpu}
     * @param texture      имя картинки; {@code null} — заливка цветом вершин
     * @param x            позиция в мире
     * @param y            позиция в мире
     * @param z            позиция в мире
     * @param scale        во сколько раз растянуть, в блоках
     * @param yaw          рыскание в градусах
     * @param pitch        тангаж в градусах
     * @param tint         цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param throughWalls видно ли сквозь блоки
     */
    public void mesh(Mesh mesh, String texture, double x, double y, double z, float scale,
                     float yaw, float pitch, int tint, boolean throughWalls) {
        if (mesh != null) {
            meshOp.run(mesh.name(), null, texture, x, y, z, scale, yaw, pitch, tint,
                    BLEND_CURRENT, throughWalls ? NO_DEPTH : DEPTH, false, false);
        }
    }

    /**
     * Меш с полным набором состояний: смешивание, глубина и отсечение задней
     * стороны задаются явно и не зависят от {@link #additive(boolean)}. Это
     * самый общий способ рисовать в мире — всё остальное здесь его частные
     * случаи.
     *
     * <pre>{@code
     * render.mesh(sprite, "glow.png", x, y, z, 0.4f, 0f, 0f, color,
     *         Render3D.ADDITIVE, Render3D.DEPTH, false);
     * }</pre>
     *
     * @param mesh    меш из {@link Gpu}, треугольниками по три вершины
     * @param texture имя картинки; {@code null} — заливка цветом вершин
     * @param x       позиция в мире
     * @param y       позиция в мире
     * @param z       позиция в мире
     * @param scale   во сколько раз растянуть, в блоках
     * @param yaw     рыскание в градусах
     * @param pitch   тангаж в градусах
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param blend   {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     * @param depth   {@link #NO_DEPTH}, {@link #DEPTH} или {@link #DEPTH_WRITE}
     * @param cull    отсекать ли треугольники, повёрнутые от камеры
     */
    public void mesh(Mesh mesh, String texture, double x, double y, double z, float scale,
                     float yaw, float pitch, int tint, int blend, int depth, boolean cull) {
        if (mesh != null) {
            meshOp.run(mesh.name(), null, texture, x, y, z, scale, yaw, pitch, tint, blend, depth, cull, false);
        }
    }

    /**
     * Билборд с полным набором состояний. Про параметры —
     * {@link #mesh(Mesh, String, double, double, double, float, float, float, int, int, int, boolean)}.
     *
     * @param mesh    меш из {@link Gpu}, собранный в плоскости XY
     * @param texture имя картинки; {@code null} — заливка цветом вершин
     * @param x       позиция в мире
     * @param y       позиция в мире
     * @param z       позиция в мире
     * @param scale   во сколько раз растянуть, в блоках
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param blend   {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     * @param depth   {@link #NO_DEPTH}, {@link #DEPTH} или {@link #DEPTH_WRITE}
     * @param cull    отсекать ли треугольники, повёрнутые от камеры
     */
    public void billboard(Mesh mesh, String texture, double x, double y, double z, float scale,
                          int tint, int blend, int depth, boolean cull) {
        if (mesh != null) {
            meshOp.run(mesh.name(), null, texture, x, y, z, scale, 0f, 0f, tint, blend, depth, cull, true);
        }
    }

    /**
     * Меш своим шейдером. Вершинная стадия получает весь формат меша:
     * {@code Position}, {@code Color}, {@code UV0}, {@code UV1}, {@code UV2} и
     * {@code LineWidth} — те же атрибуты, что в 2D у {@link Shader}. Матрицы
     * приходят в блоках {@code DynamicTransforms} и {@code Projection},
     * позиция задаётся относительно камеры, так что вершина считается как
     * {@code ProjMat * ModelViewMat * vec4(Position, 1.0)}.
     * <p>
     * У меша без расширенных вершин {@code UV1.x} приходит масштабом,
     * помноженным на 8, а {@code LineWidth} — временем в секундах, как в 2D.
     * С картинкой шейдер получает ещё и {@code Sampler0}.
     *
     * <pre>{@code
     * private final Shader beam = shaders().register("beam", VERTEX, FRAGMENT);
     *
     * render.mesh(ring, beam, null, x, y, z, 1.5f, 0f, 0f, -1,
     *         Render3D.ADDITIVE, Render3D.DEPTH, false);
     * }</pre>
     *
     * @param mesh    меш из {@link Gpu}, треугольниками по три вершины
     * @param shader  своя GLSL-программа из {@link Shaders}; {@code null} — встроенная
     * @param texture имя картинки; {@code null} — без картинки
     * @param x       позиция в мире
     * @param y       позиция в мире
     * @param z       позиция в мире
     * @param scale   во сколько раз растянуть, в блоках
     * @param yaw     рыскание в градусах
     * @param pitch   тангаж в градусах
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param blend   {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     * @param depth   {@link #NO_DEPTH}, {@link #DEPTH} или {@link #DEPTH_WRITE}
     * @param cull    отсекать ли треугольники, повёрнутые от камеры
     */
    public void mesh(Mesh mesh, Shader shader, String texture, double x, double y, double z, float scale,
                     float yaw, float pitch, int tint, int blend, int depth, boolean cull) {
        if (mesh != null) {
            meshOp.run(mesh.name(), shader == null ? null : shader.name(), texture,
                    x, y, z, scale, yaw, pitch, tint, blend, depth, cull, false);
        }
    }

    /**
     * Билборд своим шейдером. Про параметры —
     * {@link #mesh(Mesh, Shader, String, double, double, double, float, float, float, int, int, int, boolean)}.
     *
     * @param mesh    меш из {@link Gpu}, собранный в плоскости XY
     * @param shader  своя GLSL-программа из {@link Shaders}; {@code null} — встроенная
     * @param texture имя картинки; {@code null} — без картинки
     * @param x       позиция в мире
     * @param y       позиция в мире
     * @param z       позиция в мире
     * @param scale   во сколько раз растянуть, в блоках
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — как есть
     * @param blend   {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     * @param depth   {@link #NO_DEPTH}, {@link #DEPTH} или {@link #DEPTH_WRITE}
     * @param cull    отсекать ли треугольники, повёрнутые от камеры
     */
    public void billboard(Mesh mesh, Shader shader, String texture, double x, double y, double z,
                          float scale, int tint, int blend, int depth, boolean cull) {
        if (mesh != null) {
            meshOp.run(mesh.name(), shader == null ? null : shader.name(), texture,
                    x, y, z, scale, 0f, 0f, tint, blend, depth, cull, true);
        }
    }

    /**
     * @return частичный тик текущего кадра
     */
    public float delta() {
        return deltaOp.run();
    }

    /**
     * @return рыскание камеры в градусах
     */
    public float cameraYaw() {
        return (float) cameraOp.run(3);
    }

    /**
     * @return тангаж камеры в градусах
     */
    public float cameraPitch() {
        return (float) cameraOp.run(4);
    }

    /**
     * @return позиция камеры по X
     */
    public double cameraX() {
        return cameraOp.run(0);
    }

    /**
     * @return позиция камеры по Y
     */
    public double cameraY() {
        return cameraOp.run(1);
    }

    /**
     * @return позиция камеры по Z
     */
    public double cameraZ() {
        return cameraOp.run(2);
    }
}
