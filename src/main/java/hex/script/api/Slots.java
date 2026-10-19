package hex.script.api;

/**
 * Слот в руке и подбор брони. {@link #select(int)} возвращает ручку, которая
 * помнит слот, выбранный до неё, поэтому руку можно занять на пару тиков и
 * не забыть вернуть.
 *
 * <pre>{@code
 * slots().using(2, () -> interaction().useItem(false));
 *
 * int helmet = slots().bestArmor(Slots.HEAD);
 * if (helmet >= 0) {
 *     slots().equipArmor(Slots.HEAD);
 * }
 * }</pre>
 */
public final class Slots {

    /** Выбор слота с ручкой: возвращает {@code {id, slot, original}} или {@code null}. */
    @FunctionalInterface
    public interface SelectOp {
        int[] run(Object owner, int hotbar);
    }

    /** Действие: 0 — текущий слот, 1 — выбрать молча, 2 — синхронизировать. */
    @FunctionalInterface
    public interface SlotOp {
        int run(int action, int slot);
    }

    /** Броня: подбор лучшего слота и надевание. */
    @FunctionalInterface
    public interface ArmorOp {
        int run(int part, boolean equip);
    }

    /** Надевание предмета из заданного слота инвентаря. */
    @FunctionalInterface
    public interface EquipOp {
        boolean run(int part, int slot);
    }

    public static final int HEAD = 0;
    public static final int CHEST = 1;
    public static final int LEGS = 2;
    public static final int FEET = 3;

    private final Object owner;
    private final SelectOp selectOp;
    private final SlotOp slotOp;
    private final ArmorOp armorOp;
    private final EquipOp equipOp;
    private final HeldSlot.CloseOp closeOp;

    public Slots(Object owner, SelectOp selectOp, SlotOp slotOp, ArmorOp armorOp, EquipOp equipOp, HeldSlot.CloseOp closeOp) {
        this.owner = owner;
        this.selectOp = selectOp;
        this.slotOp = slotOp;
        this.armorOp = armorOp;
        this.equipOp = equipOp;
        this.closeOp = closeOp;
    }

    /**
     * @return выбранный слот хотбара 0..8, или -1 без игрока
     */
    public int selected() {
        return slotOp.run(0, 0);
    }

    /**
     * Выбирает слот хотбара и синхронизирует его с сервером.
     *
     * @param hotbar слот хотбара 0..8
     * @return ручка возврата или {@code null}, если слот не тот или игрока нет
     */
    public HeldSlot select(int hotbar) {
        int[] result = selectOp.run(owner, hotbar);
        return result == null ? null : new HeldSlot(result[0], result[1], result[2], closeOp);
    }

    /**
     * Выбирает слот хотбара, ничего не запоминая для возврата.
     *
     * @param hotbar слот хотбара 0..8
     * @return получилось ли
     */
    public boolean selectSilently(int hotbar) {
        return slotOp.run(1, hotbar) >= 0;
    }

    /**
     * Заново отправляет серверу выбранный слот хотбара.
     *
     * @return ушёл ли пакет
     */
    public boolean sync() {
        return slotOp.run(2, 0) >= 0;
    }

    /**
     * Выбирает слот, выполняет тело и возвращает слот на следующем тике —
     * даже если тело упало.
     *
     * @param hotbar слот хотбара 0..8
     * @param body   что сделать с этим слотом в руке
     * @return получилось ли занять слот
     */
    public boolean using(int hotbar, Runnable body) {
        HeldSlot held = select(hotbar);
        if (held == null) {
            return false;
        }
        try {
            body.run();
        } finally {
            held.restoreWhenSafe();
        }
        return true;
    }

    /**
     * Ищет в инвентаре кусок брони лучше надетого. Считает защиту, прочность
     * брони, чары защиты и Unbreaking — тем же счётом, что и Auto Armor.
     *
     * @param part {@link #HEAD}, {@link #CHEST}, {@link #LEGS} или {@link #FEET}
     * @return слот инвентаря 0..35 или -1, если надетое не хуже
     */
    public int bestArmor(int part) {
        return armorOp.run(part, false);
    }

    /**
     * Надевает лучший кусок брони, если он нашёлся. Клики уходят через
     * очередь клиента, поэтому зовётся из тика и не спорит с другими модулями.
     *
     * @param part {@link #HEAD}, {@link #CHEST}, {@link #LEGS} или {@link #FEET}
     * @return пошла ли смена
     */
    public boolean equipArmor(int part) {
        return armorOp.run(part, true) >= 0;
    }

    /**
     * Надевает предмет из заданного слота инвентаря, даже если по счёту
     * {@link #bestArmor(int)} он хуже надетого: элитры вместо нагрудника,
     * запасные элитры вместо изношенных. Надетое уходит на место предмета.
     * Клики идут через очередь клиента, как у {@link #equipArmor(int)}, и
     * только пока не открыт контейнер.
     *
     * <pre>{@code
     * int spare = inventory().find("elytra");
     * if (spare >= 0) {
     *     slots().equip(Slots.CHEST, spare);
     * }
     * }</pre>
     *
     * @param part {@link #HEAD}, {@link #CHEST}, {@link #LEGS} или {@link #FEET}
     * @param slot слот инвентаря, как у {@link Inventory#slot(int)}: 0..8 хотбар, 9..35 основной
     * @return пошла ли смена; {@code false}, если предмет в эту часть не надевается
     */
    public boolean equip(int part, int slot) {
        return equipOp.run(part, slot);
    }
}
