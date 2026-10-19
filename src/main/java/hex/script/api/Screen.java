package hex.script.api;

/**
 * Экран, открытый поверх игры: чат, инвентарь, сундук, меню клиента. Снимок на
 * момент события — ванильный объект наружу не отдаётся.
 *
 * <pre>{@code
 * on(MouseEvent.class, event -> {
 *     if (event.screen().chat()) {
 *         event.cancel();
 *     }
 * });
 * }</pre>
 */
public final class Screen {

    public static final int NONE = 0;
    public static final int CHAT = 1;
    public static final int INVENTORY = 2;
    public static final int CONTAINER = 3;
    public static final int MENU = 4;
    public static final int OTHER = 5;

    private int kind;
    private String type = "";
    private String title = "";

    /**
     * Заполняет снимок. Вызывает клиент.
     *
     * @param kind  вид экрана
     * @param type  простое имя класса экрана
     * @param title заголовок
     */
    public void set(int kind, String type, String title) {
        this.kind = kind;
        this.type = type == null ? "" : type;
        this.title = title == null ? "" : title;
    }

    /**
     * @return открыт ли какой-нибудь экран
     */
    public boolean open() {
        return kind != NONE;
    }

    /**
     * @return вид: {@link #NONE}, {@link #CHAT}, {@link #INVENTORY},
     * {@link #CONTAINER}, {@link #MENU} или {@link #OTHER}
     */
    public int kind() {
        return kind;
    }

    /**
     * @return простое имя класса экрана, например {@code "ChatScreen"}, или пусто
     */
    public String type() {
        return type;
    }

    /**
     * @return заголовок экрана без оформления
     */
    public String title() {
        return title;
    }

    /**
     * @return открыт ли чат
     */
    public boolean chat() {
        return kind == CHAT;
    }

    /**
     * @return открыт ли свой инвентарь
     */
    public boolean inventory() {
        return kind == INVENTORY;
    }

    /**
     * @return открыт ли контейнер: сундук, печь, торговля
     */
    public boolean container() {
        return kind == CONTAINER;
    }

    /**
     * @return открыто ли меню клиента
     */
    public boolean menu() {
        return kind == MENU;
    }

    /**
     * Совпадает ли имя класса экрана. Сравнение по вхождению без учёта регистра.
     *
     * @param name часть имени, например {@code "Chest"}
     * @return совпало ли
     */
    public boolean is(String name) {
        return name != null && !name.isEmpty() && type.toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public String toString() {
        return open() ? type : "none";
    }
}
