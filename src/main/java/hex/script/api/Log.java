package hex.script.api;

/**
 * Запись в консоль скриптов: меню — вкладка Scripts — кнопка с терминалом.
 * Туда же клиент кладёт загрузку, включение и всё, что упало внутри скрипта.
 *
 * <pre>{@code
 * log().info("goal " + target());
 * log().error("didn't find the item", error);
 * }</pre>
 */
public final class Log {

    /** Строка лога: 0 — info, 1 — warn, 2 — error. */
    @FunctionalInterface
    public interface LineOp {
        void run(Object owner, int level, String message, Throwable cause);
    }

    private final Object owner;
    private final LineOp lineOp;

    public Log(Object owner, LineOp lineOp) {
        this.owner = owner;
        this.lineOp = lineOp;
    }

    /**
     * Обычная строка.
     *
     * @param message текст
     */
    public void info(Object message) {
        lineOp.run(owner, 0, String.valueOf(message), null);
    }

    /**
     * Предупреждение.
     *
     * @param message текст
     */
    public void warn(Object message) {
        lineOp.run(owner, 1, String.valueOf(message), null);
    }

    /**
     * Ошибка.
     *
     * @param message текст
     */
    public void error(Object message) {
        lineOp.run(owner, 2, String.valueOf(message), null);
    }

    /**
     * Ошибка с причиной. В консоль идёт текст причины, полный стектрейс —
     * в лог игры.
     *
     * @param message текст
     * @param cause   причина
     */
    public void error(Object message, Throwable cause) {
        lineOp.run(owner, 2, String.valueOf(message), cause);
    }
}
