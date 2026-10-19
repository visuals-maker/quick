package hex.script.api;

/**
 * Ручка выбранного слота: помнит, что было выбрано до неё, и умеет вернуть
 * слот назад. {@link #restore()}, {@link #restoreWhenSafe()}, {@link #keep()}
 * и {@link #close()} взаимоисключающие — срабатывает первый вызов.
 * <p>
 * Живые ручки скрипта возвращаются молча, когда скрипт выключается.
 */
public final class HeldSlot implements AutoCloseable {

    /** Закрытие ручки: 0 — вернуть сейчас, 1 — вернуть на следующем тике, 2 — оставить. */
    @FunctionalInterface
    public interface CloseOp {
        void run(int id, int mode);
    }

    private final int id;
    private final int slot;
    private final int original;
    private final CloseOp closeOp;

    private boolean closed;

    HeldSlot(int id, int slot, int original, CloseOp closeOp) {
        this.id = id;
        this.slot = slot;
        this.original = original;
        this.closeOp = closeOp;
    }

    /**
     * @return слот хотбара 0..8, который выбрала эта ручка
     */
    public int slot() {
        return slot;
    }

    /**
     * @return слот хотбара 0..8, выбранный до этой ручки
     */
    public int originalSlot() {
        return original;
    }

    /**
     * Возвращает слот сейчас же и синхронизирует его с сервером.
     */
    public void restore() {
        finish(0);
    }

    /**
     * Возвращает слот на следующем тике. Так удар успевает уйти с нужным
     * предметом в руке.
     */
    public void restoreWhenSafe() {
        finish(1);
    }

    /**
     * Отменяет возврат: слот остаётся выбранным.
     */
    public void keep() {
        finish(2);
    }

    /**
     * То же, что {@link #restoreWhenSafe()}.
     */
    @Override
    public void close() {
        finish(1);
    }

    private void finish(int mode) {
        if (closed) {
            return;
        }
        closed = true;
        closeOp.run(id, mode);
    }
}
