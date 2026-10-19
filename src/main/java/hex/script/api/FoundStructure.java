package hex.script.api;

import java.util.List;

/**
 * Структура, которую генератор ставит в чанке, со всеми частями.
 */
public final class FoundStructure {

    private final String structure;
    private final int chunkX;
    private final int chunkZ;
    private final Box box;
    private final List<StructurePart> parts;

    public FoundStructure(String structure, int chunkX, int chunkZ, Box box, List<StructurePart> parts) {
        this.structure = structure;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.box = box;
        this.parts = List.copyOf(parts);
    }

    /**
     * @return id структуры, например {@code minecraft:village_plains} или {@code minecraft:bastion_remnant}
     */
    public String structure() {
        return structure;
    }

    /** @return чанк старта */
    public int chunkX() {
        return chunkX;
    }

    /** @return чанк старта */
    public int chunkZ() {
        return chunkZ;
    }

    /** @return коробка всей структуры */
    public Box box() {
        return box;
    }

    public List<StructurePart> parts() {
        return parts;
    }

    /**
     * @param type тип части, например {@code minecraft:shpr}
     * @return первая часть этого типа или {@code null}
     */
    public StructurePart partOfType(String type) {
        for (StructurePart part : parts) {
            if (part.type().equals(type)) {
                return part;
            }
        }
        return null;
    }

    /**
     * @param template имя шаблона, например {@code ship}
     * @return есть ли часть из этого шаблона
     */
    public boolean hasTemplate(String template) {
        for (StructurePart part : parts) {
            if (part.template().equals(template)) {
                return true;
            }
        }
        return false;
    }
}
