package hex.script.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Выбор любого числа вариантов из списка.
 */
public final class Combo extends Setting<Combo> {

    private final List<String> options;
    private final Set<String> selected = new LinkedHashSet<>();
    private Consumer<List<String>> listener;

    Combo(CheckBox parent, String name, String[] options, String... preselected) {
        super(parent, name);
        if (options == null || options.length == 0) {
            throw new IllegalArgumentException("at least one option is needed");
        }
        this.options = List.copyOf(Arrays.asList(options));
        if (preselected != null) {
            for (String option : preselected) {
                if (this.options.contains(option)) {
                    selected.add(option);
                }
            }
        }
    }

    /**
     * @param option имя варианта
     * @return выбран ли он
     */
    public boolean has(String option) {
        return selected.contains(option);
    }

    /**
     * @param option имя варианта
     * @param state  выбрать или снять
     * @return эта же настройка
     */
    public Combo set(String option, boolean state) {
        if (!options.contains(option)) {
            return this;
        }
        boolean changed = state ? selected.add(option) : selected.remove(option);
        if (changed) {
            fire();
        }
        return this;
    }

    /**
     * @return выбранные варианты в порядке объявления
     */
    public List<String> value() {
        List<String> result = new ArrayList<>();
        for (String option : options) {
            if (selected.contains(option)) {
                result.add(option);
            }
        }
        return Collections.unmodifiableList(result);
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
    public Combo onChange(Consumer<List<String>> consumer) {
        this.listener = consumer;
        return this;
    }

    @Override
    public String write() {
        return String.join(",", value());
    }

    @Override
    public void read(String raw) {
        if (raw == null) {
            return;
        }
        selected.clear();
        for (String part : raw.split(",")) {
            String option = part.trim();
            if (options.contains(option)) {
                selected.add(option);
            }
        }
        fire();
    }

    private void fire() {
        if (listener == null) {
            return;
        }
        try {
            listener.accept(value());
        } catch (Throwable ignored) {
        }
    }
}
