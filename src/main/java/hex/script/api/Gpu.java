package hex.script.api;

/**
 * Реестр своей геометрии. Меш собирается один раз и рисуется каждый кадр без
 * пересборки — это дешевле, чем гнать вершины заново.
 *
 * <pre>{@code
 * private final Mesh ring = gpu().mesh("ring")
 *         .triangle(new float[]{0f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f}, 0xFF41C4C8)
 *         .build();
 * }</pre>
 */
public final class Gpu {

    /** Начало сборки меша. */
    @FunctionalInterface
    public interface MeshOp {
        MeshBuilder run(String name);
    }

    /** Имена загруженных мешей. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run();
    }

    /** Удаление меша. */
    @FunctionalInterface
    public interface DropOp {
        boolean run(String name);
    }

    private final MeshOp meshOp;
    private final NamesOp namesOp;
    private final DropOp dropOp;

    public Gpu(MeshOp meshOp, NamesOp namesOp, DropOp dropOp) {
        this.meshOp = meshOp;
        this.namesOp = namesOp;
        this.dropOp = dropOp;
    }

    /**
     * @param name имя меша
     * @return сборщик; вызови у него {@code build()}
     */
    public MeshBuilder mesh(String name) {
        return meshOp.run(name);
    }

    /**
     * @return имена загруженных мешей
     */
    public String[] names() {
        return namesOp.run();
    }

    /**
     * @param name имя меша
     * @return был ли меш удалён
     */
    public boolean drop(String name) {
        return dropOp.run(name);
    }
}
