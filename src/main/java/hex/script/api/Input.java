package hex.script.api;

import java.util.function.Consumer;

/**
 * Текстовое поле.
 */
public final class Input extends Setting<Input> {

    private final String placeholder;
    private String value;
    private Consumer<String> listener;

    Input(CheckBox parent, String name, String value, String placeholder) {
        super(parent, name);
        this.value = value == null ? "" : value;
        this.placeholder = placeholder == null ? "" : placeholder;
    }

    /**
     * @return текущий текст
     */
    public String value() {
        return value;
    }

    /**
     * @param next новый текст, {@code null} превращается в пустую строку
     * @return эта же настройка
     */
    public Input value(String next) {
        String text = next == null ? "" : next;
        if (!text.equals(value)) {
            value = text;
            fire();
        }
        return this;
    }

    /**
     * @return подсказка, видимая пока поле пустое
     */
    public String placeholder() {
        return placeholder;
    }

    /**
     * @return пустое ли поле
     */
    public boolean empty() {
        return value.isEmpty();
    }

    /**
     * @param consumer вызывается на каждое изменение
     * @return эта же настройка
     */
    public Input onChange(Consumer<String> consumer) {
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
