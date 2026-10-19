package hex.script.api;

import java.util.Arrays;
import java.util.List;

/**
 * Чтение файлов из папки {@code Hex/scripts/assets}. Наружу этой папки
 * выйти нельзя: {@code ..} и абсолютные пути отбрасываются.
 *
 * <pre>{@code
 * for (String line : assets().lines("words.txt")) {
 *     chat(line);
 * }
 * }</pre>
 */
public final class Assets {

    /** Проверка наличия файла. */
    @FunctionalInterface
    public interface ExistsOp {
        boolean run(String path);
    }

    /** Чтение файла целиком. */
    @FunctionalInterface
    public interface TextOp {
        String run(String path);
    }

    /** Чтение файла байтами. */
    @FunctionalInterface
    public interface BytesOp {
        byte[] run(String path);
    }

    /** Список файлов в подпапке. */
    @FunctionalInterface
    public interface ListOp {
        String[] run(String path);
    }

    private final ExistsOp existsOp;
    private final TextOp textOp;
    private final BytesOp bytesOp;
    private final ListOp listOp;

    public Assets(ExistsOp existsOp, TextOp textOp, BytesOp bytesOp, ListOp listOp) {
        this.existsOp = existsOp;
        this.textOp = textOp;
        this.bytesOp = bytesOp;
        this.listOp = listOp;
    }

    /**
     * @param path путь относительно {@code scripts/assets}
     * @return есть ли такой файл
     */
    public boolean exists(String path) {
        return existsOp.run(path);
    }

    /**
     * @param path путь относительно {@code scripts/assets}
     * @return содержимое файла или {@code null}, если его нет
     */
    public String text(String path) {
        return textOp.run(path);
    }

    /**
     * @param path путь относительно {@code scripts/assets}
     * @return строки файла; пустой список, если файла нет
     */
    public List<String> lines(String path) {
        String text = textOp.run(path);
        if (text == null || text.isEmpty()) {
            return List.of();
        }
        return List.of(text.split("\r?\n"));
    }

    /**
     * @param path путь относительно {@code scripts/assets}
     * @return байты файла или {@code null}, если его нет
     */
    public byte[] bytes(String path) {
        return bytesOp.run(path);
    }

    /**
     * @param path подпапка относительно {@code scripts/assets}, пустая строка — корень
     * @return имена файлов внутри
     */
    public List<String> list(String path) {
        String[] names = listOp.run(path);
        return names == null ? List.of() : List.copyOf(Arrays.asList(names));
    }
}
