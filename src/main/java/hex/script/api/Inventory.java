package hex.script.api;

/**
 * Инвентарь игрока. Слоты нумеруются как в самой игре: 0..8 — хотбар,
 * 9..35 — основной инвентарь, 36..39 — броня, 40 — левая рука.
 *
 * <pre>{@code
 * int slot = inventory().find("ender_pearl");
 * if (slot >= 0 && slot <= 8) {
 *     slots().using(slot, () -> interaction().useItem(false));
 * }
 * }</pre>
 */
public final class Inventory {

    public static final int HOTBAR_END = 9;
    public static final int MAIN_END = 36;
    public static final int ARMOR_END = 40;
    public static final int OFFHAND = 40;
    public static final int SIZE = 41;

    /** Снимок слота. */
    @FunctionalInterface
    public interface SlotOp {
        Item run(int slot);
    }

    /** Число: 0 — выбранный слот, 1 — открыт ли контейнер, 2 — его номер. */
    @FunctionalInterface
    public interface NumberOp {
        int run(int query);
    }

    /** Остаток задержки предмета в тиках. */
    @FunctionalInterface
    public interface CooldownOp {
        float run(String id);
    }

    /**
     * Поиск по слотам без снимков: 0 — суммарный размер стопок, 1 — первый
     * слот с предметом, 2 — первый слот хотбара, 3 — число пустых слотов.
     */
    @FunctionalInterface
    public interface ScanOp {
        int run(int query, String id);
    }

    private final SlotOp slotOp;
    private final NumberOp numberOp;
    private final CooldownOp cooldownOp;
    private final ScanOp scanOp;

    public Inventory(SlotOp slotOp, NumberOp numberOp, CooldownOp cooldownOp, ScanOp scanOp) {
        this.slotOp = slotOp;
        this.numberOp = numberOp;
        this.cooldownOp = cooldownOp;
        this.scanOp = scanOp;
    }

    /**
     * Остаток задержки предмета — того самого отката, из-за которого
     * иконка в хотбаре залита белым.
     *
     * @param id id предмета: {@code "ender_pearl"} или {@code "minecraft:ender_pearl"}
     * @return остаток в тиках; {@code 0}, если задержки нет
     */
    public float cooldown(String id) {
        return id == null || id.isEmpty() ? 0f : Math.max(0f, cooldownOp.run(id));
    }

    /**
     * @param item снимок предмета
     * @return остаток задержки в тиках; {@code 0} у пустого предмета и без задержки
     */
    public float cooldown(Item item) {
        return item == null || item.empty() ? 0f : cooldown(item.id());
    }

    /**
     * Предмет в слоте.
     *
     * @param slot номер слота 0..40
     * @return снимок; у пустого и неверного слота — пустой предмет
     */
    public Item slot(int slot) {
        Item item = slot < 0 || slot >= SIZE ? null : slotOp.run(slot);
        return item == null ? new Item() : item;
    }

    /**
     * @return выбранный слот хотбара 0..8
     */
    public int selected() {
        return numberOp.run(0);
    }

    /**
     * @return предмет в основной руке
     */
    public Item held() {
        return slot(selected());
    }

    /**
     * @return предмет в левой руке
     */
    public Item offhand() {
        return slot(OFFHAND);
    }

    /**
     * Надетая броня.
     *
     * @param part {@link Entity#HEAD}, {@link Entity#CHEST}, {@link Entity#LEGS}
     *             или {@link Entity#FEET}
     * @return снимок предмета
     */
    public Item armor(int part) {
        return part < 0 || part > 3 ? new Item() : slot(MAIN_END + (3 - part));
    }

    /**
     * @return открыт ли контейнер, а не свой инвентарь
     */
    public boolean container() {
        return numberOp.run(1) != 0;
    }

    /**
     * @return номер открытого окна; 0 у своего инвентаря
     */
    public int containerId() {
        return numberOp.run(2);
    }

    /**
     * Ищет первый слот с этим предметом.
     *
     * @param id id предмета, пространство имён необязательно
     * @return номер слота или -1
     */
    public int find(String id) {
        return scanOp.run(1, id);
    }

    /**
     * Ищет предмет только в хотбаре.
     *
     * @param id id предмета
     * @return слот 0..8 или -1
     */
    public int findHotbar(String id) {
        return scanOp.run(2, id);
    }

    /**
     * Считает предметы во всём инвентаре.
     *
     * @param id id предмета
     * @return суммарный размер стопок
     */
    public int count(String id) {
        return scanOp.run(0, id);
    }

    /**
     * @return сколько слотов основного инвентаря пусты
     */
    public int free() {
        return scanOp.run(3, "");
    }
}
