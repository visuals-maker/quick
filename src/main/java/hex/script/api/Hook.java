package hex.script.api;

/**
 * Один вызов хука: имя, аргументы от миксина и ответ обработчиков — отмена и
 * результат. Миксин получает его из {@link Hooks#fire(String, Object...)} и сам
 * решает, что сделать с ответом: отменить метод, подменить возвращаемое
 * значение или аргумент.
 *
 * <pre>{@code
 * // mixin/scriptmixin/vanillafly/LocalPlayerMixin.java
 * @Mixin(LocalPlayer.class)
 * public class LocalPlayerMixin {
 *
 *     @Inject(method = "isShiftKeyDown", at = @At("HEAD"), cancellable = true)
 *     private void vanillafly$shift(CallbackInfoReturnable<Boolean> info) {
 *         Hook hook = Hooks.fire("vanillafly:shift");
 *         if (hook.hasResult()) {
 *             info.setReturnValue((Boolean) hook.result());
 *         }
 *     }
 * }
 * }</pre>
 */
public final class Hook {

    private static final Object[] NO_ARGS = new Object[0];

    /** Ответ, когда обработчиков нет: пустой и не принимает ни отмену, ни результат. */
    public static final Hook NONE = new Hook("", NO_ARGS, false);

    private final String name;
    private final Object[] args;
    private final boolean writable;

    private boolean cancelled;
    private boolean hasResult;
    private Object result;

    /**
     * Вызов хука. Создаёт клиент, когда у хука есть обработчики.
     *
     * @param name имя хука
     * @param args аргументы; {@code null} — без аргументов
     */
    public Hook(String name, Object[] args) {
        this(name, args, true);
    }

    private Hook(String name, Object[] args, boolean writable) {
        this.name = name;
        this.args = args == null ? NO_ARGS : args;
        this.writable = writable;
    }

    /**
     * @return имя хука
     */
    public String name() {
        return name;
    }

    /**
     * @return число аргументов
     */
    public int size() {
        return args.length;
    }

    /**
     * @param index номер аргумента с нуля
     * @return аргумент, как его передал миксин
     */
    public Object arg(int index) {
        return args[index];
    }

    /**
     * Заменяет аргумент: миксин прочитает новое значение из этого же вызова.
     *
     * @param index номер аргумента с нуля
     * @param value новое значение
     */
    public void arg(int index, Object value) {
        if (writable) {
            args[index] = value;
        }
    }

    /**
     * Просит миксин отменить исходный метод.
     */
    public void cancel() {
        if (writable) {
            cancelled = true;
        }
    }

    /**
     * @return попросил ли кто-то из обработчиков отмену
     */
    public boolean cancelled() {
        return cancelled;
    }

    /**
     * Предлагает миксину своё возвращаемое значение.
     *
     * @param value значение
     */
    public void result(Object value) {
        if (!writable) {
            return;
        }
        result = value;
        hasResult = true;
    }

    /**
     * @return значение, предложенное обработчиком; смотри {@link #hasResult()}
     */
    public Object result() {
        return result;
    }

    /**
     * @return предложил ли обработчик значение, в том числе {@code null}
     */
    public boolean hasResult() {
        return hasResult;
    }
}
