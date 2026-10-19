package hex.script.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Оформленный текст: цвет, стили и градиент кусками. Одну и ту же сборку
 * можно и напечатать в чат, и нарисовать на экране.
 *
 * <pre>{@code
 * Text line = Text.of("purpose: ").color(0xFF8A8A8A)
 *         .append(name).gradient(0xFF41C4C8, 0xFF8A5CF6).bold()
 *         .append(" 12 hp").color(0xFF57D977);
 *
 * chat().print(line);
 * render.drawText("sf_rounded_semibold", line, 10f, 10f, 7f, 0f);
 * }</pre>
 *
 * Стили и цвет ложатся на последний добавленный кусок, поэтому пиши их сразу
 * после {@link #append(Object)}.
 */
public final class Text {

    public static final int BOLD = 1;
    public static final int ITALIC = 2;
    public static final int UNDERLINE = 4;
    public static final int STRIKE = 8;
    public static final int MAGIC = 16;

    private static final int INHERIT = 0;

    private static final class Part {
        private String value;
        private int color = INHERIT;
        private int gradient = INHERIT;
        private int power = 20;
        private int speed = 5;
        private int flags;

        private Part(String value) {
            this.value = value;
        }
    }

    private final List<Part> parts = new ArrayList<>();

    private Object cache;
    private boolean animated;

    private Text(String value) {
        parts.add(new Part(value));
    }

    private Text() {
    }

    /**
     * Начинает сборку с куска текста.
     *
     * @param value текст, {@code null} превращается в {@code "null"}
     * @return новая сборка
     */
    public static Text of(Object value) {
        return new Text(String.valueOf(value));
    }

    /**
     * @return пустая сборка
     */
    public static Text empty() {
        return new Text();
    }

    /**
     * Добавляет кусок в конец. Цвет и стили после этого лягут на него.
     *
     * @param value текст
     * @return эта же сборка
     */
    public Text append(Object value) {
        parts.add(new Part(String.valueOf(value)));
        return dirty();
    }

    /**
     * Добавляет куски другой сборки, сохраняя их оформление.
     *
     * @param other другая сборка
     * @return эта же сборка
     */
    public Text append(Text other) {
        if (other != null) {
            parts.addAll(other.parts);
        }
        return dirty();
    }

    /**
     * Красит последний кусок.
     *
     * @param argb цвет {@code 0xAARRGGBB}
     * @return эта же сборка
     */
    public Text color(int argb) {
        Part part = last();
        if (part != null) {
            part.color = argb;
            part.gradient = INHERIT;
        }
        return dirty();
    }

    /**
     * Заливает последний кусок бегущим градиентом.
     *
     * @param from цвет начала {@code 0xAARRGGBB}
     * @param to   цвет конца {@code 0xAARRGGBB}
     * @return эта же сборка
     */
    public Text gradient(int from, int to) {
        return gradient(from, to, 20, 5);
    }

    /**
     * Заливает последний кусок бегущим градиентом с настройкой хода.
     *
     * @param from  цвет начала {@code 0xAARRGGBB}
     * @param to    цвет конца {@code 0xAARRGGBB}
     * @param power длина волны в символах, больше — плавнее
     * @param speed скорость бега, 0 — стоячий градиент
     * @return эта же сборка
     */
    public Text gradient(int from, int to, int power, int speed) {
        Part part = last();
        if (part != null) {
            part.color = from;
            part.gradient = to;
            part.power = Math.max(1, power);
            part.speed = Math.max(0, speed);
            animated = true;
        }
        return dirty();
    }

    /**
     * @return эта же сборка с жирным последним куском
     */
    public Text bold() {
        return style(BOLD);
    }

    /**
     * @return эта же сборка с наклонным последним куском
     */
    public Text italic() {
        return style(ITALIC);
    }

    /**
     * @return эта же сборка с подчёркнутым последним куском
     */
    public Text underline() {
        return style(UNDERLINE);
    }

    /**
     * @return эта же сборка с зачёркнутым последним куском
     */
    public Text strike() {
        return style(STRIKE);
    }

    /**
     * @return эта же сборка с шифрующимся последним куском
     */
    public Text magic() {
        return style(MAGIC);
    }

    /**
     * Меняет текст последнего куска, не трогая оформление. Так обновляют
     * счётчик в собранной один раз строке.
     *
     * @param value новый текст
     * @return эта же сборка
     */
    public Text value(Object value) {
        Part part = last();
        if (part != null) {
            part.value = String.valueOf(value);
        }
        return dirty();
    }

    /**
     * @return текст без оформления
     */
    public String string() {
        StringBuilder builder = new StringBuilder();
        for (Part part : parts) {
            builder.append(part.value);
        }
        return builder.toString();
    }

    /**
     * @return сколько кусков в сборке
     */
    public int parts() {
        return parts.size();
    }

    /**
     * @return есть ли бегущий градиент, из-за которого строку надо собирать каждый кадр
     */
    public boolean animated() {
        return animated;
    }

    public String partText(int index) {
        return parts.get(index).value;
    }

    public int partColor(int index) {
        return parts.get(index).color;
    }

    public int partGradient(int index) {
        return parts.get(index).gradient;
    }

    public int partPower(int index) {
        return parts.get(index).power;
    }

    public int partSpeed(int index) {
        return parts.get(index).speed;
    }

    public int partFlags(int index) {
        return parts.get(index).flags;
    }

    public Object cached() {
        return cache;
    }

    public void cached(Object value) {
        this.cache = value;
    }

    @Override
    public String toString() {
        return string();
    }

    private Text style(int flag) {
        Part part = last();
        if (part != null) {
            part.flags |= flag;
        }
        return dirty();
    }

    private Part last() {
        return parts.isEmpty() ? null : parts.get(parts.size() - 1);
    }

    private Text dirty() {
        cache = null;
        return this;
    }
}
