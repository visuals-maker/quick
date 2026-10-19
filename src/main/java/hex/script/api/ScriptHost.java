package hex.script.api;

/**
 * Мост в клиент. Собирается клиентом из отдельных функций и ставится через
 * {@link Bridge#install(ScriptHost)}.
 * <p>
 * Скрипты этот класс напрямую не трогают: всё нужное продублировано удобными
 * методами на {@link Script}.
 * <p>
 * Класс финальный, а операции приходят лямбдами не для красоты: клиент проходит
 * через обфускатор, и его класс, реализующий интерфейс отсюда, потерял бы имена
 * методов — интерфейс обфускатору не виден, override он не распознаёт.
 */
public final class ScriptHost {

    /** Сообщение в клиентский чат. */
    @FunctionalInterface
    public interface ChatOp {
        void run(String message);
    }

    /** Подписка владельца на событие. */
    @FunctionalInterface
    public interface SubscribeOp {
        boolean run(Object owner, String eventName, EventHandler handler);
    }

    /** Подписка на типизированное событие. */
    @FunctionalInterface
    public interface SubscribeTypedOp {
        boolean run(Object owner, Class<?> type, hex.script.api.event.EventListener<?> listener);
    }

    /** Снятие подписок владельца на одно событие. */
    @FunctionalInterface
    public interface UnsubscribeOp {
        void run(Object owner, String eventName);
    }

    /** Снятие всех подписок владельца. */
    @FunctionalInterface
    public interface UnsubscribeAllOp {
        void run(Object owner);
    }

    /** Список поддерживаемых имён событий. */
    @FunctionalInterface
    public interface NamesOp {
        String[] run();
    }

    /** Версия API клиента. */
    @FunctionalInterface
    public interface VersionOp {
        int run();
    }

    /** Текущая рисовалка HUD. */
    @FunctionalInterface
    public interface RenderOp {
        Render2D run();
    }

    /** Цель боевых модулей клиента. */
    @FunctionalInterface
    public interface TargetOp {
        Object run();
    }

    /** Снимок списка модулей клиента. */
    @FunctionalInterface
    public interface ModulesOp {
        ModuleInfo[] run();
    }

    /** Клиентские сервисы: конфиги, задачи, ассеты, буфер, уведомления. */
    @FunctionalInterface
    public interface ServicesOp {
        ScriptServices run();
    }

    /** Подписка владельца на хук из миксина. */
    @FunctionalInterface
    public interface HookOp {
        void run(Object owner, String name, HookHandler handler);
    }

    /** Вызов хука из миксина. */
    @FunctionalInterface
    public interface FireHookOp {
        Hook run(String name, Object[] args);
    }

    /** Есть ли у хука обработчики. */
    @FunctionalInterface
    public interface HookListeningOp {
        boolean run(String name);
    }

    private final VersionOp versionOp;
    private final ChatOp chatOp;
    private final RenderOp renderOp;
    private final SubscribeOp subscribeOp;
    private final SubscribeTypedOp subscribeTypedOp;
    private final UnsubscribeOp unsubscribeOp;
    private final UnsubscribeAllOp unsubscribeAllOp;
    private final NamesOp namesOp;
    private final TargetOp targetOp;
    private final ModulesOp modulesOp;
    private final ServicesOp servicesOp;
    private final HookOp hookOp;
    private final FireHookOp fireHookOp;
    private final HookListeningOp hookListeningOp;

    public ScriptHost(VersionOp versionOp,
                      ChatOp chatOp,
                      RenderOp renderOp,
                      SubscribeOp subscribeOp,
                      SubscribeTypedOp subscribeTypedOp,
                      UnsubscribeOp unsubscribeOp,
                      UnsubscribeAllOp unsubscribeAllOp,
                      NamesOp namesOp,
                      TargetOp targetOp,
                      ModulesOp modulesOp,
                      ServicesOp servicesOp,
                      HookOp hookOp,
                      FireHookOp fireHookOp,
                      HookListeningOp hookListeningOp) {
        this.versionOp = versionOp;
        this.chatOp = chatOp;
        this.renderOp = renderOp;
        this.subscribeOp = subscribeOp;
        this.subscribeTypedOp = subscribeTypedOp;
        this.unsubscribeOp = unsubscribeOp;
        this.unsubscribeAllOp = unsubscribeAllOp;
        this.namesOp = namesOp;
        this.targetOp = targetOp;
        this.modulesOp = modulesOp;
        this.servicesOp = servicesOp;
        this.hookOp = hookOp;
        this.fireHookOp = fireHookOp;
        this.hookListeningOp = hookListeningOp;
    }

    /**
     * Версия скриптового API клиента. Сверяется с {@link Script#API_VERSION},
     * против которой собран скрипт.
     *
     * @return версия API
     */
    public int apiVersion() {
        return versionOp.run();
    }

    /**
     * Пишет сообщение в клиентский чат. Видно только локальному игроку.
     *
     * @param message текст сообщения
     */
    public void chat(String message) {
        chatOp.run(message);
    }

    /**
     * Рисовалка HUD. Валидна только внутри {@link Script#onRender2D(Render2D)}.
     *
     * @return текущий контекст отрисовки
     */
    public Render2D render2D() {
        return renderOp.run();
    }

    /**
     * Подписывает обработчик на событие клиента.
     *
     * @param owner     владелец подписки; все его подписки снимаются разом
     * @param eventName имя события; неизвестное имя игнорируется
     * @param handler   обработчик
     * @return {@code true}, если имя события известно и подписка создана
     */
    public boolean subscribe(Object owner, String eventName, EventHandler handler) {
        return subscribeOp.run(owner, eventName, handler);
    }

    /**
     * Снимает подписки владельца на одно событие.
     *
     * @param owner     владелец подписки
     * @param eventName имя события
     */
    /**
     * Подписывает обработчик на типизированное событие.
     *
     * @param owner    скрипт-владелец
     * @param type     класс события
     * @param listener обработчик
     * @return принята ли подписка
     */
    public boolean subscribe(Object owner, Class<?> type, hex.script.api.event.EventListener<?> listener) {
        return subscribeTypedOp.run(owner, type, listener);
    }

    public void unsubscribe(Object owner, String eventName) {
        unsubscribeOp.run(owner, eventName);
    }

    /**
     * Снимает все подписки владельца: на события и на хуки. Клиент делает это
     * сам при выключении скрипта.
     *
     * @param owner владелец подписок
     */
    public void unsubscribeAll(Object owner) {
        unsubscribeAllOp.run(owner);
    }

    /**
     * @return имена событий, поддерживаемые этим клиентом
     */
    public String[] eventNames() {
        return namesOp.run();
    }

    /**
     * Цель, которую сейчас держат боевые модули клиента.
     *
     * @return {@code net.minecraft.world.entity.LivingEntity} или {@code null}
     */
    public Object target() {
        return targetOp.run();
    }

    /**
     * Свежий снимок списка модулей.
     *
     * @return массив снимков; менять его бесполезно
     */
    public ModuleInfo[] modules() {
        return modulesOp.run();
    }

    /**
     * Клиентские сервисы скрипта.
     *
     * @return конфиги, задачи, ассеты, буфер обмена, уведомления
     */
    public ScriptServices services() {
        return servicesOp.run();
    }

    /**
     * Подписывает обработчик владельца на хук из миксина.
     *
     * @param owner   владелец подписки
     * @param name    имя хука
     * @param handler обработчик
     */
    public void hook(Object owner, String name, HookHandler handler) {
        hookOp.run(owner, name, handler);
    }

    /**
     * Зовёт обработчики хука. Миксины ходят сюда через {@link Hooks#fire(String, Object...)}.
     *
     * @param name имя хука
     * @param args аргументы
     * @return ответ обработчиков или {@link Hook#NONE}
     */
    public Hook fireHook(String name, Object[] args) {
        return fireHookOp.run(name, args);
    }

    /**
     * @param name имя хука
     * @return есть ли у хука обработчики
     */
    public boolean hookListening(String name) {
        return hookListeningOp.run(name);
    }
}
