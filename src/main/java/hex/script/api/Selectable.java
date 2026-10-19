package hex.script.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Выбор одного варианта из списка.
 */
public final class Selectable extends Setting<Selectable> {

    private final List<String> options;
    private String value;
    private Consumer<String> listener;

    Selectable(CheckBox parent, String name, String selected, String... options) {
        super(parent, name);
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("at least one option is needed");
        }
        this.options = List.copyOf(Arrays.asList(options));
        this.value = this.options.contains(selected) ? selected : this.options.get(0);
    }

    /**
     * @return выбранный вариант
     */
    public String value() {
        return value;
    }

    /**
     * @param next имя варианта; незнакомое имя игнорируется
     * @return эта же настройка
     */
    public Selectable value(String next) {
        if (next != null && options.contains(next) && !next.equals(value)) {
            value = next;
            fire();
        }
        return this;
    }

    /**
     * @param option имя варианта
     * @return выбран ли он сейчас
     */
    public boolean is(String option) {
        return value.equals(option);
    }

    /**
     * @return все варианты в порядке объявления
     */
    public List<String> options() {
        return Collections.unmodifiableList(options);
    }

    /**
     * @param consumer вызывается на каждое изменение
     * @return эта же настройка
     */
    public Selectable onChange(Consumer<String> consumer) {
        this.listener = consumer;
        return this;
    }

    @Override
    public String write() {
        return value;
    }

    @Override
    public void read(String raw) {
        value(raw);
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
