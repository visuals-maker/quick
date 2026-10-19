package hex.script.api.event;

import hex.script.api.Screen;

/**
 * Клавиша нажата или отпущена.
 *
 * <pre>{@code
 * on(KeyEvent.class, event -> {
 *     if (event.pressed() && event.key() == Keys.F && !event.screen().open()) {
 *         toggle();
 *     }
 * });
 * }</pre>
 */
public final class KeyEvent extends Event {

    private final Screen screen = new Screen();

    private int key;
    private int scancode;
    private int modifiers;
    private boolean pressed;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param key       код клавиши GLFW
     * @param scancode  аппаратный код
     * @param modifiers маска модификаторов GLFW
     * @param pressed   нажата или отпущена
     */
    public void set(int key, int scancode, int modifiers, boolean pressed) {
        this.key = key;
        this.scancode = scancode;
        this.modifiers = modifiers;
        this.pressed = pressed;
    }

    @Override
    public String name() {
        return "key";
    }

    /**
     * @return код клавиши GLFW
     */
    public int key() {
        return key;
    }

    /**
     * @return аппаратный код клавиши
     */
    public int scancode() {
        return scancode;
    }

    /**
     * @return маска модификаторов GLFW
     */
    public int modifiers() {
        return modifiers;
    }

    /**
     * @return нажата ли клавиша; иначе отпущена
     */
    public boolean pressed() {
        return pressed;
    }

    /**
     * @return отпущена ли клавиша
     */
    public boolean released() {
        return !pressed;
    }

    /**
     * @return зажат ли shift
     */
    public boolean shift() {
        return (modifiers & 1) != 0;
    }

    /**
     * @return зажат ли control
     */
    public boolean control() {
        return (modifiers & 2) != 0;
    }

    /**
     * @return зажат ли alt
     */
    public boolean alt() {
        return (modifiers & 4) != 0;
    }

    /**
     * @return экран, открытый в момент нажатия
     */
    public Screen screen() {
        return screen;
    }
}
