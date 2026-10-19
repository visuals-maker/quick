package hex.script.api;

/**
 * Пара углов взгляда в градусах.
 */
public final class Angles {

    private final float yaw;
    private final float pitch;

    public Angles(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * @return рыскание
     */
    public float yaw() {
        return yaw;
    }

    /**
     * @return тангаж
     */
    public float pitch() {
        return pitch;
    }

    @Override
    public String toString() {
        return yaw + " / " + pitch;
    }
}
