package hex.script.api;

/**
 * Снимок предмета на момент запроса. Ванильный {@code ItemStack} наружу не
 * отдаётся, поэтому это копия полей, а не живая ссылка.
 *
 * <pre>{@code
 * Item held = player().held();
 * if (held.is("totem_of_undying")) {
 *     log().info("totem in hand, durability " + held.durability());
 * }
 *
 * Item helmet = player().inventory().armor(Entity.HEAD);
 * if (helmet.enchant("protection") >= 4 && helmet.durability() > 0.2f) {
 *     log().info("helmet fit");
 * }
 * }</pre>
 */
public final class Item {

    private long handle;

    private static final String[] NO_STRINGS = new String[0];
    private static final int[] NO_LEVELS = new int[0];

    private String id = "minecraft:air";
    private String name = "";
    private int count;
    private int damage;
    private int maxDamage;
    private int maxCount;
    private float armor;
    private float toughness;
    private boolean unbreakable;
    private boolean stackable;
    private boolean food;
    private boolean tool;
    private boolean armorPiece;
    private boolean renamed;
    private String[] enchantIds = NO_STRINGS;
    private int[] enchantLevels = NO_LEVELS;
    private String[] lore = NO_STRINGS;

    /**
     * Заполняет снимок. Вызывает клиент.
     *
     * @param id        строковый id предмета
     * @param name      отображаемое имя
     * @param count     размер стопки
     * @param damage    накопленный урон
     * @param maxDamage запас прочности, 0 у неломающихся
     */
    public void set(String id, String name, int count, int damage, int maxDamage) {
        this.id = id == null ? "minecraft:air" : id;
        this.name = name == null ? "" : name;
        this.count = count;
        this.damage = damage;
        this.maxDamage = maxDamage;
    }

    /**
     * Привязывает снимок к живому предмету в мире. Вызывает клиент: по этой
     * метке отрисовка достаёт настоящий предмет со всеми компонентами —
     * скином головы, цветом зелья, содержимым шалкера, чарами. Метка живёт
     * один тик, дальше отрисовка откатывается на предмет по одному лишь id.
     *
     * @param handle метка живого предмета
     */
    public void handle(long handle) {
        this.handle = handle;
    }

    /**
     * @return метка живого предмета; {@code 0}, если снимка в мире нет
     */
    public long handle() {
        return handle;
    }

    /**
     * Заполняет свойства предмета. Вызывает клиент.
     *
     * @param maxCount    максимум в стопке
     * @param armor       броня от атрибутов предмета
     * @param toughness   стойкость брони от атрибутов предмета
     * @param unbreakable помечен ли предмет неломающимся
     * @param stackable   складывается ли в стопку
     * @param food        еда ли
     * @param tool        инструмент или меч ли
     * @param armorPiece  надевается ли в слот брони
     * @param renamed     переименован ли предмет
     */
    public void set(int maxCount, float armor, float toughness, boolean unbreakable,
                    boolean stackable, boolean food, boolean tool, boolean armorPiece, boolean renamed) {
        this.maxCount = maxCount;
        this.armor = armor;
        this.toughness = toughness;
        this.unbreakable = unbreakable;
        this.stackable = stackable;
        this.food = food;
        this.tool = tool;
        this.armorPiece = armorPiece;
        this.renamed = renamed;
    }

    /**
     * Заполняет зачарования и лор. Вызывает клиент.
     *
     * @param enchantIds    id зачарований
     * @param enchantLevels уровни в том же порядке
     * @param lore          строки лора
     */
    public void set(String[] enchantIds, int[] enchantLevels, String[] lore) {
        this.enchantIds = enchantIds == null ? NO_STRINGS : enchantIds;
        this.enchantLevels = enchantLevels == null ? NO_LEVELS : enchantLevels;
        this.lore = lore == null ? NO_STRINGS : lore;
    }

    /**
     * @return пуст ли слот
     */
    public boolean empty() {
        return count <= 0 || id.equals("minecraft:air");
    }

    /**
     * @return строковый id, например {@code "minecraft:diamond_sword"}
     */
    public String id() {
        return id;
    }

    /**
     * @return id без пространства имён, например {@code "diamond_sword"}
     */
    public String key() {
        return path(id);
    }

    /**
     * @return отображаемое имя без оформления
     */
    public String name() {
        return name;
    }

    /**
     * @return размер стопки
     */
    public int count() {
        return count;
    }

    /**
     * @return накопленный урон
     */
    public int damage() {
        return damage;
    }

    /**
     * @return запас прочности, 0 у неломающихся
     */
    public int maxDamage() {
        return maxDamage;
    }

    /**
     * @return остаток прочности от 0 до 1; 1 у неломающихся и пустых
     */
    public float durability() {
        return maxDamage <= 0 ? 1f : (float) (maxDamage - damage) / maxDamage;
    }

    /**
     * @return сколько таких предметов влезает в один слот; 0 у пустого
     */
    public int maxCount() {
        return maxCount;
    }

    /**
     * @return складывается ли предмет в стопку
     */
    public boolean stackable() {
        return stackable;
    }

    /**
     * @return помечен ли предмет неломающимся; прочность у такого не тратится
     */
    public boolean unbreakable() {
        return unbreakable;
    }

    /**
     * Съедобность по компоненту {@code food}. Зелья и молоко сюда не попадают —
     * они выпиваются, а не съедаются.
     *
     * @return еда ли
     */
    public boolean food() {
        return food;
    }

    /**
     * Инструмент по компоненту {@code tool}: кирки, топоры, лопаты, мотыги,
     * ножницы и мечи.
     *
     * @return инструмент ли
     */
    public boolean tool() {
        return tool;
    }

    /**
     * @return надевается ли предмет в слот брони: шлем, нагрудник, поножи или ботинки
     */
    public boolean armorPiece() {
        return armorPiece;
    }

    /**
     * Броня из атрибутов предмета — то же значение, по которому клиент выбирает
     * лучший комплект. У не-брони 0.
     *
     * @return единицы брони
     */
    public float armor() {
        return armor;
    }

    /**
     * Стойкость брони из атрибутов предмета. У не-брони 0.
     *
     * @return единицы стойкости
     */
    public float toughness() {
        return toughness;
    }

    /**
     * @return отличается ли имя от стандартного, то есть переименован ли предмет
     */
    public boolean renamed() {
        return renamed;
    }

    /**
     * @return есть ли на предмете хотя бы одно зачарование
     */
    public boolean enchanted() {
        return enchantIds.length > 0;
    }

    /**
     * Уровень зачарования. Пространство имён можно не писать, как в {@link #is(String)}.
     * У зачарованной книги читаются её хранимые зачарования.
     *
     * @param value {@code "sharpness"} или {@code "minecraft:sharpness"}
     * @return уровень; 0, если зачарования нет
     */
    public int enchant(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        int limit = Math.min(enchantIds.length, enchantLevels.length);
        for (int index = 0; index < limit; index++) {
            String found = enchantIds[index];
            if (found.equals(value) || path(found).equals(value)) {
                return enchantLevels[index];
            }
        }
        return 0;
    }

    /**
     * Id всех зачарований предмета, например {@code "minecraft:sharpness"}.
     * Уровень каждого берётся через {@link #enchant(String)}.
     *
     * @return копия списка; пустой массив, если предмет не зачарован
     */
    public String[] enchants() {
        return enchantIds.length == 0 ? NO_STRINGS : enchantIds.clone();
    }

    /**
     * Строки лора без оформления, в порядке показа в подсказке.
     *
     * @return копия списка; пустой массив, если лора нет
     */
    public String[] lore() {
        return lore.length == 0 ? NO_STRINGS : lore.clone();
    }

    /**
     * Сравнивает id. Пространство имён можно не писать.
     *
     * @param value {@code "totem_of_undying"} или {@code "minecraft:totem_of_undying"}
     * @return совпало ли
     */
    public boolean is(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return id.equals(value) || key().equals(value);
    }

    private static String path(String value) {
        int index = value.indexOf(':');
        return index < 0 ? value : value.substring(index + 1);
    }

    @Override
    public String toString() {
        return empty() ? "empty" : count + "x " + id;
    }
}
