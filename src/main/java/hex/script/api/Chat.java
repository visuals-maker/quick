package hex.script.api;

/**
 * Печать в свой чат и отправка на сервер.
 *
 * <pre>{@code
 * chat().print("health " + player.getHealth());
 * chat().sendToServer("/spawn");
 * }</pre>
 */
public final class Chat {

    /** Локальная печать с подписью. Сообщением может быть строка или {@link Text}. */
    @FunctionalInterface
    public interface PrintOp {
        void run(Object owner, String prefix, Object message);
    }

    /** Отправка сообщения на сервер. */
    @FunctionalInterface
    public interface SendOp {
        void run(String message);
    }

    private final Object owner;
    private final PrintOp printOp;
    private final SendOp sendOp;

    public Chat(Object owner, PrintOp printOp, SendOp sendOp) {
        this.owner = owner;
        this.printOp = printOp;
        this.sendOp = sendOp;
    }

    /**
     * Печатает сообщение с именем скрипта. Видно только тебе. Оформление
     * {@link Text} сохраняется, всё остальное печатается через {@code toString}.
     *
     * @param message текст или сборка {@link Text}
     */
    public void print(Object message) {
        printOp.run(owner, null, message);
    }

    /**
     * Печатает сообщение со своей подписью вместо имени скрипта.
     *
     * @param prefix  подпись
     * @param message текст или сборка {@link Text}
     */
    public void print(String prefix, Object message) {
        printOp.run(owner, prefix, message);
    }

    /**
     * Отправляет сообщение в чат сервера. Ведущий {@code /} отправляет
     * команду сервера. Уходит через очередь клиента, поэтому не рвёт
     * открытый контейнер.
     *
     * @param message текст, пустой игнорируется
     */
    public void sendToServer(String message) {
        if (message != null && !message.isEmpty()) {
            sendOp.run(message);
        }
    }
}
