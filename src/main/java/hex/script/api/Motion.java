package hex.script.api;

/**
 * Один тик предсказанного движения игрока.
 */
public final class Motion {

    private final double x;
    private final double y;
    private final double z;
    private final double velocityX;
    private final double velocityY;
    private final double velocityZ;
    private final boolean onGround;
    private final boolean inWater;
    private final boolean sprinting;

    public Motion(double x, double y, double z,
                  double velocityX, double velocityY, double velocityZ,
                  boolean onGround, boolean inWater, boolean sprinting) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.onGround = onGround;
        this.inWater = inWater;
        this.sprinting = sprinting;
    }

    /**
     * @return позиция ног
     */
    public Point position() {
        return new Point(x, y, z);
    }

    /**
     * @return скорость в блоках за тик
     */
    public Point velocity() {
        return new Point(velocityX, velocityY, velocityZ);
    }

    /**
     * @return стоит ли игрок на земле в этом тике
     */
    public boolean onGround() {
        return onGround;
    }

    /**
     * @return касается ли воды
     */
    public boolean inWater() {
        return inWater;
    }

    /**
     * @return бежит ли
     */
    public boolean sprinting() {
        return sprinting;
    }
}
