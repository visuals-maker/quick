package hex.script.api;

import java.util.Collections;
import java.util.List;

/**
 * Вызов команды: путь узла, аргументы после него и ответ в чат.
 */
public final class CommandContext {

    /** Ответ в чат. */
    @FunctionalInterface
    public interface ReplyOp {
        void run(String label, String message);
    }

    private final String label;
    private final List<String> args;
    private final String usage;
    private final ReplyOp replyOp;

    public CommandContext(String label, List<String> args, String usage, ReplyOp replyOp) {
        this.label = label;
        this.args = args;
        this.usage = usage;
        this.replyOp = replyOp;
    }

    /**
     * @return полный путь узла, например {@code home go}
     */
    public String label() {
        return label;
    }

    /**
     * @return аргументы после пути узла
     */
    public List<String> args() {
        return Collections.unmodifiableList(args);
    }

    /**
     * @return сколько аргументов пришло
     */
    public int argCount() {
        return args.size();
    }

    /**
     * @param index номер аргумента с нуля
     * @return аргумент
     * @throws IllegalArgumentException если аргумента нет
     */
    public String arg(int index) {
        if (index < 0 || index >= args.size()) {
            throw new IllegalArgumentException("you need an argument " + (index + 1)
                    + (usage.isEmpty() ? "" : ": " + usage));
        }
        return args.get(index);
    }

    /**
     * @param index    номер аргумента с нуля
     * @param fallback что вернуть, если аргумента нет
     * @return аргумент или fallback
     */
    public String argOr(int index, String fallback) {
        return index < 0 || index >= args.size() ? fallback : args.get(index);
    }

    /**
     * @param index номер аргумента с нуля
     * @return аргумент как целое
     * @throws IllegalArgumentException если аргумента нет или это не целое
     */
    public int intArg(int index) {
        try {
            return Integer.parseInt(arg(index));
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("argument " + (index + 1) + " must be an integer");
        }
    }

    /**
     * @param index номер аргумента с нуля
     * @return аргумент как дробное
     * @throws IllegalArgumentException если аргумента нет или это не число
     */
    public double doubleArg(int index) {
        try {
            return Double.parseDouble(arg(index));
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("argument " + (index + 1) + " must be a number");
        }
    }

    /**
     * Принимает {@code true/on/yes/1} и {@code false/off/no/0} без учёта регистра.
     *
     * @param index номер аргумента с нуля
     * @return аргумент как флаг
     * @throws IllegalArgumentException если значение не распознано
     */
    public boolean booleanArg(int index) {
        String value = arg(index).toLowerCase();
        return switch (value) {
            case "true", "on", "yes", "1" -> true;
            case "false", "off", "no", "0" -> false;
            default -> throw new IllegalArgumentException("argument " + (index + 1) + "must be yes or no");
        };
    }

    /**
     * @return все аргументы одной строкой
     */
    public String rest() {
        return rest(0);
    }

    /**
     * @param fromIndex с какого аргумента собирать
     * @return аргументы с этого номера через пробел
     */
    public String rest(int fromIndex) {
        int from = Math.max(0, fromIndex);
        if (from >= args.size()) {
            return "";
        }
        return String.join(" ", args.subList(from, args.size()));
    }

    /**
     * Пишет ответ в твой чат с пометкой пути команды.
     *
     * @param message текст
     */
    public void reply(String message) {
        replyOp.run(label, message);
    }
}
