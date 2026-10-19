import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public final class DecryptHexAssets {
    private static final byte[] MAGIC = {0x48, 0x58, 0x54, 0x58, 0x01};
    private static final byte[] MARK = {(byte) 0xB7, 0x2F, (byte) 0xA1, 0x5C, 0x02};
    private static final long MASK = -1L;
    private static final int[] PERM = {3, 0, 5, 7, 5};

    public static void main(String[] args) throws Exception {
        Path in = Path.of(args[0]);
        Path out = Path.of(args[1]);
        Files.createDirectories(out);
        List<Path> files;
        try (var stream = Files.list(in)) {
            files = stream.filter(Files::isRegularFile).sorted().toList();
        }
        Map<String, String> logicalToPhysical = new LinkedHashMap<>();
        int ok = 0, failed = 0;
        for (Path file : files) {
            byte[] raw = Files.readAllBytes(file);
            byte[] plain;
            try {
                plain = decodeArchive(raw);
            } catch (RuntimeException e) {
                System.out.println("FAIL " + file.getFileName() + ": " + e.getMessage());
                failed++;
                continue;
            }
            String name = file.getFileName().toString();
            Path target = out.resolve(name);
            Files.write(target, plain);
            ok++;
            if (name.equals("0.bin")) {
                String index = new String(plain, StandardCharsets.UTF_8);
                Files.writeString(out.resolve("0.index.txt"), index);
                for (String line : index.split("\\R")) {
                    int tab = line.indexOf('\t');
                    if (tab > 0) {
                        String from = line.substring(0, tab);
                        String to = line.substring(tab + 1);
                        logicalToPhysical.put(from, to);
                    }
                }
            }
        }
        StringBuilder report = new StringBuilder();
        report.append("decoded=").append(ok).append(" failed=").append(failed).append('\n');
        report.append("logical=").append(logicalToPhysical.size()).append('\n');
        for (var e : logicalToPhysical.entrySet()) {
            report.append(e.getKey()).append('\t').append(e.getValue()).append('\n');
        }
        Files.writeString(out.resolve("manifest.tsv"), report.toString());
        System.out.print(report);
    }

    static byte[] decodeArchive(byte[] raw) {
        if (!startsWith(raw, MAGIC)) {
            return raw;
        }
        byte[] body = Arrays.copyOfRange(raw, MAGIC.length, raw.length);
        if (startsWith(body, MARK)) {
            return decryptMarked(body);
        }
        return xorFallbackDecrypt(body, body.length);
    }

    static byte[] decryptMarked(byte[] in) {
        int len = in.length - 13;
        if (len < 0) return new byte[0];
        long seed = 0L;
        for (int i = 0; i < 8; i++) {
            seed = seed << 8 | in[5 + i] & 255L;
        }
        int[] table = shuffle(seed);
        int[] inverse = new int[256];
        for (int i = 0; i < 256; i++) inverse[table[i]] = i;
        byte[] out = new byte[len];
        long state = seed;
        int prev = (int) (xs(seed ^ 0xA5A5A5A5A5A5A5A5L) & 255L);
        for (int i = 0; i < len; i++) {
            state = xs(state);
            int lo = (int) (state & 255L);
            int mid = (int) (state >>> 8 & 255L);
            int n = (int) (state >>> 16 & 7L);
            int cipher = in[13 + i] & 255;
            int value = cipher ^ prev;
            prev = cipher;
            value ^= lo;
            value = ((value >>> n | value << (8 - n)) & 255);
            value = (value - mid - (i & 255)) & 255;
            out[i] = (byte) inverse[value];
        }
        return out;
    }

    static long xs(long x) {
        x ^= x << 13;
        x ^= x >>> 7;
        x ^= x << 17;
        return x;
    }

    static int[] shuffle(long seed) {
        int[] t = new int[256];
        for (int i = 0; i < 256; i++) t[i] = i;
        long state = seed ^ 0x9E3779B97F4A7C15L;
        if (state == 0L) state = 0xD1B54A32D192ED03L;
        for (int i = 255; i > 0; i--) {
            state = xs(state);
            int j = (int) ((state >>> 11) % (i + 1));
            int tmp = t[i];
            t[i] = t[j];
            t[j] = tmp;
        }
        return t;
    }

    static byte[] xorFallbackDecrypt(byte[] in, int len) {
        int base = 0;
        byte[] out = new byte[len];
        int a = 0, b = 0, c = 0, d = 0, e = 0;
        for (int i = 0; i < len; i++) {
            int f = a + d;
            int g = PERM[c];
            int h = in[base + i] & 255;
            out[i] = (byte) ((h ^ g ^ e) - f & 255);
            int j = f + b;
            while (j >= 7) j -= 7;
            d += j;
            while (d >= 3) d -= 3;
            e += j;
            if (e >= 255) e -= 255;
            if (++a == 10) a = 0;
            if (++b == 7) b = 0;
            if (++c == 5) c = 0;
        }
        return out;
    }

    static boolean startsWith(byte[] data, byte[] prefix) {
        if (data.length < prefix.length) return false;
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) return false;
        }
        return true;
    }
}
