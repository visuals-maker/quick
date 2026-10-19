package hex.script.api;

/**
 * Настройка со своей отрисовкой: клиент выдаёт полосу нужной высоты в окне
 * настроек модуля, а рисует её сам скрипт.
 *
 * <pre>{@code
 * private float value = 0.5f;
 *
 * private final Custom bar = custom("Volume", 16f)
 *         .onDraw(canvas -> {
 *             Render2D render = canvas.render();
 *             render.drawClientRect(canvas.x(), canvas.y() + 6f, canvas.width(), 4f, 2f, canvas.fade(0x60FFFFFF));
 *             render.drawClientRect(canvas.x(), canvas.y() + 6f, canvas.width() * value, 4f, 2f,
 *                     canvas.fade(render.themeColor("basic")));
 *         })
 *         .onClick((mouseX, mouseY, button) -> {
 *             value = mouseX;
 *             return true;
 *         });
 * }</pre>
 *
 * В {@code onClick} и {@code onRelease} координаты приходят долями от области:
 * 0 — левый или верхний край, 1 — правый или нижний.
 */
public final class Custom extends Setting<Custom> {

    /** Отрисовка настройки. */
    @FunctionalInterface
    public interface DrawOp {
        void run(Canvas canvas);
    }

    /** Нажатие или отпускание мыши внутри настройки. */
    @FunctionalInterface
    public interface ClickOp {
        boolean run(float mouseX, float mouseY, int button);
    }

    /** Сериализация значения настройки, если оно у неё есть. */
    @FunctionalInterface
    public interface ValueOp {
        String run();
    }

    /** Разбор значения настройки из конфига. */
    @FunctionalInterface
    public interface ParseOp {
        void run(String raw);
    }

    private float height;
    private DrawOp drawOp;
    private ClickOp clickOp;
    private ClickOp releaseOp;
    private ValueOp writeOp;
    private ParseOp readOp;

    Custom(CheckBox parent, String name, float height) {
        super(parent, name);
        this.height = Math.max(1f, height);
    }

    /**
     * @return высота полосы в меню
     */
    public float height() {
        return height;
    }

    /**
     * @param value высота полосы в меню
     * @return эта же настройка
     */
    public Custom height(float value) {
        this.height = Math.max(1f, value);
        return this;
    }

    /**
     * @param op отрисовка
     * @return эта же настройка
     */
    public Custom onDraw(DrawOp op) {
        this.drawOp = op;
        return this;
    }

    /**
     * @param op нажатие мыши; {@code true} съедает клик
     * @return эта же настройка
     */
    public Custom onClick(ClickOp op) {
        this.clickOp = op;
        return this;
    }

    /**
     * @param op отпускание мыши
     * @return эта же настройка
     */
    public Custom onRelease(ClickOp op) {
        this.releaseOp = op;
        return this;
    }

    /**
     * Учит клиент сохранять значение этой настройки в конфиг скрипта.
     *
     * @param write отдаёт значение строкой
     * @param read  принимает строку из конфига
     * @return эта же настройка
     */
    public Custom persist(ValueOp write, ParseOp read) {
        this.writeOp = write;
        this.readOp = read;
        return this;
    }

    /**
     * Рисует настройку. Клиент зовёт это сам.
     *
     * @param canvas выданная область
     */
    public void draw(Canvas canvas) {
        if (drawOp == null) {
            return;
        }
        try {
            drawOp.run(canvas);
        } catch (Throwable ignored) {
        }
    }

    /**
     * Отдаёт клик настройке. Клиент зовёт это сам.
     *
     * @param mouseX доля по ширине
     * @param mouseY доля по высоте
     * @param button кнопка мыши
     * @return съеден ли клик
     */
    public boolean click(float mouseX, float mouseY, int button) {
        return fire(clickOp, mouseX, mouseY, button);
    }

    /**
     * Отдаёт отпускание настройке. Клиент зовёт это сам.
     *
     * @param mouseX доля по ширине
     * @param mouseY доля по высоте
     * @param button кнопка мыши
     * @return съедено ли событие
     */
    public boolean release(float mouseX, float mouseY, int button) {
        return fire(releaseOp, mouseX, mouseY, button);
    }

    @Override
    public String write() {
        if (writeOp == null) {
            return "";
        }
        try {
            String value = writeOp.run();
            return value == null ? "" : value;
        } catch (Throwable ignored) {
            return "";
        }
    }

    @Override
    public void read(String raw) {
        if (readOp == null || raw == null) {
            return;
        }
        try {
            readOp.run(raw);
        } catch (Throwable ignored) {
        }
    }

    private boolean fire(ClickOp op, float mouseX, float mouseY, int button) {
        if (op == null) {
            return false;
        }
        try {
            return op.run(mouseX, mouseY, button);
        } catch (Throwable ignored) {
            return false;
        }
    }
}
