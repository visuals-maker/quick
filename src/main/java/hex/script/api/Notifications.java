package hex.script.api;

/**
 * Всплывающие уведомления клиента.
 *
 * <pre>{@code
 * notifications().info("done", 2000L);
 * }</pre>
 */
public final class Notifications {

    /** Показ уведомления: тип 0 — обычное, 1 — успех, 2 — предупреждение, 3 — ошибка. */
    @FunctionalInterface
    public interface ShowOp {
        void run(String message, long millis, int type);
    }

    private final ShowOp showOp;

    public Notifications(ShowOp showOp) {
        this.showOp = showOp;
    }

    /**
     * @param message текст
     * @param millis  сколько держать на экране
     */
    public void info(String message, long millis) {
        showOp.run(message, millis, 0);
    }

    /**
     * @param message текст
     * @param millis  сколько держать на экране
     */
    public void success(String message, long millis) {
        showOp.run(message, millis, 1);
    }

    /**
     * @param message текст
     * @param millis  сколько держать на экране
     */
    public void warning(String message, long millis) {
        showOp.run(message, millis, 2);
    }

    /**
     * @param message текст
     * @param millis  сколько держать на экране
     */
    public void error(String message, long millis) {
        showOp.run(message, millis, 3);
    }
}
