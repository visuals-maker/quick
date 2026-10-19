package hex.script.api;

/**
 * Кнопка: значения нет, есть только действие.
 */
public final class Button extends Setting<Button> {

    private final Runnable action;

    Button(CheckBox parent, String name, Runnable action) {
        super(parent, name);
        this.action = action;
    }

    /**
     * Выполняет действие кнопки. Исключения гасятся.
     */
    public void press() {
        if (action == null) {
            return;
        }
        try {
            action.run();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public String write() {
        return "";
    }

    @Override
    public void read(String raw) {
    }
}
