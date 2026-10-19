package hex.script.api;

import java.util.function.Consumer;

/**
 * Галка. Единственная настройка, внутрь которой можно вкладывать другие.
 */
public final class CheckBox extends Setting<CheckBox> {

    private final boolean initial;
    private boolean value;
    private Consumer<Boolean> listener;

    CheckBox(CheckBox parent, String name, boolean value) {
        super(parent, name);
        this.initial = value;
        this.value = value;
    }

    /**
     * @return текущее состояние
     */
    public boolean value() {
        return value;
    }

    /**
     * @param next новое состояние
     * @return эта же настройка
     */
    public CheckBox value(boolean next) {
        if (this.value != next) {
            this.value = next;
            fire();
        }
        return this;
    }

    /**
     * @return эта же настройка с инвертированным состоянием
     */
    public CheckBox toggle() {
        return value(!value);
    }

    /**
     * @param consumer вызывается на каждое изменение
     * @return эта же настройка
     */
    public CheckBox onChange(Consumer<Boolean> consumer) {
        this.listener = consumer;
        return this;
    }

    @Override
    public String write() {
        return String.valueOf(value);
    }

    @Override
    public void read(String raw) {
        boolean next = raw == null ? initial : Boolean.parseBoolean(raw);
        if (next != value) {
            value = next;
            fire();
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
