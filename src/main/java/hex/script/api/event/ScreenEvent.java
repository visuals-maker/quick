package hex.script.api.event;

import hex.script.api.Screen;

/**
 * Экран открылся или закрылся.
 *
 * <pre>{@code
 * on(ScreenEvent.class, event -> {
 *     if (event.opened() && event.screen().container()) {
 *         log().info("open " + event.screen().title());
 *     }
 * });
 * }</pre>
 */
public final class ScreenEvent extends Event {

    private final Screen screen = new Screen();

    private boolean opened;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param opened открылся ли экран; иначе закрылся
     */
    public void set(boolean opened) {
        this.opened = opened;
    }

    @Override
    public String name() {
        return "screen";
    }

    /**
     * @return открылся ли экран
     */
    public boolean opened() {
        return opened;
    }

    /**
     * @return закрылся ли экран
     */
    public boolean closed() {
        return !opened;
    }

    /**
     * @return экран: при закрытии — тот, что закрылся
     */
    public Screen screen() {
        return screen;
    }
}
