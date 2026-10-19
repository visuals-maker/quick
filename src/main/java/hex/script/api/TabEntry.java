package hex.script.api;

/**
 * Строка списка игроков (таба) на момент запроса. Ванильный {@code PlayerInfo}
 * наружу не отдаётся, поэтому это копия полей, а не живая ссылка: пинг и
 * гейммод в уже полученной записи не обновятся, перечитывай через
 * {@link Tab#entries()}.
 *
 * <pre>{@code
 * TabEntry entry = tab().of("Notch");
 * if (entry != null && entry.spectator()) {
 *     log().info(entry.name() + " in the observer, ping " + entry.ping());
 * }
 * }</pre>
 */
public final class TabEntry {

    private String name = "";
    private String uuid = "";
    private String displayName = "";
    private String team = "";
    private String prefix = "";
    private String suffix = "";
    private int ping;
    private int gamemode = -1;
    private String skin = "";
    private boolean listed;

    /**
     * Заполняет запись. Вызывает клиент.
     *
     * @param name        ник игрока
     * @param uuid        uuid игрока строкой с дефисами
     * @param displayName что реально показано в табе, плоской строкой
     * @param ping        задержка в миллисекундах
     * @param gamemode    0 выживание, 1 креатив, 2 приключение, 3 наблюдатель, -1 неизвестно
     * @param team        имя команды скорборда или пусто
     * @param prefix      префикс команды плоской строкой
     * @param suffix      суффикс команды плоской строкой
     * @param skin        путь текстуры скина в виде {@code namespace:path}
     * @param listed      показан ли игрок в самом табе
     */
    public void set(String name, String uuid, String displayName, int ping, int gamemode,
                    String team, String prefix, String suffix, String skin, boolean listed) {
        this.name = name == null ? "" : name;
        this.uuid = uuid == null ? "" : uuid;
        this.displayName = displayName == null ? "" : displayName;
        this.ping = ping;
        this.gamemode = gamemode;
        this.team = team == null ? "" : team;
        this.prefix = prefix == null ? "" : prefix;
        this.suffix = suffix == null ? "" : suffix;
        this.skin = skin == null ? "" : skin;
        this.listed = listed;
    }

    /**
     * Путь текстуры скина в виде {@code namespace:path}. Работает и для тех,
     * кого нет в мире рядом — в отличие от {@link Entity#skin()}.
     *
     * @return путь текстуры; пустая строка, если скин ещё не приехал
     */
    public String skin() {
        return skin;
    }

    /**
     * @return ник игрока
     */
    public String name() {
        return name;
    }

    /**
     * @return uuid игрока строкой с дефисами
     */
    public String uuid() {
        return uuid;
    }

    /**
     * Строка, которую видно в табе: с префиксом и суффиксом команды либо с
     * собственным именем, присланным сервером. Оформление снято.
     *
     * @return показанное имя плоской строкой
     */
    public String displayName() {
        return displayName;
    }

    /**
     * @return задержка в миллисекундах, как её отдаёт сервер
     */
    public int ping() {
        return ping;
    }

    /**
     * @return 0 выживание, 1 креатив, 2 приключение, 3 наблюдатель, -1 неизвестно
     */
    public int gamemode() {
        return gamemode;
    }

    /**
     * @return стоит ли у игрока режим наблюдателя
     */
    public boolean spectator() {
        return gamemode == 3;
    }

    /**
     * @return имя команды скорборда или пустая строка
     */
    public String team() {
        return team;
    }

    /**
     * @return префикс команды плоской строкой или пустая строка
     */
    public String prefix() {
        return prefix;
    }

    /**
     * @return суффикс команды плоской строкой или пустая строка
     */
    public String suffix() {
        return suffix;
    }

    /**
     * Сервер может держать игрока в списке, но не показывать его строкой в
     * табе — у такого записи {@code false}.
     *
     * @return показан ли игрок в самом табе
     */
    public boolean listed() {
        return listed;
    }

    /**
     * Сравнивает ник без учёта регистра.
     *
     * @param value ник
     * @return совпало ли
     */
    public boolean is(String value) {
        return value != null && !value.isEmpty() && name.equalsIgnoreCase(value);
    }

    @Override
    public String toString() {
        return name + " (" + ping + "ms)";
    }
}
