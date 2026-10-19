package hex.script.api;

/**
 * Статистика клиента: кадры, задержка, темп тиков, аккаунт, сервер и
 * активные эффекты игрока. Всё безопасно вне мира — там числа нулевые,
 * строки пустые, а список эффектов пуст.
 * <p>
 * Масштаб интерфейса живёт в {@link Control#guiScale()}, здесь его нет.
 *
 * <pre>{@code
 * ClientInfo info = client();
 * String line = info.username() + "  " + info.fps() + " fps  " + info.ping() + " ms";
 * for (Effect effect : info.effects()) {
 *     line += "  " + effect.key() + " " + effect.level();
 * }
 * }</pre>
 */
public final class ClientInfo {

    /** Число по запросу: 0 — кадры, 1 — задержка, 2 — темп тиков. */
    @FunctionalInterface
    public interface NumberOp {
        double run(int query);
    }

    /** Флаг по запросу: 0 — тики заморожены, 1 — одиночная игра. */
    @FunctionalInterface
    public interface FlagOp {
        boolean run(int query);
    }

    /** Строка по запросу: 0 — ник аккаунта, 1 — адрес сервера. */
    @FunctionalInterface
    public interface TextOp {
        String run(int query);
    }

    /** Снимок всех активных эффектов игрока. */
    /** Заполнение снимка открытого экрана. */
    @FunctionalInterface
    public interface ScreenOp {
        void run(Screen target);
    }

    /** Подмена защищённых ников в произвольной строке. */
    @FunctionalInterface
    public interface ProtectOp {
        String run(String text);
    }

    @FunctionalInterface
    public interface EffectsOp {
        Effect[] run();
    }

    /** Снимок одного эффекта по id или {@code null}. */
    @FunctionalInterface
    public interface EffectOp {
        Effect run(String id);
    }

    private static final Effect[] EMPTY = new Effect[0];

    private final NumberOp numberOp;
    private final FlagOp flagOp;
    private final TextOp textOp;
    private final EffectsOp effectsOp;
    private final ProtectOp protectOp;
    private final ScreenOp screenOp;

    private final Screen screen = new Screen();
    private final EffectOp effectOp;

    public ClientInfo(NumberOp numberOp, FlagOp flagOp, TextOp textOp,
                      EffectsOp effectsOp, ProtectOp protectOp, ScreenOp screenOp, EffectOp effectOp) {
        this.numberOp = numberOp;
        this.flagOp = flagOp;
        this.textOp = textOp;
        this.effectsOp = effectsOp;
        this.protectOp = protectOp;
        this.screenOp = screenOp;
        this.effectOp = effectOp;
    }

    /**
     * Кадры в секунду — то же число, что клиент рисует в своём вотермарке.
     * Считается за прошлую секунду, поэтому обновляется раз в секунду.
     *
     * @return кадры в секунду
     */
    public int fps() {
        return (int) numberOp.run(0);
    }

    /**
     * Задержка до сервера в миллисекундах. Меряется по обратным пакетам
     * клиента, а не по вкладке игроков, поэтому цифра живее ванильной. В
     * одиночной игре и до первого замера возвращает 0.
     *
     * @return задержка в миллисекундах
     */
    public int ping() {
        return (int) numberOp.run(1);
    }

    /**
     * Темп тиков мира, обычный — 20. Сервер умеет его менять, и тогда все
     * длительности в тиках идут не по 50 миллисекунд.
     *
     * @return тиков в секунду; 0 вне мира
     */
    public float tickrate() {
        return (float) numberOp.run(2);
    }

    /**
     * @return заморожены ли тики мира сервером
     */
    public boolean frozen() {
        return flagOp.run(0);
    }

    /**
     * @return играешь ли в одиночной игре
     */
    public boolean singleplayer() {
        return flagOp.run(1);
    }

    /**
     * @return открыт ли отладочный экран F3
     */
    public boolean debugScreen() {
        return flagOp.run(2);
    }

    /**
     * Ник аккаунта. Известен и в главном меню, в отличие от
     * {@link Player#name()}. Ни токен, ни сессия наружу не отдаются.
     *
     * @return ник аккаунта
     */
    public String username() {
        return textOp.run(0);
    }

    /**
     * @return адрес текущего сервера; пусто в одиночной игре и вне мира
     */
    public String server() {
        return textOp.run(1);
    }

    /**
     * Имя аккаунта в Globals — то, под которым выдана лицензия клиента.
     * Не имеет отношения к нику Minecraft.
     *
     * @return имя в Globals; пусто, если профиль ещё не поднялся
     */
    public String globalsName() {
        return textOp.run(2);
    }

    /**
     * Ник Minecraft с учётом защиты ника: пока она включена, отдаётся
     * подменённое имя, а не настоящее. Это то, что безопасно рисовать на
     * экране — в отличие от {@link #username()}, который всегда отдаёт
     * настоящий ник.
     *
     * @return ник под защитой
     */
    public String protectedUsername() {
        return textOp.run(3);
    }

    /**
     * Пропускает строку через защиту ника: любые защищённые имена внутри
     * заменяются на подставные. Пока защита выключена, строка возвращается
     * как есть. Прогоняй через это всё, что рисуешь на экране — ники цели,
     * состав таба, чужие сообщения.
     *
     * @param text исходная строка
     * @return строка с подменёнными никами
     */
    /**
     * Открытый сейчас экран. В отличие от {@code screen()} у событий доступен
     * из любого места — например, чтобы показывать что-то только при открытом
     * чате.
     * <p>
     * Возвращается один и тот же объект, перезаполняемый на каждый вызов:
     * держать его между кадрами бессмысленно, читай сразу.
     *
     * @return снимок открытого экрана
     */
    public Screen screen() {
        screenOp.run(screen);
        return screen;
    }

    public String protect(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        String result = protectOp.run(text);
        return result == null ? text : result;
    }

    /**
     * Активные эффекты локального игрока. Каждый вызов собирает новые
     * снимки, так что в цикле отрисовки бери список один раз.
     *
     * @return эффекты; пустой массив без игрока
     */
    public Effect[] effects() {
        Effect[] value = effectsOp.run();
        return value == null ? EMPTY : value;
    }

    /**
     * Один эффект по id. Пространство имён можно не писать.
     *
     * @param id {@code "speed"} или {@code "minecraft:speed"}
     * @return снимок эффекта или {@code null}, если его нет
     */
    public Effect effect(String id) {
        return id == null || id.isEmpty() ? null : effectOp.run(id);
    }
}
