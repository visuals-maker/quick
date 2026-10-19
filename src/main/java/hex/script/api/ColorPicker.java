package hex.script.api;

import java.util.function.Consumer;

/**
 * Цвет в формате {@code 0xAARRGGBB}.
 */
public final class ColorPicker extends Setting<ColorPicker> {

    private int value;
    private Consumer<Integer> listener;

    ColorPicker(CheckBox parent, String name, int argb) {
        super(parent, name);
        this.value = argb;
    }

    /**
     * @return цвет как {@code 0xAARRGGBB}
     */
    public int value() {
        return value;
    }

    /**
     * @param argb новый цвет
     * @return эта же настройка
     */
    public ColorPicker value(int argb) {
        if (argb != value) {
            value = argb;
            fire();
        }
        return this;
    }

    /**
     * @param consumer вызывается на каждое изменение
     * @return эта же настройка
     */
    public ColorPicker onChange(Consumer<Integer> consumer) {
        this.listener = consumer;
        return this;
    }

    @Override
    public String write() {
        return Integer.toHexString(value);
    }

    @Override
    public void read(String raw) {
        if (raw == null) {
            return;
        }
        try {
            value((int) Long.parseLong(raw.trim(), 16));
        } catch (NumberFormatException ignored) {
        }
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
