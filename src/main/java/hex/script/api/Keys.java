package hex.script.api;

/**
 * Что зажато прямо сейчас. Опрашивает систему напрямую, поэтому остаётся
 * честным и при открытом экране, и не смотрит в бинды клиента.
 *
 * <pre>{@code
 * on("tick", event -> {
 *     if (keys().inputBlocked()) {
 *         return;
 *     }
 *     if (keys().isDown(Keys.LEFT_SHIFT)) {
 *         chat().print("the schift is clamped");
 *     }
 * });
 * }</pre>
 */
public final class Keys {

    /** Состояние: 0 — клавиша, 1 — кнопка мыши, 2 — курсор захвачен, 3 — ввод забран. */
    @FunctionalInterface
    public interface StateOp {
        boolean run(int query, int code);
    }

    /** Позиция курсора: 0 — x, 1 — y. */
    @FunctionalInterface
    public interface CursorOp {
        float run(int query);
    }

    /** Захват курсора. */
    @FunctionalInterface
    public interface GrabOp {
        void run(boolean grab);
    }

    /** Перевод имени клавиши в код и обратно. */
    @FunctionalInterface
    public interface NameOp {
        String run(int code);
    }

    /** Код клавиши по имени. */
    @FunctionalInterface
    public interface CodeOp {
        int run(String name);
    }

    public static final int UNKNOWN = -1;
    public static final int LEFT_SHIFT = 340;
    public static final int LEFT_CONTROL = 341;
    public static final int LEFT_ALT = 342;
    public static final int SPACE = 32;
    public static final int ENTER = 257;
    public static final int TAB = 258;
    public static final int ESCAPE = 256;

    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_RIGHT = 1;
    public static final int MOUSE_MIDDLE = 2;

    private final StateOp stateOp;
    private final CursorOp cursorOp;
    private final GrabOp grabOp;
    private final NameOp nameOp;
    private final CodeOp codeOp;

    public Keys(StateOp stateOp, CursorOp cursorOp, GrabOp grabOp, NameOp nameOp, CodeOp codeOp) {
        this.stateOp = stateOp;
        this.cursorOp = cursorOp;
        this.grabOp = grabOp;
        this.nameOp = nameOp;
        this.codeOp = codeOp;
    }

    /**
     * Зажата ли клавиша прямо сейчас.
     *
     * @param key код GLFW
     * @return зажата ли
     */
    public boolean isDown(int key) {
        return key != UNKNOWN && stateOp.run(0, key);
    }

    /**
     * Зажата ли кнопка мыши прямо сейчас.
     *
     * @param button кнопка GLFW: 0 левая, 1 правая, 2 средняя
     * @return зажата ли
     */
    public boolean mouseDown(int button) {
        return button >= 0 && button <= 7 && stateOp.run(1, button);
    }

    /**
     * @return x курсора в пикселях окна
     */
    public float mouseX() {
        return cursorOp.run(0);
    }

    /**
     * @return y курсора в пикселях окна
     */
    public float mouseY() {
        return cursorOp.run(1);
    }

    /**
     * @return захвачен ли курсор для обзора камерой
     */
    public boolean cursorLocked() {
        return stateOp.run(2, 0);
    }

    /**
     * @return забирает ли ввод чат, меню клиента или текстовое поле
     */
    public boolean inputBlocked() {
        return stateOp.run(3, 0);
    }

    /**
     * Захватывает курсор.
     */
    public void lockCursor() {
        grabOp.run(true);
    }

    /**
     * Отпускает курсор.
     */
    public void unlockCursor() {
        grabOp.run(false);
    }

    /**
     * Подпись клавиши так, как её показывает клиент.
     *
     * @param key код GLFW
     * @return подпись, например {@code "Left Shift"}
     */
    public String name(int key) {
        String value = nameOp.run(key);
        return value == null ? "" : value;
    }

    /**
     * Код клавиши по её подписи.
     *
     * @param name подпись клавиши
     * @return код GLFW или {@link #UNKNOWN}
     */
    public int code(String name) {
        return name == null || name.isEmpty() ? UNKNOWN : codeOp.run(name);
    }
}
