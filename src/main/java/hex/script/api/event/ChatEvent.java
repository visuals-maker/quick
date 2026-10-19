package hex.script.api.event;

import hex.script.api.Text;

/**
 * Пришло сообщение в чат. Отмена не пускает его в чат клиента — сервер о
 * ней не узнает, сообщение просто не показывается.
 *
 * <pre>{@code
 * on(ChatEvent.class, event -> {
 *     if (event.server() && event.contains("joined")) {
 *         event.cancel();
 *     }
 * });
 * }</pre>
 *
 * Приходит в главном потоке, уже собранным текстом, поэтому читать и
 * сравнивать строки тут безопасно.
 */
public final class ChatEvent extends Event {

    /** Сообщение написал игрок. */
    public static final int PLAYER = 0;

    /** Системное сообщение сервера. */
    public static final int SERVER = 1;

    /** Сообщение самого клиента: команды, ошибки, печать скриптов. */
    public static final int CLIENT = 2;

    /** Оформленный текст сообщения, собирается по требованию. */
    @FunctionalInterface
    public interface TextOp {
        Text run();
    }

    private String message;
    private int source;
    private TextOp textOp;
    private Text text;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param message текст сообщения без оформления
     * @param source  откуда пришло: {@link #PLAYER}, {@link #SERVER} или {@link #CLIENT}
     * @param textOp  сборка оформленного текста
     */
    public void set(String message, int source, TextOp textOp) {
        this.message = message == null ? "" : message;
        this.source = source;
        this.textOp = textOp;
        this.text = null;
    }

    @Override
    public String name() {
        return "chatReceive";
    }

    /**
     * @return текст сообщения без цветов и стилей
     */
    public String message() {
        return message;
    }

    /**
     * @return откуда пришло: {@link #PLAYER}, {@link #SERVER} или {@link #CLIENT}
     */
    public int source() {
        return source;
    }

    /**
     * @return написал ли это игрок
     */
    public boolean player() {
        return source == PLAYER;
    }

    /**
     * @return системное ли это сообщение сервера
     */
    public boolean server() {
        return source == SERVER;
    }

    /**
     * @return сам ли клиент это напечатал
     */
    public boolean client() {
        return source == CLIENT;
    }

    /**
     * Оформленный текст с цветами и стилями. Собирается при первом обращении
     * и только для этой отправки — держать его в поле нельзя.
     *
     * @return оформленный текст
     */
    public Text text() {
        if (text == null) {
            text = textOp == null ? Text.of(message) : textOp.run();
        }
        return text;
    }

    /**
     * @param part кусок текста
     * @return есть ли он в сообщении
     */
    public boolean contains(String part) {
        return part != null && !part.isEmpty() && message.contains(part);
    }

    /**
     * @param prefix начало текста
     * @return начинается ли сообщение с него
     */
    public boolean startsWith(String prefix) {
        return prefix != null && !prefix.isEmpty() && message.startsWith(prefix);
    }

    /**
     * Хвост сообщения после первого вхождения куска. Так вынимают значение из
     * серверной строки вроде {@code "Balance: $1,200"}.
     *
     * @param prefix кусок текста
     * @return хвост после него или пустая строка, если куска нет
     */
    public String after(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return "";
        }
        int index = message.indexOf(prefix);
        return index < 0 ? "" : message.substring(index + prefix.length());
    }
}
