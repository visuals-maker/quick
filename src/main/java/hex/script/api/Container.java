package hex.script.api;

/**
 * Открытый контейнер: сундук, эндер-сундук, печь, меню сервера. Свой инвентарь
 * контейнером не считается — у него {@link #open()} вернёт {@code false}.
 *
 * <p>Слоты самого контейнера идут первыми: {@code 0..size()-1}. Дальше в том же
 * окне лежит инвентарь игрока — {@link #slot(int)} его не показывает, но клики
 * по нему проходят, поэтому предмет можно закинуть в сундук шифт-кликом.
 *
 * <pre>{@code
 * if (container().open() && container().title().contains("Chest")) {
 *     int slot = container().find("ender_pearl");
 *     if (slot >= 0) {
 *         container().shiftClick(slot);
 *     }
 * }
 * }</pre>
 */
public final class Container {

    /** Хотбарная кнопка обмена для левой руки. */
    public static final int OFFHAND = 40;

    private static final int CLICK = 0;
    private static final int QUICK_MOVE = 1;
    private static final int SWAP = 2;
    private static final int DROP = 3;
    private static final int CLOSE = 4;

    /** Число: 0 — открыт ли контейнер, 1 — номер окна, 2 — сколько в нём слотов. */
    @FunctionalInterface
    public interface NumberOp {
        int run(int query);
    }

    /** Строка: 0 — заголовок окна, 1 — тип меню. */
    @FunctionalInterface
    public interface TextOp {
        String run(int query);
    }

    /** Снимок слота контейнера. */
    @FunctionalInterface
    public interface SlotOp {
        Item run(int index);
    }

    /** Действие: 0 — клик, 1 — шифт-клик, 2 — обмен, 3 — выброс, 4 — закрытие. */
    @FunctionalInterface
    public interface ClickOp {
        boolean run(int action, int slot, int value);
    }

    private final NumberOp numberOp;
    private final TextOp textOp;
    private final SlotOp slotOp;
    private final ClickOp clickOp;

    public Container(NumberOp numberOp, TextOp textOp, SlotOp slotOp, ClickOp clickOp) {
        this.numberOp = numberOp;
        this.textOp = textOp;
        this.slotOp = slotOp;
        this.clickOp = clickOp;
    }

    /**
     * @return открыт ли контейнер, а не свой инвентарь
     */
    public boolean open() {
        return numberOp.run(0) != 0;
    }

    /**
     * @return номер окна, которым его называет сервер; 0 у закрытого
     */
    public int id() {
        return numberOp.run(1);
    }

    /**
     * @return сколько слотов у самого контейнера, без инвентаря игрока
     */
    public int size() {
        return Math.max(0, numberOp.run(2));
    }

    /**
     * @return заголовок окна без оформления; пустая строка у закрытого
     */
    public String title() {
        return text(0);
    }

    /**
     * Тип меню, например {@code "minecraft:generic_9x6"}.
     *
     * @return строковый id типа; пустая строка, если тип неизвестен
     */
    public String type() {
        return text(1);
    }

    /**
     * Предмет в слоте контейнера.
     *
     * @param index номер слота {@code 0..size()-1}
     * @return снимок; у пустого и неверного слота — пустой предмет
     */
    public Item slot(int index) {
        return index < 0 || index >= size() ? new Item() : item(index);
    }

    /**
     * Ищет первый слот контейнера с этим предметом.
     *
     * @param itemId id предмета, пространство имён необязательно
     * @return номер слота или -1
     */
    public int find(String itemId) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (item(i).is(itemId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Считает предметы в контейнере.
     *
     * @param itemId id предмета, пространство имён необязательно
     * @return суммарный размер стопок
     */
    public int count(String itemId) {
        int size = size();
        int total = 0;
        for (int i = 0; i < size; i++) {
            Item found = item(i);
            if (found.is(itemId)) {
                total += found.count();
            }
        }
        return total;
    }

    /**
     * Обычный клик по слоту. Уходит в очередь кликов клиента, а не мгновенно.
     *
     * @param slot   слот окна: {@code 0..size()-1} — контейнер, дальше инвентарь игрока
     * @param button 0 — левая кнопка, 1 — правая
     * @return встал ли клик в очередь
     */
    public boolean click(int slot, int button) {
        return clickOp.run(CLICK, slot, button);
    }

    /**
     * Шифт-клик: перекидывает стопку между контейнером и инвентарём.
     *
     * @param slot слот окна
     * @return встал ли клик в очередь
     */
    public boolean shiftClick(int slot) {
        return clickOp.run(QUICK_MOVE, slot, 0);
    }

    /**
     * Меняет местами предмет слота и предмет хотбара.
     *
     * @param slot   слот окна
     * @param hotbar слот хотбара {@code 0..8} или {@link #OFFHAND}
     * @return встал ли клик в очередь
     */
    public boolean swap(int slot, int hotbar) {
        return clickOp.run(SWAP, slot, hotbar);
    }

    /**
     * Выбрасывает всю стопку из слота.
     *
     * @param slot слот окна
     * @return встал ли клик в очередь
     */
    public boolean drop(int slot) {
        return clickOp.run(DROP, slot, 0);
    }

    /**
     * Закрывает контейнер после того, как отработают уже поставленные клики.
     *
     * @return встало ли закрытие в очередь
     */
    public boolean close() {
        return clickOp.run(CLOSE, 0, 0);
    }

    private Item item(int index) {
        Item found = slotOp.run(index);
        return found == null ? new Item() : found;
    }

    private String text(int query) {
        String value = textOp.run(query);
        return value == null ? "" : value;
    }
}
