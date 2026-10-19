package hex.script.api;

/**
 * Связь миксинов скрипта с его обычным кодом.
 * <p>
 * Миксины лежат в папке {@code mixin/} jar-скрипта, в пакете
 * {@code scriptmixin.<имя jar в нижнем регистре>}, и вшиваются в игру при её
 * запуске — после их правки нужен перезапуск Minecraft. Код из {@code java/}
 * перезагружается на лету, но из миксина его классы не видны: они живут в
 * другом загрузчике. Мост между ними — именованные хуки: миксин зовёт
 * {@link #fire(String, Object...)}, скрипт отвечает через
 * {@link Script#hook(String, HookHandler)}. Пока скрипт выключен, его
 * обработчики не вызываются.
 * <p>
 * Имена хуков общие на все скрипты, поэтому начинай их с имени своего скрипта.
 * Скрипт в песочнице не может обращаться к классам Minecraft, так что передавай
 * в хук числа, строки и типы этого API.
 */
public final class Hooks {

    private Hooks() {
    }

    /**
     * Зовёт обработчики хука в том потоке, где сработал миксин.
     *
     * @param name имя хука
     * @param args аргументы; обработчик может заменить их через {@link Hook#arg(int, Object)}
     * @return ответ обработчиков; {@link Hook#NONE}, если слушателей нет или клиент ещё не готов
     */
    public static Hook fire(String name, Object... args) {
        return Bridge.installed() ? Bridge.host().fireHook(name, args) : Hook.NONE;
    }

    /**
     * Есть ли у хука обработчики. Проверяй до того, как собирать дорогие
     * аргументы: без слушателей {@link #fire(String, Object...)} их не прочитает.
     *
     * @param name имя хука
     * @return подписан ли хоть один включённый скрипт
     */
    public static boolean listening(String name) {
        return Bridge.installed() && Bridge.host().hookListening(name);
    }
}
