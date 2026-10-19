package hex.script.api;

/**
 * Отрисовка HUD (двумерный слой поверх игры).
 * <p>
 * Экземпляр приезжает аргументом в {@link Script#onRender2D(Render2D)} и живёт
 * ровно один кадр. Держать его в поле и звать между кадрами нельзя — методы
 * бросят {@link IllegalStateException}.
 * <p>
 * Координаты — в единицах интерфейса клиента, начало в левом верхнем углу.
 * Цвет — упакованный ARGB {@code int} (0xAARRGGBB); альфа {@code 0} значит
 * «полностью прозрачный», такие вызовы отбрасываются.
 * <p>
 * Класс намеренно финальный, а операции приходят отдельными функциями: клиент
 * проходит через обфускатор, и любой его класс, реализующий интерфейс отсюда,
 * потерял бы имена методов. Лямбды этой проблемы лишены.
 */
public final class Render2D {

    /** Сторона иконки предмета в инвентаре. */
    public static final float ITEM_SIZE = 16f;

    /**
     * Семплер, который прописан у самой текстуры. Так рисуют перегрузки без
     * явного семплера — поведение по умолчанию.
     */
    /** Смешивание по альфе — обычная полупрозрачность. */
    public static final int NORMAL = 0;

    /** Смешивание сложением: цвет, помноженный на свою альфу, добавляется к фону. */
    public static final int ADDITIVE = 1;

    /** Без смешивания: цвет пишется поверх вместе с альфой. */
    public static final int OVERWRITE = 2;

    public static final int DEFAULT_SAMPLER = -1;

    /**
     * Фильтрация по ближайшему текселю: пиксельная картинка остаётся резкой на
     * любом увеличении. Складывается с {@link #CLAMP} или {@link #REPEAT}.
     */
    public static final int NEAREST = 0;

    /**
     * Линейная фильтрация: соседние тексели смешиваются. Нужна MSDF-атласам и
     * вообще всему, что уменьшается — иначе края шумят. Складывается с
     * {@link #CLAMP} или {@link #REPEAT}.
     */
    public static final int LINEAR = 1;

    /**
     * Края куска текстуры растягиваются: выход за {@code 0..1} берёт крайний
     * тексель. Складывается с {@link #NEAREST} или {@link #LINEAR}.
     */
    public static final int CLAMP = 0;

    /**
     * Текстура повторяется: выход за {@code 0..1} заворачивается обратно.
     * Складывается с {@link #NEAREST} или {@link #LINEAR}.
     */
    public static final int REPEAT = 2;

    /** Заливка прямоугольника со скруглением. */
    @FunctionalInterface
    public interface RectOp {
        void run(float x, float y, float width, float height, float round, int color);
    }

    /** Отрисовка строки. */
    @FunctionalInterface
    public interface TextOp {
        void run(String text, float x, float y, float size, int color);
    }

    /** Ширина строки без отрисовки. */
    @FunctionalInterface
    public interface WidthOp {
        float run(String text, float size);
    }

    /** Высота строки для кегля. */
    @FunctionalInterface
    public interface HeightOp {
        float run(float size);
    }

    /** Цвет темы по имени токена. */
    @FunctionalInterface
    public interface ThemeOp {
        int run(String token);
    }

    /** Прямоугольник с четырьмя цветами и четырьмя радиусами. */
    @FunctionalInterface
    public interface GradientRectOp {
        void run(float x, float y, float width, float height,
                 float round1, float round2, float round3, float round4,
                 int color1, int color2, int color3, int color4);
    }

    /** Скруглённая обводка с градиентом. */
    @FunctionalInterface
    public interface OutlineOp {
        void run(float x, float y, float width, float height, float round, float smooth,
                 int color1, int color2, int color3, int color4);
    }

    /** Строка с горизонтальным градиентом. */
    @FunctionalInterface
    public interface GradientTextOp {
        void run(String text, float x, float y, float size, int color1, int color2);
    }

    /** Цвет бегущего градиента темы по смещению. */
    @FunctionalInterface
    public interface FadeOp {
        int run(int index);
    }

    /** Текущий контекст отрисовки ванили. */
    @FunctionalInterface
    public interface ContextOp {
        Object run();
    }

    /** Состояние отрисовки кадра для собственных элементов. */
    @FunctionalInterface
    public interface StateOp {
        Object run();
    }

    /** Размытие фона под прямоугольником. */
    @FunctionalInterface
    public interface BlurOp {
        void run(float x, float y, float width, float height, float round, float smooth, float radius,
                 float alpha, int color);
    }

    /** Отрисовка готового меша; {@code texture} может быть {@code null}. */
    @FunctionalInterface
    public interface MeshOp {
        void run(String mesh, String shader, String texture, int sampler,
                 float x, float y, float scale, int tint, int blend);
    }

    /** Отрисовка иконки предмета по строковому id. */
    @FunctionalInterface
    public interface ItemOp {
        boolean run(String id, long handle, float x, float y, float size,
                    int count, int damage, int maxDamage);
    }

    /** Установка прямоугольника обрезки. */
    @FunctionalInterface
    public interface ScissorOp {
        void run(float x, float y, float width, float height);
    }

    /**
     * Преобразование системы координат: 0 — запомнить, 1 — вернуть,
     * 2 — сдвиг, 3 — масштаб, 4 — поворот в градусах.
     */
    @FunctionalInterface
    public interface TransformOp {
        void run(int action, float x, float y);
    }

    /** Открытие нового слоя отрисовки. */
    @FunctionalInterface
    public interface LayerOp {
        void run();
    }

    /** Снятие последнего прямоугольника обрезки. */
    @FunctionalInterface
    public interface ResetOp {
        void run();
    }

    /** Отрисовка куска текстуры по полному идентификатору. */
    @FunctionalInterface
    public interface TextureOp {
        void run(String id, int sampler, float x, float y, float width, float height,
                 float u, float v, float uWidth, float vHeight, float round, int color);
    }

    /** Частичный тик текущего кадра. */
    @FunctionalInterface
    public interface DeltaOp {
        float run();
    }

    /** Отрисовка одного четырёхугольника со всеми атрибутами формата. */
    @FunctionalInterface
    public interface QuadOp {
        void run(String shader, String texture, int sampler, float x, float y, float width, float height,
                 float u, float v, float uWidth, float vHeight,
                 int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth,
                 int color1, int color2, int color3, int color4, int blend);
    }

    /** Отрисовка картинки из папки ресурсов скриптов. */
    @FunctionalInterface
    public interface ImageOp {
        void run(String path, float x, float y, float width, float height, int color);
    }

    /** Отрисовка прямоугольника своим шейдером. */
    @FunctionalInterface
    public interface ShaderOp {
        void run(String shader, float x, float y, float width, float height,
                 int color, float first, float second, int blend);
    }

    /** Имена шрифтов клиента. */
    @FunctionalInterface
    public interface FontsOp {
        String[] run();
    }

    /** Отрисовка текста выбранным шрифтом. */
    @FunctionalInterface
    public interface FontTextOp {
        float run(String font, Object text, float x, float y, float size, float thickness, int color);
    }

    /** Отрисовка строки с обрезкой по ширине и мягким затуханием у края. */
    @FunctionalInterface
    public interface FadeTextOp {
        void run(String font, String text, float x, float y, float size, float thickness,
                 int color, float maxWidth, float fade);
    }

    /** Отрисовка бегущего градиентного текста; градиент считает и кеширует клиент. */
    @FunctionalInterface
    public interface TextGradientOp {
        void run(String font, String key, String text, float x, float y, float size, float thickness,
                 int color, int from, int to, int power, int speed);
    }

    /** Ширина текста выбранным шрифтом. */
    @FunctionalInterface
    public interface FontWidthOp {
        float run(String font, Object text, float size, float thickness);
    }

    /** Высота строки выбранного шрифта. */
    @FunctionalInterface
    public interface FontHeightOp {
        float run(String font, float size);
    }

    /** Межстрочный интервал шрифта: от верха выносного элемента до низа. */
    @FunctionalInterface
    public interface FontLineOp {
        float run(String font, float size);
    }

    /** Расстояние от переданного в отрисовку y до базовой линии. */
    @FunctionalInterface
    public interface FontBaseOp {
        float run(String font, float size);
    }

    /** Глубина подстрочного элемента под базовой линией. */
    @FunctionalInterface
    public interface FontDropOp {
        float run(String font, float size);
    }

    /** Ширина или высота экрана в единицах интерфейса. */
    @FunctionalInterface
    public interface ScreenOp {
        int run();
    }

    private final RectOp rectOp;
    private final TextOp textOp;
    private final WidthOp widthOp;
    private final HeightOp heightOp;
    private final ThemeOp themeOp;
    private final GradientRectOp gradientRectOp;
    private final OutlineOp outlineOp;
    private final GradientTextOp gradientTextOp;
    private final FadeOp fadeOp;
    private final ContextOp contextOp;
    private final StateOp stateOp;
    private final ScreenOp screenWidthOp;
    private final ScreenOp screenHeightOp;
    private final FontsOp fontsOp;
    private final FontTextOp fontTextOp;
    private final FontWidthOp fontWidthOp;
    private final FadeTextOp fadeTextOp;
    private final TextGradientOp textGradientOp;
    private final FontHeightOp fontHeightOp;
    private final FontLineOp fontLineOp;
    private final FontBaseOp fontBaseOp;
    private final FontDropOp fontDropOp;
    private final ShaderOp shaderOp;
    private final ImageOp imageOp;
    private final MeshOp meshOp;
    private final BlurOp blurOp;
    private final ItemOp itemOp;
    private final ScissorOp scissorOp;
    private final ResetOp resetOp;
    private final LayerOp layerOp;
    private final TransformOp transformOp;
    private final TextureOp textureOp;
    private final DeltaOp deltaOp;
    private final QuadOp quadOp;

    public Render2D(RectOp rectOp, TextOp textOp, WidthOp widthOp, HeightOp heightOp, ThemeOp themeOp,
                    GradientRectOp gradientRectOp, OutlineOp outlineOp, GradientTextOp gradientTextOp,
                    FadeOp fadeOp, ContextOp contextOp, StateOp stateOp,
                    ScreenOp screenWidthOp, ScreenOp screenHeightOp,
                    FontsOp fontsOp, FontTextOp fontTextOp,
                    FontWidthOp fontWidthOp, FadeTextOp fadeTextOp, TextGradientOp textGradientOp, FontHeightOp fontHeightOp, FontLineOp fontLineOp, FontBaseOp fontBaseOp, FontDropOp fontDropOp, ShaderOp shaderOp,
                    ImageOp imageOp, MeshOp meshOp, ItemOp itemOp,
                    ScissorOp scissorOp, ResetOp resetOp, LayerOp layerOp, TransformOp transformOp, TextureOp textureOp, DeltaOp deltaOp,
                    QuadOp quadOp, BlurOp blurOp) {
        this.rectOp = rectOp;
        this.textOp = textOp;
        this.widthOp = widthOp;
        this.heightOp = heightOp;
        this.themeOp = themeOp;
        this.gradientRectOp = gradientRectOp;
        this.outlineOp = outlineOp;
        this.gradientTextOp = gradientTextOp;
        this.fadeOp = fadeOp;
        this.contextOp = contextOp;
        this.stateOp = stateOp;
        this.screenWidthOp = screenWidthOp;
        this.screenHeightOp = screenHeightOp;
        this.fontsOp = fontsOp;
        this.fontTextOp = fontTextOp;
        this.fontWidthOp = fontWidthOp;
        this.fadeTextOp = fadeTextOp;
        this.textGradientOp = textGradientOp;
        this.fontHeightOp = fontHeightOp;
        this.fontLineOp = fontLineOp;
        this.fontBaseOp = fontBaseOp;
        this.fontDropOp = fontDropOp;
        this.shaderOp = shaderOp;
        this.imageOp = imageOp;
        this.meshOp = meshOp;
        this.blurOp = blurOp;
        this.itemOp = itemOp;
        this.scissorOp = scissorOp;
        this.resetOp = resetOp;
        this.layerOp = layerOp;
        this.transformOp = transformOp;
        this.textureOp = textureOp;
        this.deltaOp = deltaOp;
        this.quadOp = quadOp;
    }

    /**
     * Рисует один четырёхугольник своим шейдером, задавая руками весь набор
     * вершинных атрибутов. Это самый низкий уровень 2D-отрисовки: через него
     * собираются свои прямоугольники со скруглением, обводки и MSDF-глифы,
     * потому что каждый вызов несёт собственные размеры, цвета и параметры.
     * <p>
     * Цвета идут по углам в порядке обхода: левый верхний, левый нижний,
     * правый нижний, правый верхний. Вершины приходят в шейдер как
     * {@code Position}, {@code Color}, {@code UV0} — точка внутри куска
     * текстуры, {@code ivec2 UV1}, {@code ivec2 UV2} и {@code float LineWidth}.
     * <p>
     * Полностью прозрачные четырёхугольники и несобравшиеся шейдеры
     * отбрасываются: невалидный пайплайн в кадр не попадает.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param texture   путь внутри папки textures пака скриптов либо полный
     *                  идентификатор; {@code null} — рисовать без текстуры.
     *                  С текстурой шейдер обязан объявить {@code sampler2D Sampler0}
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param u         левый край куска текстуры, доля от 0 до 1
     * @param v         верхний край куска текстуры, доля от 0 до 1
     * @param uWidth    ширина куска текстуры, доля от 0 до 1
     * @param vHeight   высота куска текстуры, доля от 0 до 1
     * @param uv1x      целое в {@code UV1.x}; от -32768 до 32767
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color1    цвет левого верхнего угла {@code 0xAARRGGBB}
     * @param color2    цвет левого нижнего угла
     * @param color3    цвет правого нижнего угла
     * @param color4    цвет правого верхнего угла
     */
    public void drawQuad(Shader shader, String texture, float x, float y, float width, float height,
                         float u, float v, float uWidth, float vHeight,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth,
                         int color1, int color2, int color3, int color4) {
        drawQuad(shader, texture, DEFAULT_SAMPLER, x, y, width, height, u, v, uWidth, vHeight,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color1, color2, color3, color4);
    }

    /**
     * То же, что
     * {@link #drawQuad(Shader, String, float, float, float, float, float, float, float, float, int, int, int, int, float, int, int, int, int)},
     * но с явным семплером текстуры.
     * <p>
     * Семплер собирается сложением фильтрации и заворачивания:
     * {@code LINEAR | CLAMP} для MSDF-атласа, {@code NEAREST | CLAMP} для
     * пиксельной картинки. {@link #DEFAULT_SAMPLER} оставляет тот семплер,
     * который прописан у самой текстуры.
     * <p>
     * Семплеры берутся из общего кеша клиента, поэтому просить их на каждом
     * кадре ничего не стоит. Без текстуры аргумент не значит ничего.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param texture   путь внутри папки textures пака скриптов либо полный
     *                  идентификатор; {@code null} — рисовать без текстуры
     * @param sampler   {@link #NEAREST} или {@link #LINEAR}, сложенное с
     *                  {@link #CLAMP} или {@link #REPEAT}; либо {@link #DEFAULT_SAMPLER}
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param u         левый край куска текстуры, доля от 0 до 1
     * @param v         верхний край куска текстуры, доля от 0 до 1
     * @param uWidth    ширина куска текстуры, доля от 0 до 1
     * @param vHeight   высота куска текстуры, доля от 0 до 1
     * @param uv1x      целое в {@code UV1.x}; от -32768 до 32767
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color1    цвет левого верхнего угла {@code 0xAARRGGBB}
     * @param color2    цвет левого нижнего угла
     * @param color3    цвет правого нижнего угла
     * @param color4    цвет правого верхнего угла
     */
    public void drawQuad(Shader shader, String texture, int sampler, float x, float y, float width, float height,
                         float u, float v, float uWidth, float vHeight,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth,
                         int color1, int color2, int color3, int color4) {
        drawQuad(shader, texture, sampler, x, y, width, height, u, v, uWidth, vHeight,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color1, color2, color3, color4, NORMAL);
    }

    /**
     * Тот же четырёхугольник, но со своим смешиванием. Про остальные параметры —
     * {@link #drawQuad(Shader, String, int, float, float, float, float, float, float, float, float, int, int, int, int, float, int, int, int, int)}.
     *
     * @param blend {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     */
    public void drawQuad(Shader shader, String texture, int sampler, float x, float y, float width, float height,
                         float u, float v, float uWidth, float vHeight,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth,
                         int color1, int color2, int color3, int color4, int blend) {
        if (shader != null) {
            quadOp.run(shader.name(), texture, sampler, x, y, width, height, u, v, uWidth, vHeight,
                    uv1x, uv1y, uv2x, uv2y, lineWidth, color1, color2, color3, color4, blend);
        }
    }

    /**
     * Четырёхугольник с текстурой, явным семплером и одним цветом на все углы.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param texture   путь внутри папки textures пака скриптов либо полный идентификатор
     * @param sampler   {@link #NEAREST} или {@link #LINEAR}, сложенное с
     *                  {@link #CLAMP} или {@link #REPEAT}; либо {@link #DEFAULT_SAMPLER}
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param u         левый край куска текстуры, доля от 0 до 1
     * @param v         верхний край куска текстуры, доля от 0 до 1
     * @param uWidth    ширина куска текстуры, доля от 0 до 1
     * @param vHeight   высота куска текстуры, доля от 0 до 1
     * @param uv1x      целое в {@code UV1.x}
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color     цвет всех углов {@code 0xAARRGGBB}
     */
    public void drawQuad(Shader shader, String texture, int sampler, float x, float y, float width, float height,
                         float u, float v, float uWidth, float vHeight,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth, int color) {
        drawQuad(shader, texture, sampler, x, y, width, height, u, v, uWidth, vHeight,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color, color, color, color);
    }

    /**
     * Четырёхугольник с текстурой и одним цветом на все углы.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param texture   путь внутри папки textures пака скриптов либо полный идентификатор
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param u         левый край куска текстуры, доля от 0 до 1
     * @param v         верхний край куска текстуры, доля от 0 до 1
     * @param uWidth    ширина куска текстуры, доля от 0 до 1
     * @param vHeight   высота куска текстуры, доля от 0 до 1
     * @param uv1x      целое в {@code UV1.x}
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color     цвет всех углов {@code 0xAARRGGBB}
     */
    public void drawQuad(Shader shader, String texture, float x, float y, float width, float height,
                         float u, float v, float uWidth, float vHeight,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth, int color) {
        drawQuad(shader, texture, x, y, width, height, u, v, uWidth, vHeight,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color, color, color, color);
    }

    /**
     * Четырёхугольник без текстуры: {@code UV0} пробегает от 0 до 1 по обеим осям.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param uv1x      целое в {@code UV1.x}
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color1    цвет левого верхнего угла {@code 0xAARRGGBB}
     * @param color2    цвет левого нижнего угла
     * @param color3    цвет правого нижнего угла
     * @param color4    цвет правого верхнего угла
     */
    public void drawQuad(Shader shader, float x, float y, float width, float height,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth,
                         int color1, int color2, int color3, int color4) {
        drawQuad(shader, null, x, y, width, height, 0f, 0f, 1f, 1f,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color1, color2, color3, color4);
    }

    /**
     * Четырёхугольник без текстуры и с одним цветом на все углы.
     *
     * @param shader    хэндл из {@link Shaders}
     * @param x         левый край
     * @param y         верхний край
     * @param width     ширина
     * @param height    высота
     * @param uv1x      целое в {@code UV1.x}
     * @param uv1y      целое в {@code UV1.y}
     * @param uv2x      целое в {@code UV2.x}
     * @param uv2y      целое в {@code UV2.y}
     * @param lineWidth число с плавающей точкой в {@code LineWidth}
     * @param color     цвет всех углов {@code 0xAARRGGBB}
     */
    public void drawQuad(Shader shader, float x, float y, float width, float height,
                         int uv1x, int uv1y, int uv2x, int uv2y, float lineWidth, int color) {
        drawQuad(shader, null, x, y, width, height, 0f, 0f, 1f, 1f,
                uv1x, uv1y, uv2x, uv2y, lineWidth, color, color, color, color);
    }

    /**
     * Рисует готовый меш своим шейдером.
     *
     * @param mesh   меш из {@link Gpu}
     * @param shader шейдер из {@link Shaders}
     * @param x      куда сместить меш по горизонтали
     * @param y      куда сместить меш по вертикали
     * @param scale  во сколько раз растянуть
     * @param tint   цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawMesh(Mesh mesh, Shader shader, float x, float y, float scale, int tint) {
        drawMesh(mesh, shader, null, x, y, scale, tint);
    }

    /**
     * Рисует готовый меш своим шейдером, привязав к нему текстуру.
     * <p>
     * Шейдер обязан объявить {@code uniform sampler2D Sampler0} — под текстуру
     * собирается отдельный пайплайн. Если такого семплера в исходниках нет,
     * пайплайн не соберётся и вызов молча ничего не нарисует.
     *
     * @param mesh    меш из {@link Gpu}
     * @param shader  шейдер из {@link Shaders}
     * @param texture путь внутри папки textures пака скриптов ({@code "logo.png"})
     *                либо полный идентификатор ({@code "minecraft:textures/misc/white.png"});
     *                {@code null} — рисовать без текстуры
     * @param x       куда сместить меш по горизонтали
     * @param y       куда сместить меш по вертикали
     * @param scale   во сколько раз растянуть
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawMesh(Mesh mesh, Shader shader, String texture,
                         float x, float y, float scale, int tint) {
        drawMesh(mesh, shader, texture, DEFAULT_SAMPLER, x, y, scale, tint);
    }

    /**
     * Рисует готовый меш своим шейдером, привязав к нему текстуру с явным
     * семплером.
     * <p>
     * Семплер собирается сложением фильтрации и заворачивания:
     * {@code LINEAR | CLAMP}, {@code NEAREST | REPEAT} и так далее.
     * {@link #DEFAULT_SAMPLER} оставляет семплер самой текстуры.
     *
     * @param mesh    меш из {@link Gpu}
     * @param shader  шейдер из {@link Shaders}
     * @param texture путь внутри папки textures пака скриптов либо полный
     *                идентификатор; {@code null} — рисовать без текстуры
     * @param sampler {@link #NEAREST} или {@link #LINEAR}, сложенное с
     *                {@link #CLAMP} или {@link #REPEAT}; либо {@link #DEFAULT_SAMPLER}
     * @param x       куда сместить меш по горизонтали
     * @param y       куда сместить меш по вертикали
     * @param scale   во сколько раз растянуть
     * @param tint    цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawMesh(Mesh mesh, Shader shader, String texture, int sampler,
                         float x, float y, float scale, int tint) {
        drawMesh(mesh, shader, texture, sampler, x, y, scale, tint, NORMAL);
    }

    /**
     * Тот же меш, но со своим смешиванием. Про остальные параметры —
     * {@link #drawMesh(Mesh, Shader, String, int, float, float, float, int)}.
     *
     * @param blend {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     */
    public void drawMesh(Mesh mesh, Shader shader, String texture, int sampler,
                         float x, float y, float scale, int tint, int blend) {
        if (mesh != null && shader != null) {
            meshOp.run(mesh.name(), shader.name(), texture, sampler, x, y, scale, tint, blend);
        }
    }

    /**
     * Рисует иконку предмета размером с инвентарную клетку.
     *
     * @param item снимок предмета; {@code null} и пустой слот игнорируются
     * @param x    левый край иконки
     * @param y    верхний край иконки
     * @return была ли иконка поставлена в кадр
     */
    public boolean drawItem(Item item, float x, float y) {
        return drawItem(item, x, y, ITEM_SIZE);
    }

    /**
     * Рисует иконку предмета заданного размера.
     *
     * @param item снимок предмета; {@code null} и пустой слот игнорируются
     * @param x    левый край иконки
     * @param y    верхний край иконки
     * @param size сторона иконки в единицах интерфейса; {@code 16} — как в инвентаре
     * @return была ли иконка поставлена в кадр
     */
    public boolean drawItem(Item item, float x, float y, float size) {
        return item != null && !item.empty()
                && itemOp.run(item.id(), item.handle(), x, y, size,
                        item.count(), item.damage(), item.maxDamage());
    }

    /**
     * Рисует иконку предмета вместе с подписью количества и полоской прочности,
     * как в инвентаре. Данные берутся из снимка предмета.
     *
     * @param item        снимок предмета; {@code null} и пустой слот игнорируются
     * @param x           левый край иконки
     * @param y           верхний край иконки
     * @param size        сторона иконки в единицах интерфейса; {@code 16} — как в инвентаре
     * @param decorations рисовать ли количество и прочность
     * @return была ли иконка поставлена в кадр
     */
    public boolean drawItem(Item item, float x, float y, float size, boolean decorations) {
        if (item == null || item.empty()) {
            return false;
        }
        return decorations
                ? itemOp.run(item.id(), item.handle(), x, y, size,
                        item.count(), item.damage(), item.maxDamage())
                : itemOp.run(item.id(), item.handle(), x, y, size, 1, 0, 0);
    }

    /**
     * Рисует иконку предмета размером с инвентарную клетку.
     *
     * @param id id предмета: {@code "totem_of_undying"} или {@code "minecraft:totem_of_undying"}
     * @param x  левый край иконки
     * @param y  верхний край иконки
     * @return была ли иконка поставлена в кадр
     */
    public boolean drawItem(String id, float x, float y) {
        return drawItem(id, x, y, ITEM_SIZE);
    }

    /**
     * Рисует иконку предмета заданного размера.
     * <p>
     * Иконки копятся до конца кадра и уходят в отрисовку одной пачкой поверх
     * всего, что скрипт нарисовал до них: иначе полупрозрачные подложки легли
     * бы сверху и предмет вышел бы бледным.
     *
     * @param id   id предмета: {@code "totem_of_undying"} или {@code "minecraft:totem_of_undying"}
     * @param x    левый край иконки
     * @param y    верхний край иконки
     * @param size сторона иконки в единицах интерфейса; {@code 16} — как в инвентаре
     * @return была ли иконка поставлена в кадр; {@code false} у неизвестного id
     */
    public boolean drawItem(String id, float x, float y, float size) {
        return itemOp.run(id, 0L, x, y, size, 1, 0, 0);
    }

    /**
     * Рисует иконку предмета с подписью количества, как в инвентаре.
     *
     * @param id    id предмета: {@code "totem_of_undying"} или {@code "minecraft:totem_of_undying"}
     * @param x     левый край иконки
     * @param y     верхний край иконки
     * @param size  сторона иконки в единицах интерфейса; {@code 16} — как в инвентаре
     * @param count размер стака; {@code 1} и меньше рисуются без подписи
     * @return была ли иконка поставлена в кадр; {@code false} у неизвестного id
     */
    public boolean drawItem(String id, float x, float y, float size, int count) {
        return itemOp.run(id, 0L, x, y, size, count, 0, 0);
    }

    /**
     * Обрезает всё, что рисуется дальше, прямоугольником. Вызовы вкладываются:
     * каждый следующий пересекается с предыдущим, и на каждый нужен свой
     * {@link #clearScissor()}. Незакрытые обрезки снимаются в конце кадра.
     *
     * @param x      левая граница
     * @param y      верхняя граница
     * @param width  ширина; {@code 0} и меньше прячут всё
     * @param height высота; {@code 0} и меньше прячут всё
     */
    public void scissor(float x, float y, float width, float height) {
        scissorOp.run(x, y, width, height);
    }

    /**
     * Снимает последнюю обрезку, поставленную
     * {@link #scissor(float, float, float, float)}. Лишний вызов ничего не делает.
     */
    /**
     * Открывает новый слой: всё, что нарисовано дальше, ляжет поверх всего
     * нарисованного до этого вызова.
     * <p>
     * Без него порядок между разными шейдерами внутри одного слоя решает
     * клиент, и он не обязан совпадать с порядком вызовов. Зови там, где
     * перекрытие обязано лечь именно так: всплывающее окно поверх панели,
     * иконка поверх подложки.
     */
    /**
     * Запоминает систему координат. Всё, что сделают {@link #translate},
     * {@link #scale} и {@link #rotate} дальше, снимется парным {@link #pop()}.
     * Незакрытые вызовы снимаются в конце кадра сами.
     */
    public void push() {
        transformOp.run(0, 0f, 0f);
    }

    /**
     * Возвращает систему координат к состоянию на момент {@link #push()}.
     */
    public void pop() {
        transformOp.run(1, 0f, 0f);
    }

    /**
     * Сдвигает начало координат.
     *
     * @param x сдвиг по горизонтали
     * @param y сдвиг по вертикали
     */
    public void translate(float x, float y) {
        transformOp.run(2, x, y);
    }

    /**
     * Растягивает всё, что рисуется дальше, относительно начала координат.
     *
     * @param x множитель по горизонтали
     * @param y множитель по вертикали
     */
    public void scale(float x, float y) {
        transformOp.run(3, x, y);
    }

    /**
     * Растягивает относительно заданной точки — например от центра панели.
     *
     * @param scale   множитель по обеим осям
     * @param originX точка, вокруг которой тянуть, по горизонтали
     * @param originY точка, вокруг которой тянуть, по вертикали
     */
    public void scale(float scale, float originX, float originY) {
        translate(originX, originY);
        scale(scale, scale);
        translate(-originX, -originY);
    }

    /**
     * Поворачивает вокруг начала координат.
     *
     * @param degrees угол в градусах, по часовой стрелке
     */
    public void rotate(float degrees) {
        transformOp.run(4, degrees, 0f);
    }

    /**
     * Поворачивает вокруг заданной точки.
     *
     * @param degrees угол в градусах, по часовой стрелке
     * @param originX центр поворота по горизонтали
     * @param originY центр поворота по вертикали
     */
    public void rotate(float degrees, float originX, float originY) {
        translate(originX, originY);
        rotate(degrees);
        translate(-originX, -originY);
    }

    public void newLayer() {
        layerOp.run();
    }

    public void clearScissor() {
        resetOp.run();
    }

    /**
     * Рисует текстуру целиком.
     *
     * @param id     путь внутри папки textures пака скриптов ({@code "logo.png"})
     *               либо полный идентификатор ({@code "minecraft:textures/gui/sprites/hud/heart/full.png"})
     * @param x      левый край
     * @param y      верхний край
     * @param width  ширина
     * @param height высота
     * @param color  цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawTexture(String id, float x, float y, float width, float height, int color) {
        textureOp.run(id, DEFAULT_SAMPLER, x, y, width, height, 0f, 0f, 1f, 1f, 0f, color);
    }

    /**
     * Рисует текстуру целиком с явным семплером.
     * <p>
     * Семплер собирается сложением фильтрации и заворачивания:
     * {@code LINEAR | CLAMP} сглаживает картинку при уменьшении,
     * {@code NEAREST | CLAMP} держит пиксели резкими.
     * {@link #DEFAULT_SAMPLER} оставляет семплер самой текстуры.
     *
     * @param id      путь внутри папки textures пака скриптов ({@code "logo.png"})
     *                либо полный идентификатор
     * @param sampler {@link #NEAREST} или {@link #LINEAR}, сложенное с
     *                {@link #CLAMP} или {@link #REPEAT}; либо {@link #DEFAULT_SAMPLER}
     * @param x       левый край
     * @param y       верхний край
     * @param width   ширина
     * @param height  высота
     * @param color   цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawTexture(String id, int sampler, float x, float y, float width, float height, int color) {
        textureOp.run(id, sampler, x, y, width, height, 0f, 0f, 1f, 1f, 0f, color);
    }

    /**
     * Рисует кусок текстуры. Координаты куска — доли от размера текстуры, а не
     * пиксели: лицо со скина игрока это {@code u = 0.125}, {@code v = 0.125},
     * {@code uWidth = 0.125}, {@code vHeight = 0.125}.
     *
     * @param id      путь внутри папки textures пака скриптов либо полный идентификатор
     * @param x       левый край
     * @param y       верхний край
     * @param width   ширина
     * @param height  высота
     * @param u       левый край куска, доля от 0 до 1
     * @param v       верхний край куска, доля от 0 до 1
     * @param uWidth  ширина куска, доля от 0 до 1
     * @param vHeight высота куска, доля от 0 до 1
     * @param color   цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawTexture(String id, float x, float y, float width, float height,
                            float u, float v, float uWidth, float vHeight, int color) {
        textureOp.run(id, DEFAULT_SAMPLER, x, y, width, height, u, v, uWidth, vHeight, 0f, color);
    }

    /**
     * Рисует кусок текстуры со скруглёнными углами.
     *
     * @param id      путь внутри папки textures пака скриптов либо полный идентификатор
     * @param x       левый край
     * @param y       верхний край
     * @param width   ширина
     * @param height  высота
     * @param u       левый край куска, доля от 0 до 1
     * @param v       верхний край куска, доля от 0 до 1
     * @param uWidth  ширина куска, доля от 0 до 1
     * @param vHeight высота куска, доля от 0 до 1
     * @param round   радиус скругления углов
     * @param color   цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawTexture(String id, float x, float y, float width, float height,
                            float u, float v, float uWidth, float vHeight, float round, int color) {
        textureOp.run(id, DEFAULT_SAMPLER, x, y, width, height, u, v, uWidth, vHeight, round, color);
    }

    /**
     * Рисует кусок текстуры со скруглёнными углами и явным семплером. Это самая
     * полная форма: у неё есть и кусок, и скругление, и выбор семплера — если
     * скругление не нужно, передай {@code round = 0}.
     *
     * @param id      путь внутри папки textures пака скриптов либо полный идентификатор
     * @param sampler {@link #NEAREST} или {@link #LINEAR}, сложенное с
     *                {@link #CLAMP} или {@link #REPEAT}; либо {@link #DEFAULT_SAMPLER}
     * @param x       левый край
     * @param y       верхний край
     * @param width   ширина
     * @param height  высота
     * @param u       левый край куска, доля от 0 до 1
     * @param v       верхний край куска, доля от 0 до 1
     * @param uWidth  ширина куска, доля от 0 до 1
     * @param vHeight высота куска, доля от 0 до 1
     * @param round   радиус скругления углов; {@code 0} — прямые углы
     * @param color   цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawTexture(String id, int sampler, float x, float y, float width, float height,
                            float u, float v, float uWidth, float vHeight, float round, int color) {
        textureOp.run(id, sampler, x, y, width, height, u, v, uWidth, vHeight, round, color);
    }

    /**
     * Доля тика, прошедшая к этому кадру. Считай по ней анимации: значение
     * растёт вместе с кадрами и не зависит от системных часов.
     *
     * @return число от 0 до 1
     */
    /**
     * Размывает то, что уже нарисовано под прямоугольником. Скруглённое, со
     * сглаженным краем — так делают матовые панели интерфейса.
     *
     * @param x      левая граница
     * @param y      верхняя граница
     * @param width  ширина
     * @param height высота
     * @param round  радиус скругления
     * @param smooth сглаживание края, 1 — обычное
     * @param radius сила размытия в пикселях
     * @param alpha  непрозрачность от 0 до 1
     */
    public void drawBlur(float x, float y, float width, float height,
                         float round, float smooth, float radius, float alpha) {
        drawBlur(x, y, width, height, round, smooth, radius, alpha, 0xFFFFFFFF);
    }

    /**
     * Рисует размытую подложку, затонированную цветом. Тон накладывается на
     * размытие в том же проходе, поэтому подложка остаётся одной поверхностью
     * и гаснет ровно по {@code alpha} — в отличие от отдельного цветного
     * прямоугольника поверх, который на полупрозрачности даёт другую картинку.
     *
     * @param x      левый край
     * @param y      верхний край
     * @param width  ширина
     * @param height высота
     * @param round  радиус скругления
     * @param smooth мягкость края
     * @param radius сила размытия
     * @param alpha  непрозрачность подложки от 0 до 1
     * @param color  цветовой множитель {@code 0xAARRGGBB}; альфа цвета не влияет
     */
    public void drawBlur(float x, float y, float width, float height,
                         float round, float smooth, float radius, float alpha, int color) {
        if (width > 0f && height > 0f) {
            blurOp.run(x, y, width, height, round, smooth, radius, alpha, color);
        }
    }

    public float delta() {
        return deltaOp.run();
    }

    /**
     * Рисует картинку из {@code Hex/scripts/resources/assets/script/textures}.
     *
     * @param path   путь внутри папки textures, например {@code "logo.png"}
     * @param x      левый край
     * @param y      верхний край
     * @param width  ширина
     * @param height высота
     * @param color  цветовой множитель {@code 0xAARRGGBB}, {@code -1} — без изменений
     */
    public void drawImage(String path, float x, float y, float width, float height, int color) {
        imageOp.run(path, x, y, width, height, color);
    }

    /**
     * Рисует прямоугольник своим шейдером.
     *
     * @param shader хэндл из {@link Shaders}
     * @param x      левый край
     * @param y      верхний край
     * @param width  ширина
     * @param height высота
     * @param color  цвет вершин {@code 0xAARRGGBB}
     * @param first  первый параметр, приезжает в шейдер как {@code UV2.x / 256}
     * @param second второй параметр, приезжает как {@code UV2.y / 256}
     */
    public void shader(Shader shader, float x, float y, float width, float height,
                       int color, float first, float second) {
        shader(shader, x, y, width, height, color, first, second, NORMAL);
    }

    /**
     * Тот же прямоугольник, но со своим смешиванием. Про остальные параметры —
     * {@link #shader(Shader, float, float, float, float, int, float, float)}.
     *
     * @param blend {@link #NORMAL}, {@link #ADDITIVE} или {@link #OVERWRITE}
     */
    public void shader(Shader shader, float x, float y, float width, float height,
                       int color, float first, float second, int blend) {
        if (shader != null) {
            shaderOp.run(shader.name(), x, y, width, height, color, first, second, blend);
        }
    }

    /**
     * Рисует прямоугольник своим шейдером без дополнительных параметров.
     *
     * @param shader хэндл из {@link Shaders}
     * @param x      левый край
     * @param y      верхний край
     * @param width  ширина
     * @param height высота
     * @param color  цвет вершин {@code 0xAARRGGBB}
     */
    public void shader(Shader shader, float x, float y, float width, float height, int color) {
        shader(shader, x, y, width, height, color, 0f, 0f);
    }

    /**
     * Все шрифты клиента, доступные скриптам. Имя из этого списка принимают
     * {@link #drawText}, {@link #textWidth(String, String, float, float)} и
     * {@link #textHeight(String, float)}.
     *
     * @return имена шрифтов
     */
    public String[] fonts() {
        return fontsOp.run();
    }

    /**
     * Рисует текст выбранным шрифтом клиента.
     *
     * @param font      имя шрифта из {@link #fonts()}; неизвестное имя даёт шрифт по умолчанию
     * @param text      текст
     * @param x         левый край
     * @param y         верхний край
     * @param size      высота строки
     * @param thickness межбуквенный отступ, обычно от -0.1 до 0.2
     * @param color     цвет {@code 0xAARRGGBB}
     * @return правый край отрисованного текста
     */
    public float drawText(String font, String text, float x, float y, float size, float thickness, int color) {
        return fontTextOp.run(font, text, x, y, size, thickness, color);
    }

    /**
     * @param font      имя шрифта из {@link #fonts()}
     * @param text      текст
     * @param size      высота строки
     * @param thickness межбуквенный отступ
     * @return ширина текста
     */
    public float textWidth(String font, String text, float size, float thickness) {
        return fontWidthOp.run(font, text, size, thickness);
    }

    /**
     * Рисует оформленный текст: цвет и стили берутся из самой сборки.
     *
     * @param font      имя шрифта из {@link #fonts()}
     * @param text      сборка {@link Text}
     * @param x         левая граница текста
     * @param y         верхняя граница текста
     * @param size      кегль
     * @param thickness добавка к толщине, 0 — как есть
     * @return ширина нарисованного текста
     */
    public float drawText(String font, Text text, float x, float y, float size, float thickness) {
        return fontTextOp.run(font, text, x, y, size, thickness, -1);
    }

    /**
     * Рисует оформленный текст, задавая цвет кускам без своего.
     *
     * @param font      имя шрифта из {@link #fonts()}
     * @param text      сборка {@link Text}
     * @param x         левая граница текста
     * @param y         верхняя граница текста
     * @param size      кегль
     * @param thickness добавка к толщине, 0 — как есть
     * @param color     цвет по умолчанию в ARGB
     * @return ширина нарисованного текста
     */
    public float drawText(String font, Text text, float x, float y, float size, float thickness, int color) {
        return fontTextOp.run(font, text, x, y, size, thickness, color);
    }

    /**
     * @param font      имя шрифта из {@link #fonts()}
     * @param text      сборка {@link Text}
     * @param size      кегль
     * @param thickness добавка к толщине
     * @return ширина оформленного текста
     */
    public float textWidth(String font, Text text, float size, float thickness) {
        return fontWidthOp.run(font, text, size, thickness);
    }

    /**
     * @param font имя шрифта из {@link #fonts()}
     * @param size высота строки
     * @return высота строки этого шрифта
     */
    /**
     * Рисует строку, обрезанную по ширине: последние {@code fade} единиц
     * плавно уходят в прозрачность вместо резкого среза. Если строка целиком
     * влезает в {@code maxWidth}, рисуется как обычно.
     *
     * @param font      имя шрифта из {@link #fonts()}
     * @param text      строка
     * @param x         левая граница текста
     * @param y         верхняя граница текста
     * @param size      кегль
     * @param thickness добавка к толщине, 0 — как есть
     * @param color     цвет {@code 0xAARRGGBB}
     * @param maxWidth  предельная ширина в единицах интерфейса
     * @param fade      ширина затухания у правого края
     */
    public void drawTextFaded(String font, String text, float x, float y, float size, float thickness,
                              int color, float maxWidth, float fade) {
        if (text != null && !text.isEmpty() && maxWidth > 0f) {
            fadeTextOp.run(font, text, x, y, size, thickness, color, maxWidth, fade);
        }
    }

    /**
     * Рисует текст бегущим градиентом между двумя цветами. Сам градиент
     * считает и кеширует клиент, скрипту не нужно собирать раскраску по
     * символам каждый кадр.
     * <p>
     * {@code key} — постоянное имя этого текста: пока оно не меняется,
     * клиент переиспользует одну запись кеша, даже если цвета анимированы.
     * Пустой ключ означает кеш по самому тексту и цветам.
     *
     * @param font      имя шрифта из {@link #fonts()}
     * @param key       постоянный ключ кеша; может быть пустым
     * @param text      строка
     * @param x         левая граница текста
     * @param y         верхняя граница текста
     * @param size      кегль
     * @param thickness добавка к толщине, 0 — как есть
     * @param color     базовый цвет; из него берётся прозрачность
     * @param from      цвет начала градиента
     * @param to        цвет конца градиента
     * @param power     на сколько символов растянута волна
     * @param speed     скорость бега; меньше — быстрее
     */
    public void drawTextGradient(String font, String key, String text, float x, float y, float size,
                                 float thickness, int color, int from, int to, int power, int speed) {
        if (text != null && !text.isEmpty()) {
            textGradientOp.run(font, key, text, x, y, size, thickness, color, from, to, power, speed);
        }
    }

    public float textHeight(String font, float size) {
        return fontHeightOp.run(font, size);
    }

    /**
     * Реальная высота строки: расстояние от верха выносного элемента до низа
     * подстрочного. Отличается от {@link #textHeight(String, float)}, который
     * отдаёт сам кегль. Годится, чтобы центрировать текст в панели или
     * разложить несколько строк.
     *
     * @param font имя шрифта из {@link #fonts()}
     * @param size кегль
     * @return высота строки в единицах интерфейса
     */
    public float lineHeight(String font, float size) {
        return fontLineOp.run(font, size);
    }

    /**
     * Расстояние от того {@code y}, что принимает {@link #drawText}, вниз до
     * базовой линии — на неё садятся буквы без выносных элементов. Нужно,
     * чтобы посадить текст на заданную базовую линию: передавай в отрисовку
     * {@code нужнаяЛиния - baseline(font, size)}.
     *
     * @param font имя шрифта из {@link #fonts()}
     * @param size кегль
     * @return расстояние до базовой линии в единицах интерфейса
     */
    public float baseline(String font, float size) {
        return fontBaseOp.run(font, size);
    }

    /**
     * Насколько подстрочные элементы вроде «у» и «р» свисают под базовую
     * линию. Чистая метрика шрифта, в отличие от {@link #baseline}.
     *
     * @param font имя шрифта из {@link #fonts()}
     * @param size кегль
     * @return глубина под базовой линией в единицах интерфейса
     */
    public float descender(String font, float size) {
        return fontDropOp.run(font, size);
    }

    /**
     * Ширина экрана в единицах интерфейса.
     *
     * @return ширина
     */
    public int screenWidth() {
        return screenWidthOp.run();
    }

    /**
     * Высота экрана в единицах интерфейса.
     *
     * @return высота
     */
    public int screenHeight() {
        return screenHeightOp.run();
    }

    /**
     * Прямоугольник с независимыми цветами и радиусами углов. Порядок углов —
     * левый верхний, правый верхний, правый нижний, левый нижний.
     */
    public void drawClientGradientRect(float x, float y, float width, float height,
                                       float round1, float round2, float round3, float round4,
                                       int color1, int color2, int color3, int color4) {
        gradientRectOp.run(x, y, width, height, round1, round2, round3, round4, color1, color2, color3, color4);
    }

    /**
     * Скруглённая обводка с градиентом по четырём углам.
     *
     * @param smooth мягкость края обводки
     */
    public void drawClientOutline(float x, float y, float width, float height, float round, float smooth,
                                  int color1, int color2, int color3, int color4) {
        outlineOp.run(x, y, width, height, round, smooth, color1, color2, color3, color4);
    }

    /**
     * Строка с горизонтальным градиентом от {@code color1} к {@code color2}.
     */
    public void drawClientGradientText(String text, float x, float y, float size, int color1, int color2) {
        gradientTextOp.run(text, x, y, size, color1, color2);
    }

    /**
     * Цвет бегущего градиента текущей темы. {@code index} — смещение по фазе
     * в градусах: 0, 90, 180, 270 дают четыре угла одной волны.
     *
     * @param index смещение фазы
     * @return цвет в ARGB
     */
    public int fade(int index) {
        return fadeOp.run(index);
    }

    /**
     * Ванильный контекст отрисовки текущего кадра —
     * {@code net.minecraft.client.gui.GuiGraphicsExtractor}.
     * <p>
     * Устарело и скоро закроется: скриптам запретят {@code net.minecraft.**}.
     * Всё, ради чего его звали, есть здесь напрямую — предметы
     * {@link #drawItem(String, float, float, float)}, текстуры с UV
     * {@link #drawTexture(String, float, float, float, float, float, float, float, float, int)},
     * обрезка {@link #scissor(float, float, float, float)}, размеры экрана
     * {@link #screenWidth()} и {@link #screenHeight()}.
     * <p>
     * Возвращается как {@link Object}, чтобы SDK не зависел от Minecraft —
     * приводи тип сам. Валиден только внутри {@link Script#onRender2D(Render2D)}.
     *
     * @return контекст отрисовки кадра
     */
    public Object context() {
        return contextOp.run();
    }

    /**
     * Состояние отрисовки текущего кадра —
     * {@code net.minecraft.client.renderer.state.gui.GuiRenderState}.
     * <p>
     * Устарело и скоро закроется: свой элемент с полным набором вершинных
     * атрибутов теперь ставится в кадр без ванили —
     * {@link #drawQuad(Shader, String, float, float, float, float, float, float, float, float, int, int, int, int, float, int, int, int, int)}
     * для отдельного четырёхугольника и
     * {@link #drawMesh(Mesh, Shader, String, float, float, float, int)} для готовой геометрии.
     * <p>
     * Нужно, чтобы скрипт мог отдать в кадр **свой** элемент: реализуй
     * {@code GuiElementRenderState} со своим {@code pipeline()} и
     * {@code buildVertices()} и добавь его через {@code addGuiElement(...)}.
     * Так собственный шейдер попадает в отрисовку, минуя любой чужой рендерер.
     * <p>
     * Поле {@code guiRenderState} в ванили приватное и открывается access
     * widener'ом, который применяется в памяти — компилятор скриптов читает
     * джарник с диска и его не видит. Поэтому объект отдаётся отсюда.
     * <p>
     * Возвращается как {@link Object}, чтобы SDK не зависел от Minecraft.
     *
     * @return состояние отрисовки кадра
     */
    public Object renderState() {
        return stateOp.run();
    }

    /**
     * Рисует прямоугольник со скруглёнными углами.
     *
     * @param x      левая граница
     * @param y      верхняя граница
     * @param width  ширина
     * @param height высота
     * @param round  радиус скругления углов; {@code 0} — прямые углы
     * @param color  цвет заливки в ARGB
     */
    public void drawClientRect(float x, float y, float width, float height, float round, int color) {
        rectOp.run(x, y, width, height, round, color);
    }

    /**
     * Рисует строку шрифтом клиента.
     * <p>
     * Ширину метод намеренно не возвращает: замер строки — это отдельная
     * раскладка шрифта, и делать её на каждой отрисовке впустую дорого. Если
     * ширина нужна для вёрстки, посчитай её через
     * {@link #textWidth(String, float)} и переиспользуй значение.
     *
     * @param text  текст; {@code null} и пустая строка игнорируются
     * @param x     левая граница текста
     * @param y     верхняя граница текста
     * @param size  кегль
     * @param color цвет в ARGB
     */
    public void drawClientText(String text, float x, float y, float size, int color) {
        textOp.run(text, x, y, size, color);
    }

    /**
     * Считает ширину строки, не рисуя её.
     *
     * @param text текст; {@code null} даёт {@code 0}
     * @param size кегль
     * @return ширина в единицах интерфейса
     */
    public float textWidth(String text, float size) {
        return widthOp.run(text, size);
    }

    /**
     * Высота строки для заданного кегля.
     *
     * @param size кегль
     * @return высота в единицах интерфейса
     */
    public float textHeight(float size) {
        return heightOp.run(size);
    }

    /**
     * Цвет из текущей темы клиента. Пользуйся этим вместо захардкоженных
     * констант — скрипт будет выглядеть родным в любой теме пользователя.
     * <p>
     * Известные токены: {@code "basic"}, {@code "basic2"}, {@code "main"},
     * {@code "text"}, {@code "hudText"}, {@code "headerText"},
     * {@code "inactiveText"}, {@code "rect"}, {@code "outline"},
     * {@code "separator"}, {@code "field"}, {@code "inactiveField"},
     * {@code "button"}, {@code "inactiveButton"}, {@code "slider"},
     * {@code "sliderCircle"}, {@code "sliderWindow"}, {@code "switcher"},
     * {@code "inactiveSwitch"}, {@code "window"}, {@code "scrollBar"},
     * {@code "logo"}, {@code "tooltip"}.
     *
     * @param token имя токена; неизвестный токен даёт непрозрачный белый
     * @return цвет в ARGB
     */
    public int themeColor(String token) {
        return themeOp.run(token);
    }
}
