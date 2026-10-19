package hex.script.api;

/**
 * Клавиша с действием. Срабатывает по нажатию, пока скрипт включён.
 * Коды клавиш — GLFW, как в событии {@code "key"}.
 */
public final class Hotkey extends Setting<Hotkey> {

    /** Клавиша не назначена. */
    public static final int UNKNOWN = -1;

    private final Runnable action;
    private int key;

    Hotkey(CheckBox parent, String name, int key, Runnable action) {
        super(parent, name);
        this.key = key;
        this.action = action;
    }

    /**
     * @return код клавиши GLFW или {@link #UNKNOWN}
     */
    public int key() {
        return key;
    }

    /**
     * @param code новый код клавиши
     * @return эта же настройка
     */
    public Hotkey key(int code) {
        this.key = code;
        return this;
    }

    /**
     * @return назначена ли клавиша
     */
    public boolean bound() {
        return key != UNKNOWN;
    }

    /**
     * Вызывает действие, если нажата именно эта клавиша.
     *
     * @param pressed код нажатой клавиши
     * @return сработало ли действие
     */
    public boolean press(int pressed) {
        if (key == UNKNOWN || key != pressed || action == null) {
            return false;
        }
        try {
            action.run();
        } catch (Throwable ignored) {
        }
        return true;
    }

    @Override
    public String write() {
        return String.valueOf(key);
    }

    @Override
    public void read(String raw) {
        if (raw == null) {
            return;
        }
        try {
            key = Integer.parseInt(raw.trim());
        } catch (NumberFormatException ignored) {
        }
    }
}
