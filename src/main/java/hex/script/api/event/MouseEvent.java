package hex.script.api.event;

import hex.script.api.Screen;

/**
 * Кнопка мыши нажата или отпущена. Приходит и при открытом экране, поэтому
 * годится для кликов по своему интерфейсу поверх чата.
 *
 * <pre>{@code
 * on(MouseEvent.class, event -> {
 *     if (event.left() && event.pressed() && panel.hit(event.x(), event.y())) {
 *         event.cancel();
 *     }
 * });
 * }</pre>
 *
 * Отмена не пускает клик в игру: ваниль его не увидит.
 */
public final class MouseEvent extends Event {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int MIDDLE = 2;

    private final Screen screen = new Screen();

    private int button;
    private boolean pressed;
    private double x;
    private double y;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param button  кнопка GLFW
     * @param pressed нажата или отпущена
     * @param x       позиция курсора в пикселях окна
     * @param y       позиция курсора в пикселях окна
     */
    public void set(int button, boolean pressed, double x, double y) {
        this.button = button;
        this.pressed = pressed;
        this.x = x;
        this.y = y;
    }

    @Override
    public String name() {
        return "mouse";
    }

    /**
     * @return кнопка GLFW: {@link #LEFT}, {@link #RIGHT}, {@link #MIDDLE}
     */
    public int button() {
        return button;
    }

    /**
     * @return нажата ли кнопка; иначе отпущена
     */
    public boolean pressed() {
        return pressed;
    }

    /**
     * @return отпущена ли кнопка
     */
    public boolean released() {
        return !pressed;
    }

    /**
     * @return левая ли кнопка
     */
    public boolean left() {
        return button == LEFT;
    }

    /**
     * @return правая ли кнопка
     */
    public boolean right() {
        return button == RIGHT;
    }

    /**
     * @return средняя ли кнопка
     */
    public boolean middle() {
        return button == MIDDLE;
    }

    /**
     * @return x курсора в пикселях окна
     */
    public double x() {
        return x;
    }

    /**
     * @return y курсора в пикселях окна
     */
    public double y() {
        return y;
    }

    /**
     * @return экран, открытый в момент клика
     */
    public Screen screen() {
        return screen;
    }
}
