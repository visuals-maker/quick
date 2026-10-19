package hex.script.api;

/**
 * Боковое табло сервера, только чтение. Строки идут сверху вниз, ровно как
 * их рисует клиент: скрытые убраны, порядок по очкам, не больше пятнадцати.
 *
 * <pre>{@code
 * if (scoreboard().visible()) {
 *     render.drawText(font, scoreboard().title(), 10f, 10f, 7f, -1);
 *     String balance = scoreboard().after("Balance: ");
 * }
 * }</pre>
 *
 * Клиент пересобирает табло раз в тик и отдаёт готовое, поэтому спрашивать
 * можно хоть каждый кадр. Без мира и без бокового табло всё пустое.
 */
public final class Scoreboard {

    /** Числа: 0 — сколько строк, 1 — очки строки, 2 — есть ли табло. */
    @FunctionalInterface
    public interface NumberOp {
        int run(int query, int index);
    }

    /** Текст: 0 — заголовок, 1 — строка по номеру. */
    @FunctionalInterface
    public interface TextOp {
        String run(int query, int index);
    }

    /** Оформленный текст: 0 — заголовок, 1 — строка по номеру. */
    @FunctionalInterface
    public interface RichOp {
        Text run(int query, int index);
    }

    /** Все строки одним массивом. */
    @FunctionalInterface
    public interface LinesOp {
        String[] run();
    }

    private static final String[] NO_LINES = new String[0];

    private final NumberOp numberOp;
    private final TextOp textOp;
    private final RichOp richOp;
    private final LinesOp linesOp;

    public Scoreboard(NumberOp numberOp, TextOp textOp, RichOp richOp, LinesOp linesOp) {
        this.numberOp = numberOp;
        this.textOp = textOp;
        this.richOp = richOp;
        this.linesOp = linesOp;
    }

    /**
     * @return висит ли сейчас боковое табло
     */
    public boolean visible() {
        return numberOp.run(2, 0) != 0;
    }

    /**
     * @return заголовок табло плоской строкой
     */
    public String title() {
        String result = textOp.run(0, 0);
        return result == null ? "" : result;
    }

    /**
     * Заголовок с сохранённым оформлением: цветами, жирностью и прочим стилем.
     * Клиент держит готовую сборку и пересобирает её, только когда текст
     * поменялся, поэтому звать можно каждый кадр.
     *
     * @return оформленный заголовок
     */
    public Text titleText() {
        return rich(0, 0);
    }

    /**
     * @return сколько строк на табло
     */
    public int size() {
        return numberOp.run(0, 0);
    }

    /**
     * @param index номер строки, 0 — верхняя
     * @return строка плоской строкой; пусто, если номера нет
     */
    public String line(int index) {
        String result = textOp.run(1, index);
        return result == null ? "" : result;
    }

    /**
     * Строка с цветами сервера. Как и {@link #titleText()}, кешируется до
     * следующей смены текста.
     *
     * @param index номер строки, 0 — верхняя
     * @return оформленная строка; пустая, если номера нет
     */
    public Text lineText(int index) {
        return rich(1, index);
    }

    /**
     * Очки строки — то число, что сервер держит справа. Обычно это просто
     * порядок строк, но некоторые сервера кладут туда данные.
     *
     * @param index номер строки, 0 — верхняя
     * @return очки или 0, если номера нет
     */
    public int score(int index) {
        return numberOp.run(1, index);
    }

    /**
     * Все строки сверху вниз. Массив общий на всех и живёт до следующего
     * тика: не меняй его содержимое, а нужный порядок делай на своей копии.
     *
     * @return строки табло
     */
    public String[] lines() {
        String[] result = linesOp.run();
        return result == null ? NO_LINES : result;
    }

    /**
     * @param part кусок текста
     * @return есть ли строка с ним
     */
    public boolean contains(String part) {
        return find(part) >= 0;
    }

    /**
     * @param part кусок текста
     * @return номер первой строки с ним или {@code -1}
     */
    public int find(String part) {
        if (part == null || part.isEmpty()) {
            return -1;
        }
        String[] all = lines();
        for (int index = 0; index < all.length; index++) {
            if (all[index].contains(part)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Хвост первой строки после куска текста. Так вынимают значение поля:
     * {@code after("Balance: ")} на строке {@code "Balance: $1,200"} отдаст
     * {@code "1200$"}.
     *
     * @param prefix кусок текста
     * @return хвост после него или пустая строка, если такой строки нет
     */
    public String after(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return "";
        }
        for (String line : lines()) {
            int index = line.indexOf(prefix);
            if (index >= 0) {
                return line.substring(index + prefix.length());
            }
        }
        return "";
    }

    private Text rich(int query, int index) {
        Text result = richOp.run(query, index);
        return result == null ? Text.empty() : result;
    }
}
