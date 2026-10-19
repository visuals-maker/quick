package hex.script.api;

/**
 * Часть найденной структуры.
 */
public final class StructurePart {

    private final String type;
    private final String template;
    private final Box box;

    public StructurePart(String type, String template, Box box) {
        this.type = type;
        this.template = template;
        this.box = box;
    }

    /**
     * @return тип части из реестра игры, например {@code minecraft:shpr} у портальной комнаты крепости
     */
    public String type() {
        return type;
    }

    /**
     * @return имя шаблона у частей из шаблонов, например {@code ship} у корабля энд-сити; иначе пустая строка
     */
    public String template() {
        return template;
    }

    public Box box() {
        return box;
    }
}
