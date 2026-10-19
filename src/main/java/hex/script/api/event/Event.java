package hex.script.api.event;

/**
 * База типизированных событий. Экземпляр переиспользуется клиентом между
 * отправками — держать его в поле и читать позже нельзя.
 *
 * <pre>{@code
 * on(MouseEvent.class, event -> {
 *     if (event.left() && event.pressed() && event.screen().chat()) {
 *         event.cancel();
 *     }
 * });
 * }</pre>
 */
public abstract class Event {

    /** Отмена текущей отправки. */
    @FunctionalInterface
    public interface CancelOp {
        void run();
    }

    /** Проверка, отменена ли текущая отправка. */
    @FunctionalInterface
    public interface CancelledOp {
        boolean run();
    }

    private boolean cancellable;
    private CancelOp cancelOp;
    private CancelledOp cancelledOp;

    /**
     * Подключает отмену перед вызовом обработчиков. Вызывает клиент.
     *
     * @param cancellable можно ли отменить
     * @param cancelOp    операция отмены
     * @param cancelledOp проверка отмены
     */
    public final void bind(boolean cancellable, CancelOp cancelOp, CancelledOp cancelledOp) {
        this.cancellable = cancellable;
        this.cancelOp = cancelOp;
        this.cancelledOp = cancelledOp;
    }

    /** Отцепляет отмену после вызова обработчиков. Вызывает клиент. */
    public final void release() {
        this.cancelOp = null;
        this.cancelledOp = null;
    }

    /**
     * @return можно ли отменить это событие
     */
    public final boolean cancellable() {
        return cancellable;
    }

    /**
     * @return отменено ли — этим скриптом или чем-то ещё
     */
    public final boolean cancelled() {
        return cancelledOp != null && cancelledOp.run();
    }

    /**
     * Отменяет событие: клиент не выполнит связанное действие.
     * На неотменяемом не делает ничего.
     */
    public final void cancel() {
        if (cancellable && cancelOp != null) {
            cancelOp.run();
        }
    }

    /**
     * Имя события, под которым оно ходит внутри клиента.
     *
     * @return имя
     */
    public abstract String name();
}
