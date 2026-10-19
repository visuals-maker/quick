package hex.script.api;

/**
 * Планировщик скрипта. Всё считается по игровому клиенту и выполняется
 * на его потоке, поэтому из задачи можно спокойно трогать мир и рендер.
 * Все задачи снимаются автоматически при выключении скрипта.
 *
 * <pre>{@code
 * tasks().after(500L, () -> chat("half a second has passed"));
 * int id = tasks().every(1000L, () -> chat("tick"));
 * tasks().cancel(id);
 * }</pre>
 */
public final class Tasks {

    /** Постановка задачи; возвращает её идентификатор. */
    @FunctionalInterface
    public interface ScheduleOp {
        int run(long delay, long period, int repeats, Runnable body);
    }

    /** Снятие задачи по идентификатору. */
    @FunctionalInterface
    public interface CancelOp {
        boolean run(int id);
    }

    /** Снятие всех задач скрипта. */
    @FunctionalInterface
    public interface CancelAllOp {
        int run();
    }

    private final ScheduleOp scheduleOp;
    private final CancelOp cancelOp;
    private final CancelAllOp cancelAllOp;

    public Tasks(ScheduleOp scheduleOp, CancelOp cancelOp, CancelAllOp cancelAllOp) {
        this.scheduleOp = scheduleOp;
        this.cancelOp = cancelOp;
        this.cancelAllOp = cancelAllOp;
    }

    /**
     * Выполнить один раз через задержку.
     *
     * @param millis задержка в миллисекундах
     * @param body   что выполнить
     * @return идентификатор задачи
     */
    public int after(long millis, Runnable body) {
        return scheduleOp.run(millis, 0L, 1, body);
    }

    /**
     * Выполнять бесконечно с периодом.
     *
     * @param millis период в миллисекундах
     * @param body   что выполнять
     * @return идентификатор задачи
     */
    public int every(long millis, Runnable body) {
        return scheduleOp.run(millis, millis, -1, body);
    }

    /**
     * Выполнить заданное число раз с периодом.
     *
     * @param millis период в миллисекундах
     * @param times  сколько раз
     * @param body   что выполнять
     * @return идентификатор задачи
     */
    public int repeat(long millis, int times, Runnable body) {
        return scheduleOp.run(millis, millis, times, body);
    }

    /**
     * Выполнить один раз на ближайшем кадре клиента.
     *
     * @param body что выполнить
     * @return идентификатор задачи
     */
    public int soon(Runnable body) {
        return scheduleOp.run(0L, 0L, 1, body);
    }

    /**
     * @param id идентификатор задачи
     * @return была ли задача снята
     */
    public boolean cancel(int id) {
        return cancelOp.run(id);
    }

    /**
     * @return сколько задач было снято
     */
    public int cancelAll() {
        return cancelAllOp.run();
    }
}
