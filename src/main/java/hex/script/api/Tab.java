package hex.script.api;

/**
 * Список игроков (таб) и команды скорборда, только чтение. Отдаёт снимки
 * {@link TabEntry} — ванильные объекты наружу не выдаются.
 *
 * <pre>{@code
 * for (String team : tab().teams()) {
 *     for (String nick : tab().members(team)) {
 *         TabEntry entry = tab().of(nick);
 *         if (entry != null && entry.prefix().contains("Guarantor")) {
 *             chat(nick + " — Staff, ping " + entry.ping());
 *         }
 *     }
 * }
 * }</pre>
 *
 * Без соединения и без мира всё отдаёт пустые массивы и пустые строки.
 */
public final class Tab {

    /** Все записи таба. */
    @FunctionalInterface
    public interface EntriesOp {
        TabEntry[] run();
    }

    /** Запись по нику или {@code null}. */
    @FunctionalInterface
    public interface EntryOp {
        TabEntry run(String name);
    }

    /** Число записей в табе. */
    @FunctionalInterface
    public interface SizeOp {
        int run();
    }

    /** Имена: 0 — команды скорборда, 1 — состав команды. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run(int query, String team);
    }

    /**
     * Текст: 0 — заголовок, 1 — подвал, 2 — префикс команды,
     * 3 — суффикс команды, 4 — отображаемое имя команды.
     */
    @FunctionalInterface
    public interface TextOp {
        String run(int query, String team);
    }

    /** Оформленный текст: 0 — префикс команды, 1 — суффикс команды. */
    @FunctionalInterface
    public interface RichOp {
        Text run(int query, String team);
    }

    private static final TabEntry[] NO_ENTRIES = new TabEntry[0];
    private static final String[] NO_NAMES = new String[0];

    private final EntriesOp entriesOp;
    private final EntryOp entryOp;
    private final SizeOp sizeOp;
    private final NamesOp namesOp;
    private final TextOp textOp;
    private final RichOp richOp;

    public Tab(EntriesOp entriesOp, EntryOp entryOp, SizeOp sizeOp, NamesOp namesOp,
               TextOp textOp, RichOp richOp) {
        this.entriesOp = entriesOp;
        this.entryOp = entryOp;
        this.sizeOp = sizeOp;
        this.namesOp = namesOp;
        this.textOp = textOp;
        this.richOp = richOp;
    }

    /**
     * Все записи таба, отсортированные по нику. Клиент держит готовый массив и
     * пересобирает его, только когда таб реально поменялся — состав, пинг,
     * гейммод, показанное имя или команда, — поэтому звать можно хоть каждый
     * кадр. Массив общий на всех: не меняй его содержимое, а нужный порядок
     * делай на своей копии.
     *
     * @return записи таба, ники по возрастанию
     */
    public TabEntry[] entries() {
        TabEntry[] result = entriesOp.run();
        return result == null ? NO_ENTRIES : result;
    }

    /**
     * Запись по нику, регистр не важен.
     *
     * @param name ник игрока
     * @return запись или {@code null}, если такого в табе нет
     */
    public TabEntry of(String name) {
        return name == null || name.isEmpty() ? null : entryOp.run(name);
    }

    /**
     * @return сколько игроков в табе
     */
    public int size() {
        return sizeOp.run();
    }

    /**
     * Имена команд скорборда по возрастанию. Массив кешируется клиентом и
     * пересобирается только при смене набора команд.
     *
     * @return имена команд
     */
    public String[] teams() {
        String[] result = namesOp.run(0, "");
        return result == null ? NO_NAMES : result;
    }

    /**
     * Ники в команде. В отличие от {@link #teams()} собирается на каждый вызов,
     * так что в цикле по кадрам это мусор — зови по тику.
     *
     * @param team имя команды
     * @return ники или пустой массив, если команды нет
     */
    public String[] members(String team) {
        if (team == null || team.isEmpty()) {
            return NO_NAMES;
        }
        String[] result = namesOp.run(1, team);
        return result == null ? NO_NAMES : result;
    }

    /**
     * @return заголовок таба плоской строкой
     */
    public String header() {
        String result = textOp.run(0, "");
        return result == null ? "" : result;
    }

    /**
     * @return подвал таба плоской строкой
     */
    public String footer() {
        String result = textOp.run(1, "");
        return result == null ? "" : result;
    }

    /**
     * Префикс команды скорборда. В отличие от {@link TabEntry#prefix()}
     * известен и у команды, в которой сейчас нет ни одного игрока в табе.
     *
     * @param team имя команды из {@link #teams()}
     * @return префикс плоской строкой; пусто у неизвестной команды
     */
    public String teamPrefix(String team) {
        return text(2, team);
    }

    /**
     * @param team имя команды из {@link #teams()}
     * @return суффикс команды плоской строкой; пусто у неизвестной команды
     */
    public String teamSuffix(String team) {
        return text(3, team);
    }

    /**
     * @param team имя команды из {@link #teams()}
     * @return отображаемое имя команды; пусто у неизвестной команды
     */
    public String teamDisplayName(String team) {
        return text(4, team);
    }

    /**
     * Префикс команды с сохранённым оформлением: цветами, жирностью и прочим
     * стилем. Плоский вариант — {@link #teamPrefix(String)}.
     *
     * @param team имя команды из {@link #teams()}
     * @return оформленный префикс; пустой у неизвестной команды
     */
    public Text teamPrefixText(String team) {
        return rich(0, team);
    }

    /**
     * @param team имя команды из {@link #teams()}
     * @return оформленный суффикс; пустой у неизвестной команды
     */
    public Text teamSuffixText(String team) {
        return rich(1, team);
    }

    private Text rich(int query, String team) {
        if (team == null || team.isEmpty()) {
            return Text.empty();
        }
        Text result = richOp.run(query, team);
        return result == null ? Text.empty() : result;
    }

    private String text(int query, String team) {
        if (team == null || team.isEmpty()) {
            return "";
        }
        String result = textOp.run(query, team);
        return result == null ? "" : result;
    }
}
