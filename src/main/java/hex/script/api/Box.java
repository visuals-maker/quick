package hex.script.api;

/**
 * Коробка блоков в мире: обе границы включительно.
 */
public final class Box {

    private static final double HALF = 0.5;

    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;

    public Box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public int minX() {
        return minX;
    }

    public int minY() {
        return minY;
    }

    public int minZ() {
        return minZ;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public int maxZ() {
        return maxZ;
    }

    /** @return середина по X с учётом того, что граница включительная */
    public double centerX() {
        return (minX + maxX + 1) * HALF;
    }

    /** @return середина по Z с учётом того, что граница включительная */
    public double centerZ() {
        return (minZ + maxZ + 1) * HALF;
    }

    @Override
    public String toString() {
        return "[" + minX + " " + minY + " " + minZ + " .. " + maxX + " " + maxY + " " + maxZ + "]";
    }
}
