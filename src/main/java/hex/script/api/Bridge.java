package hex.script.api;

/**
 * Точка стыковки скриптового API с клиентом.
 * <p>
 * Классы этого джара не проходят через обфускатор, поэтому их имена одинаковы
 * в SDK, против которого компилируется скрипт, и в рантайме клиента. Клиент
 * при запуске ставит сюда свою реализацию {@link ScriptHost}; всё остальное
 * API ходит в клиент только через неё.
 * <p>
 * Скрипту этот класс напрямую не нужен — пользуйся методами {@link Script}.
 */
public final class Bridge {

    private static volatile ScriptHost host;

    private Bridge() {
    }

    /**
     * Ставит реализацию хоста. Вызывается клиентом один раз при инициализации.
     *
     * @param value реализация; {@code null} снимает установленный хост
     */
    public static void install(ScriptHost value) {
        host = value;
    }

    /**
     * @return {@code true}, если клиент уже установил хост и API готово к работе
     */
    public static boolean installed() {
        return host != null;
    }

    /**
     * @return установленный хост
     * @throws IllegalStateException если API ещё не проинициализировано клиентом
     */
    public static ScriptHost host() {
        ScriptHost current = host;
        if (current == null) {
            throw new IllegalStateException("Script API bridge is not installed yet");
        }
        return current;
    }
}
