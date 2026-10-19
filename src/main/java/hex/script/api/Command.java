package hex.script.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Своя команда клиента с префиксом {@code .}. Живёт, пока включён скрипт:
 * выключенный скрипт не отвечает и пропадает из подсказок.
 *
 * <pre>{@code
 * command("home")
 *         .usage("<go|list>")
 *         .alias("h")
 *         .sub("go", go -> go
 *                 .completes(0, "base", "farm")
 *                 .runs(context -> context.reply("going to " + context.arg(0))))
 *         .sub("list", list -> list.runs(context -> context.reply("base, farm")))
 *         .register();
 * }</pre>
 *
 * Аргументы — это хвост после пути узла, разбитый по пробелам. Подсказки
 * задаются номером аргумента с нуля.
 */
public final class Command {

    /** Тело команды. */
    @FunctionalInterface
    public interface Handler {
        void run(CommandContext context);
    }

    /** Поставщик подсказок для аргумента. */
    @FunctionalInterface
    public interface Completer {
        List<String> run();
    }

    /** Регистрация готового дерева команд в клиенте. */
    @FunctionalInterface
    public interface RegisterOp {
        void run(Command command);
    }

    private final String name;
    private final RegisterOp registerOp;
    private final List<String> aliases = new ArrayList<>();
    private final Map<String, Command> subs = new LinkedHashMap<>();
    private final Map<Integer, Completer> completions = new LinkedHashMap<>();

    private String usage = "";
    private Handler handler;

    Command(String name, RegisterOp registerOp) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("team has an empty name");
        }
        String clean = name.trim().toLowerCase();
        if (clean.startsWith(".")) {
            clean = clean.substring(1);
        }
        if (clean.isEmpty() || clean.contains(" ")) {
            throw new IllegalArgumentException("team name must be one word");
        }
        this.name = clean;
        this.registerOp = registerOp;
    }

    /**
     * @param text подсказка, дописываемая к ошибке о нехватке аргумента
     * @return эта же команда
     */
    public Command usage(String text) {
        this.usage = text == null ? "" : text;
        return this;
    }

    /**
     * @param alias дополнительное имя того же дерева
     * @return эта же команда
     */
    public Command alias(String alias) {
        if (alias == null || alias.trim().isEmpty() || alias.trim().contains(" ")) {
            throw new IllegalArgumentException("alias should be one word");
        }
        aliases.add(alias.trim().toLowerCase());
        return this;
    }

    /**
     * @param value тело команды; вызывается на клиентском потоке
     * @return эта же команда
     */
    public Command runs(Handler value) {
        this.handler = value;
        return this;
    }

    /**
     * @param name имя подкоманды
     * @param body описание подкоманды
     * @return эта же команда
     */
    public Command sub(String name, Consumer<Command> body) {
        if (body == null) {
            throw new IllegalArgumentException("subcommand has no body");
        }
        Command child = subs.computeIfAbsent(clean(name), key -> new Command(key, null));
        body.accept(child);
        return this;
    }

    /**
     * @param index    номер аргумента с нуля
     * @param supplier поставщик вариантов; вызывается, пока игрок печатает
     * @return эта же команда
     */
    public Command completes(int index, Completer supplier) {
        if (index < 0) {
            throw new IllegalArgumentException("argument number cannot be negative");
        }
        completions.put(index, supplier);
        return this;
    }

    /**
     * @param index   номер аргумента с нуля
     * @param options фиксированный список вариантов
     * @return эта же команда
     */
    public Command completes(int index, String... options) {
        List<String> fixed = options == null ? List.of() : List.copyOf(Arrays.asList(options));
        return completes(index, () -> fixed);
    }

    /**
     * Регистрирует команду и все её алиасы. До этого вызова команда не работает.
     */
    public void register() {
        if (registerOp == null) {
            throw new IllegalStateException("only root command can be registered");
        }
        if (handler == null && subs.isEmpty()) {
            throw new IllegalStateException("at the team " + name + " no runs, no sub");
        }
        registerOp.run(this);
    }

    /**
     * @return имя команды
     */
    public String name() {
        return name;
    }

    /**
     * @return алиасы команды
     */
    public List<String> aliases() {
        return Collections.unmodifiableList(aliases);
    }

    /**
     * @return подсказка по аргументам
     */
    public String usage() {
        return usage;
    }

    /**
     * @return тело команды или {@code null}
     */
    public Handler handler() {
        return handler;
    }

    /**
     * @return подкоманды в порядке объявления
     */
    public Map<String, Command> subs() {
        return Collections.unmodifiableMap(subs);
    }

    /**
     * @param index номер аргумента с нуля
     * @return варианты подсказок для этого аргумента
     */
    public List<String> complete(int index) {
        Completer completer = completions.get(index);
        if (completer == null) {
            return List.of();
        }
        try {
            List<String> options = completer.run();
            return options == null ? List.of() : options;
        } catch (Throwable ignored) {
            return List.of();
        }
    }

    private static String clean(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("subcommand has an empty name");
        }
        String result = value.trim().toLowerCase();
        int space = result.indexOf(' ');
        return space < 0 ? result : result.substring(0, space);
    }
}
