package hex.script.api;

/**
 * Системный буфер обмена.
 */
public final class Clipboard {

    /** Чтение буфера. */
    @FunctionalInterface
    public interface ReadOp {
        String run();
    }

    /** Запись в буфер. */
    @FunctionalInterface
    public interface WriteOp {
        void run(String text);
    }

    private final ReadOp readOp;
    private final WriteOp writeOp;

    public Clipboard(ReadOp readOp, WriteOp writeOp) {
        this.readOp = readOp;
        this.writeOp = writeOp;
    }

    /**
     * @return текст из буфера, пустая строка если там ничего нет
     */
    public String read() {
        String value = readOp.run();
        return value == null ? "" : value;
    }

    /**
     * @param text что положить в буфер
     */
    public void write(String text) {
        writeOp.run(text == null ? "" : text);
    }
}
