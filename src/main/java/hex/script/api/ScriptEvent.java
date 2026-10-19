package hex.script.api;

/**
 * Событие клиента, доставленное скрипту.
 * <p>
 * Экземпляр переиспользуется клиентом между вызовами — сохранять его в поле
 * и трогать позже нельзя, вне обработчика он пуст.
 */
public final class ScriptEvent {

    /** Отмена текущего события. */
    @FunctionalInterface
    public interface CancelOp {
        void run();
    }

    /** Проверка, отменено ли текущее событие. */
    @FunctionalInterface
    public interface CancelledOp {
        boolean run();
    }

    private String name = "";
    private Object payload;
    private boolean cancellable;
    private CancelOp cancelOp;
    private CancelledOp cancelledOp;

    /**
     * Заполняет событие перед вызовом обработчиков. Вызывает клиент.
     *
     * @param name        имя события
     * @param payload     полезная нагрузка или {@code null}
     * @param cancellable можно ли отменить
     * @param cancelOp    операция отмены
     * @param cancelledOp проверка отмены
     */
    public void set(String name, Object payload, boolean cancellable, CancelOp cancelOp, CancelledOp cancelledOp) {
        this.name = name;
        this.payload = payload;
        this.cancellable = cancellable;
        this.cancelOp = cancelOp;
        this.cancelledOp = cancelledOp;
    }

    /** Сбрасывает событие после вызова обработчиков. Вызывает клиент. */
    public void clear() {
        this.payload = null;
        this.cancelOp = null;
        this.cancelledOp = null;
    }

    /**
     * Имя события — то же, что передавалось в {@link Script#on(String, EventHandler)}.
     *
     * @return имя события
     */
    public String name() {
        return name;
    }

    /**
     * Полезная нагрузка события, если она есть. Тип зависит от события:
     * для {@code "packetSend"} и {@code "packetReceive"} это
     * {@code net.minecraft.network.protocol.Packet}, для {@code "attack"} —
     * {@code net.minecraft.world.entity.Entity}, для {@code "jump"} —
     * {@code net.minecraft.world.entity.LivingEntity} (событие приходит на
     * прыжок ЛЮБОГО существа, сравни с {@code Minecraft.getInstance().player}),
     * для {@code "key"} — код клавиши {@link Integer}, для {@code "render3d"} —
     * {@code Object[]}, для остальных {@code null}.
     * <p>
     * Проверяй тип через {@code instanceof} — классы ванили скриптам доступны:
     * <pre>{@code
     * on("packetSend", e -> {
     *     if (e.payload() instanceof ServerboundMovePlayerPacket) e.cancel();
     * });
     * }</pre>
     *
     * @return объект события или {@code null}
     */
    public Object payload() {
        return payload;
    }

    /**
     * @return {@code true}, если событие можно отменить
     */
    public boolean cancellable() {
        return cancellable;
    }

    /**
     * @return {@code true}, если событие уже отменено — этим скриптом или чем-то ещё
     */
    public boolean cancelled() {
        return cancelledOp != null && cancelledOp.run();
    }

    /**
     * Отменяет событие: клиент не выполнит связанное с ним действие.
     * На неотменяемом событии не делает ничего.
     */
    public void cancel() {
        if (cancellable && cancelOp != null) {
            cancelOp.run();
        }
    }
}
