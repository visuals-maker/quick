package hex.script.api;

import java.util.function.Consumer;

/**
 * Числовой ползунок. Значение всегда зажато в {@code min..max}.
 */
public final class Slider extends Setting<Slider> {

    private final float min;
    private final float max;
    private final float step;

    private float value;
    private String postfix = "";
    private Consumer<Float> listener;

    Slider(CheckBox parent, String name, float value, float min, float max, float step) {
        super(parent, name);
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }
        if (step <= 0f) {
            throw new IllegalArgumentException("step must be greater than zero");
        }
        this.min = min;
        this.max = max;
        this.step = step;
        this.value = clamp(value);
    }

    /**
     * @return текущее значение
     */
    public float value() {
        return value;
    }

    /**
     * @return текущее значение, округлённое до целого
     */
    public int intValue() {
        return Math.round(value);
    }

    /**
     * @param next новое значение, будет зажато в границы
     * @return эта же настройка
     */
    public Slider value(float next) {
        float clamped = clamp(next);
        if (clamped != value) {
            value = clamped;
            fire();
        }
        return this;
    }

    /**
     * @return нижняя граница
     */
    public float min() {
        return min;
    }

    /**
     * @return верхняя граница
     */
    public float max() {
        return max;
    }

    /**
     * @return шаг перетаскивания
     */
    public float step() {
        return step;
    }

    /**
     * @return единица измерения после числа
     */
    public String postfix() {
        return postfix;
    }

    /**
     * @param unit единица измерения, например {@code "ms"}
     * @return эта же настройка
     */
    public Slider postfix(String unit) {
        this.postfix = unit == null ? "" : unit;
        return this;
    }

    /**
     * @param consumer вызывается на каждое изменение
     * @return эта же настройка
     */
    public Slider onChange(Consumer<Float> consumer) {
        this.listener = consumer;
        return this;
    }

    @Override
    public String write() {
        return String.valueOf(value);
    }

    @Override
    public void read(String raw) {
        if (raw == null) {
            return;
        }
        try {
            value(Float.parseFloat(raw));
        } catch (NumberFormatException ignored) {
        }
    }

    private float clamp(float raw) {
        return raw < min ? min : (raw > max ? max : raw);
    }

    private void fire() {
        if (listener == null) {
            return;
        }
        try {
            listener.accept(value);
        } catch (Throwable ignored) {
        }
    }
}
