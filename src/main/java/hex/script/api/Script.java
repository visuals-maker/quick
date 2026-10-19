package hex.script.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * База пользовательского скрипта. Наследуйся от неё, переопредели нужные
 * колбэки — и положи {@code .java} файл в {@code .minecraft/Hex/scripts/}.
 * <p>
 * Скрипт живёт как обычный модуль клиента: пока он выключен, ни один колбэк
 * не вызывается. При включении зовётся {@link #activate()}, при выключении —
 * {@link #deactivate()}.
 *
 * <pre>{@code
 * public class Circle extends Script {
 *
 *     private int ticks;
 *
 *     @Override
 *     public void activate() {
 *         on("tick", e -> ticks++);
 *     }
 *
 *     @Override
 *     public void onRender2D(Render2D render) {
 *         render.drawClientRect(10, 10, 100, 20, 4, render.themeColor("main"));
 *     }
 * }
 * }</pre>
 *
 * Все колбэки идут из главного потока игры. Долгая работа в них — это
 * подвисание клиента: тяжёлое считай заранее и клади в поле.
 */
public abstract class Script {

    private final List<Setting<?>> settings = new ArrayList<>();

    /**
     * Версия API, против которой собран скрипт. Значение подставляется
     * компилятором в момент сборки скрипта, поэтому его можно сверить с
     * {@link ScriptHost#apiVersion()} текущего клиента.
     */
    public static final int API_VERSION = 30;

    /**
     * Имя скрипта в интерфейсе клиента и в сообщениях об ошибках.
     * По умолчанию — простое имя класса.
     *
     * @return отображаемое имя
     */
    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * Вызывается при включении скрипта. Здесь заводят состояние, обнуляют
     * счётчики и подписываются на события через {@link #on(String, EventHandler)}.
     */
    public void activate() {
    }

    /**
     * Вызывается при выключении скрипта, а также перед перезагрузкой и при
     * выходе из игры. Подписки снимать не нужно — клиент снимает их сам.
     */
    public void deactivate() {
    }

    /**
     * Игровой тик, 20 раз в секунду. Сюда кладут логику, а не отрисовку.
     */
    public void onTick() {
    }

    /**
     * Кадр отрисовки HUD. Вызывается с частотой кадров, то есть чаще тика —
     * не считай тут ничего тяжёлого.
     *
     * @param render рисовалка, валидная только на время этого вызова
     */
    public void onRender2D(Render2D render) {
    }

    /**
     * Подписывает обработчик на событие клиента. Подписки снимаются
     * автоматически при выключении скрипта, вручную отписываться не нужно.
     * <p>
     * Список доступных имён — {@link #eventNames()}. На момент API 3 это
     * {@code "tick"}, {@code "update"}, {@code "motion"}, {@code "attack"},
     * {@code "jump"}, {@code "key"}, {@code "keyRelease"}, {@code "packetSend"},
     * {@code "packetReceive"}, {@code "render3d"}, {@code "gameTick"},
     * {@code "postUpdate"}, {@code "input"}, {@code "move"}, {@code "mouse"},
     * {@code "slotChange"}, {@code "hotbarSelect"}, {@code "clickSlot"},
     * {@code "useItem"}, {@code "interactItem"}, {@code "destroyBlock"},
     * {@code "placeBlock"}, {@code "newBlock"}, {@code "joinWorld"},
     * {@code "death"}, {@code "sendMessage"}, {@code "chatReceive"},
     * {@code "useTotem"}, {@code "entityVelocity"}, {@code "toggleModule"}.
     * <p>
     * Полезная нагрузка по событиям: {@code slotChange} и {@code hotbarSelect} —
     * {@code Integer} слот; {@code sendMessage} и {@code chatReceive} —
     * {@code String} текст;
     * {@code death}, {@code jump} — сущность; {@code move} — {@code Vec3};
     * {@code input} и {@code postUpdate} — {@code Object[]} из forward, strafe,
     * jump, shift, sprint; {@code mouse} — кнопка, {@code "press"} или
     * {@code "release"}, x, y; {@code clickSlot} — слот, кнопка, id контейнера;
     * {@code useItem} и {@code useTotem} — сущность и предмет;
     * {@code destroyBlock} — позиция и сторона; {@code newBlock} — позиция и
     * состояние; {@code entityVelocity} — сущность и вектор;
     * {@code toggleModule} — имя и новое состояние.
     * <p>
     * {@code "key"} приходит на нажатие и только когда не открыт ни один экран,
     * {@code "keyRelease"} — на отпускание. Полезная нагрузка у обоих —
     * {@code Integer} с кодом клавиши GLFW.
     * <p>
     * У {@code "render3d"} полезная нагрузка — {@code Object[]} из четырёх
     * ванильных объектов: {@code PoseStack}, {@code VertexConsumer} (линии,
     * топология DEBUG_LINES, формат позиция+цвет), {@code Camera} и
     * {@code Frustum}. Координаты вершин задаются относительно
     * {@code camera.position()}.
     *
     * @param eventName имя события
     * @param handler   обработчик
     * @return {@code true}, если имя известно этому клиенту
     */
    protected final boolean on(String eventName, EventHandler handler) {
        return Bridge.host().subscribe(this, eventName, handler);
    }

    /**
     * Снимает свои подписки на одно событие.
     *
     * @param eventName имя события
     */
    /**
     * Подписывает обработчик на типизированное событие. Подписка живёт, пока
     * скрипт включён, и снимается сама при выключении.
     *
     * <pre>{@code
     * on(MouseEvent.class, event -> {
     *     if (event.left() && event.pressed() && event.screen().chat()) {
     *         event.cancel();
     *     }
     * });
     * }</pre>
     *
     * @param type     класс события из {@code hex.script.api.event}
     * @param listener обработчик
     * @param <E>      тип события
     * @return принята ли подписка
     */
    protected final <E extends hex.script.api.event.Event> boolean on(
            Class<E> type, hex.script.api.event.EventListener<E> listener) {
        return Bridge.host().subscribe(this, type, listener);
    }

    protected final void off(String eventName) {
        Bridge.host().unsubscribe(this, eventName);
    }

    /**
     * @return имена событий, поддерживаемые этим клиентом
     */
    protected final String[] eventNames() {
        return Bridge.host().eventNames();
    }

    /**
     * Подписывает обработчик на хук, который зовёт миксин этого скрипта через
     * {@link Hooks#fire(String, Object...)}. Подписка живёт, пока скрипт
     * включён, поэтому выключенный скрипт миксину не отвечает. Сами миксины
     * лежат в {@code mixin/} и вшиваются только при запуске игры, а этот
     * обработчик, как и весь код из {@code java/}, перезагружается на лету.
     *
     * <pre>{@code
     * hook("vanillafly:shift", hook -> {
     *     if (hideSneak.value()) {
     *         hook.result(false);
     *     }
     * });
     * }</pre>
     *
     * @param name    имя хука, лучше с именем скрипта в начале
     * @param handler обработчик
     */
    protected final void hook(String name, HookHandler handler) {
        Bridge.host().hook(this, name, handler);
    }

    /**
     * Цель, которую сейчас держат боевые модули клиента: по ней бьёт аура,
     * её же показывает TargetHud. Возвращается ванильный
     * {@code net.minecraft.world.entity.LivingEntity}, но типом {@code Object} —
     * SDK собирается отдельно от игры и её классов не знает.
     *
     * <pre>{@code
     * if (target() instanceof LivingEntity entity) {
     *     render.drawClientText(entity.getName().getString(), 5f, 5f, 7f, -1);
     * }
     * }</pre>
     *
     * @return цель или {@code null}, если её нет
     */
    protected final Object target() {
        return Bridge.host().target();
    }

    /**
     * Свежий снимок модулей клиента: имя, клавиша бинда буквами, категория,
     * состояние и анимация включения.
     * <p>
     * Сами модули наружу не отдаются — только {@link ModuleInfo}, читать и
     * ничего больше. Массив собирается заново на каждый вызов, поэтому правки
     * в нём никуда не уходят; клиент из него ничего не читает.
     *
     * @return массив снимков, отсортированный как в меню клиента
     */
    protected final ModuleInfo[] modules() {
        return Bridge.host().modules();
    }

    /**
     * Пишет сообщение в клиентский чат. Видно только тебе.
     *
     * @param message текст сообщения
     */
    protected final void chat(String message) {
        Bridge.host().chat(message);
    }

    /**
     * Рисование в мире: линии, коробки, трейсеры, полигоны. Вызывается каждый
     * кадр, пока скрипт включён и игрок в мире.
     *
     * @param render рисовалка мира; валидна только внутри этого вызова
     */
    public void onRender3D(Render3D render) {
    }

    /**
     * Описание скрипта для меню и {@code .script list}.
     *
     * @return описание, по умолчанию пустое
     */
    public String description() {
        return "";
    }

    /**
     * Все настройки скрипта в порядке создания. Клиент читает этот список,
     * чтобы сохранить значения на диск и показать их в меню.
     *
     * @return неизменяемая копия списка
     */
    public final List<Setting<?>> settings() {
        return Collections.unmodifiableList(settings);
    }

    /**
     * Галка. Внутрь неё можно вкладывать другие настройки.
     *
     * @param name  имя в меню
     * @param value значение по умолчанию
     * @return созданная настройка
     */
    protected final CheckBox checkBox(String name, boolean value) {
        return checkBox(null, name, value);
    }

    /**
     * @param parent галка-родитель; вложенное видно, только пока она включена
     * @param name   имя в меню
     * @param value  значение по умолчанию
     * @return созданная настройка
     */
    protected final CheckBox checkBox(CheckBox parent, String name, boolean value) {
        return add(new CheckBox(parent, name, value));
    }

    /**
     * Числовой ползунок.
     *
     * @param name  имя в меню
     * @param value значение по умолчанию
     * @param min   нижняя граница
     * @param max   верхняя граница
     * @param step  шаг перетаскивания
     * @return созданная настройка
     */
    protected final Slider slider(String name, float value, float min, float max, float step) {
        return slider(null, name, value, min, max, step);
    }

    /**
     * @param parent галка-родитель
     * @param name   имя в меню
     * @param value  значение по умолчанию
     * @param min    нижняя граница
     * @param max    верхняя граница
     * @param step   шаг перетаскивания
     * @return созданная настройка
     */
    protected final Slider slider(CheckBox parent, String name, float value, float min, float max, float step) {
        return add(new Slider(parent, name, value, min, max, step));
    }

    /**
     * Выбор одного варианта.
     *
     * @param name     имя в меню
     * @param selected вариант по умолчанию
     * @param options  все варианты
     * @return созданная настройка
     */
    protected final Selectable selectable(String name, String selected, String... options) {
        return selectable(null, name, selected, options);
    }

    /**
     * @param parent   галка-родитель
     * @param name     имя в меню
     * @param selected вариант по умолчанию
     * @param options  все варианты
     * @return созданная настройка
     */
    protected final Selectable selectable(CheckBox parent, String name, String selected, String... options) {
        return add(new Selectable(parent, name, selected, options));
    }

    /**
     * Выбор любого числа вариантов.
     *
     * @param name        имя в меню
     * @param options     все варианты
     * @param preselected выбранные по умолчанию
     * @return созданная настройка
     */
    protected final Combo combo(String name, String[] options, String... preselected) {
        return combo(null, name, options, preselected);
    }

    /**
     * @param parent      галка-родитель
     * @param name        имя в меню
     * @param options     все варианты
     * @param preselected выбранные по умолчанию
     * @return созданная настройка
     */
    protected final Combo combo(CheckBox parent, String name, String[] options, String... preselected) {
        return add(new Combo(parent, name, options, preselected));
    }

    /**
     * Цвет в формате {@code 0xAARRGGBB}.
     *
     * @param name имя в меню
     * @param argb цвет по умолчанию
     * @return созданная настройка
     */
    protected final ColorPicker colorPicker(String name, int argb) {
        return colorPicker(null, name, argb);
    }

    /**
     * @param parent галка-родитель
     * @param name   имя в меню
     * @param argb   цвет по умолчанию
     * @return созданная настройка
     */
    protected final ColorPicker colorPicker(CheckBox parent, String name, int argb) {
        return add(new ColorPicker(parent, name, argb));
    }

    /**
     * Текстовое поле.
     *
     * @param name        имя в меню
     * @param value       текст по умолчанию
     * @param placeholder подсказка, видимая пока поле пустое
     * @return созданная настройка
     */
    protected final Input input(String name, String value, String placeholder) {
        return input(null, name, value, placeholder);
    }

    /**
     * @param parent      галка-родитель
     * @param name        имя в меню
     * @param value       текст по умолчанию
     * @param placeholder подсказка
     * @return созданная настройка
     */
    protected final Input input(CheckBox parent, String name, String value, String placeholder) {
        return add(new Input(parent, name, value, placeholder));
    }

    /**
     * Кнопка в меню.
     *
     * @param name   имя в меню
     * @param action что выполнить по нажатию
     * @return созданная настройка
     */
    protected final Button button(String name, Runnable action) {
        return button(null, name, action);
    }

    /**
     * @param parent галка-родитель
     * @param name   имя в меню
     * @param action что выполнить по нажатию
     * @return созданная настройка
     */
    protected final Button button(CheckBox parent, String name, Runnable action) {
        return add(new Button(parent, name, action));
    }

    /**
     * Клавиша с действием. Срабатывает, пока скрипт включён.
     *
     * @param name   имя в меню
     * @param key    код клавиши GLFW или {@link Hotkey#UNKNOWN}
     * @param action что выполнить по нажатию
     * @return созданная настройка
     */
    protected final Hotkey hotkey(String name, int key, Runnable action) {
        return hotkey(null, name, key, action);
    }

    /**
     * @param parent галка-родитель
     * @param name   имя в меню
     * @param key    код клавиши GLFW
     * @param action что выполнить по нажатию
     * @return созданная настройка
     */
    protected final Hotkey hotkey(CheckBox parent, String name, int key, Runnable action) {
        return add(new Hotkey(parent, name, key, action));
    }

    /**
     * Настройка со своей отрисовкой: клиент выдаёт полосу, рисует её скрипт.
     *
     * @param name   имя в меню
     * @param height высота полосы
     * @return созданная настройка
     */
    protected final Custom custom(String name, float height) {
        return custom(null, name, height);
    }

    /**
     * @param parent галка-родитель
     * @param name   имя в меню
     * @param height высота полосы
     * @return созданная настройка
     */
    protected final Custom custom(CheckBox parent, String name, float height) {
        return add(new Custom(parent, name, height));
    }

    /**
     * Своя команда клиента с префиксом {@code .}. Работает, пока скрипт
     * включён; на выключении снимается вместе с алиасами.
     *
     * <pre>{@code
     * command("home")
     *         .usage("<go|list>")
     *         .sub("go", go -> go.completes(0, "base", "farm")
     *                 .runs(context -> context.reply("going to " + context.arg(0))))
     *         .register();
     * }</pre>
     *
     * @param name имя команды без точки
     * @return построитель команды; вызови у него {@code register()}
     */
    protected final Command command(String name) {
        return new Command(name, built -> Bridge.host().services().command(this, built));
    }

    /**
     * Мир вокруг: блоки, лучи, погода, время.
     *
     * @return мир
     */
    protected final World world() {
        return Bridge.host().services().world();
    }

    /**
     * Локальный игрок: позиция, здоровье, сытость, режим, инвентарь.
     *
     * @return игрок
     */
    protected final Player player() {
        return Bridge.host().services().player();
    }

    /**
     * Инвентарь игрока: слоты, поиск и подсчёт предметов.
     *
     * @return инвентарь
     */
    protected final Inventory inventory() {
        return Bridge.host().services().inventory();
    }

    /**
     * Звуки клиента: проиграть по строковому id, заглушить. Слышно только тебе.
     *
     * @return звуки
     */
    protected final Sounds sounds() {
        return Bridge.host().services().sounds();
    }

    /**
     * Частицы мира, только у себя на экране.
     *
     * @return частицы
     */
    protected final Particles particles() {
        return Bridge.host().services().particles();
    }

    /**
     * Статистика клиента: кадры, задержка, темп тиков, аккаунт, сервер и
     * активные эффекты игрока.
     *
     * @return статистика клиента
     */
    protected final ClientInfo client() {
        return Bridge.host().services().client();
    }

    /**
     * Шрифты: встроенные в клиент и свои, зарегистрированные из папки ассетов.
     *
     * @return реестр шрифтов
     */
    protected final FontRegistry fonts() {
        return Bridge.host().services().fonts();
    }

    /**
     * Открытый контейнер: чтение слотов и клики по ним. У своего инвентаря
     * {@link Container#open()} отдаёт {@code false}.
     *
     * @return контейнер
     */
    protected final Container container() {
        return Bridge.host().services().container();
    }

    /**
     * Список игроков и команды скорборда: ники, пинги, режимы, префиксы.
     *
     * @return таб
     */
    protected final Tab tab() {
        return Bridge.host().services().tab();
    }

    /**
     * Боковое табло сервера: заголовок, строки и очки. Клиент пересобирает
     * его раз в тик, так что спрашивать можно каждый кадр.
     *
     * @return табло
     */
    protected final Scoreboard scoreboard() {
        return Bridge.host().services().scoreboard();
    }

    /**
     * Сущности мира: поиск вокруг, цель боевого модуля, сущность под прицелом.
     * Отдаёт обёртки {@link Entity} — ванильные объекты наружу не выдаются.
     *
     * @return сущности
     */
    protected final Entities entities() {
        return Bridge.host().services().entities();
    }

    /**
     * Сборка и отправка пакетов. События {@code packetSend} и
     * {@code packetReceive} отдают ванильные пакеты как есть.
     *
     * @return пакеты
     */
    protected final Packets packets() {
        return Bridge.host().services().packets();
    }

    /**
     * Что зажато прямо сейчас: клавиши, кнопки мыши, курсор. Опрашивает
     * систему напрямую, мимо биндов клиента.
     *
     * @return состояние ввода
     */
    protected final Keys keys() {
        return Bridge.host().services().keys();
    }

    /**
     * Прямые записи в локального игрока и кусок настроек игры.
     *
     * @return управление игроком
     */
    protected final Control control() {
        return Bridge.host().services().control();
    }

    /**
     * Слот в руке с автовозвратом и подбор брони.
     *
     * @return слоты
     */
    protected final Slots slots() {
        return Bridge.host().services().slots(this);
    }

    /**
     * Печать в свой чат, отправка на сервер и команды клиента.
     *
     * @return чат
     */
    protected final Chat chat() {
        return Bridge.host().services().chat(this);
    }

    /**
     * Запись в консоль скриптов.
     *
     * @return лог
     */
    protected final Log log() {
        return Bridge.host().services().log(this);
    }

    /**
     * Реестр своей геометрии: меш собирается один раз и рисуется каждый кадр
     * без пересборки.
     *
     * @return реестр мешей
     */
    protected final Gpu gpu() {
        return Bridge.host().services().gpu();
    }

    /**
     * Путевые точки клиента: постоянные и временные.
     *
     * @return путевые точки
     */
    protected final Waypoints waypoints() {
        return Bridge.host().services().waypoints();
    }

    /**
     * Генерация мира по сиду кодом игры: где встанут структуры и из каких частей соберутся.
     *
     * @return генерация мира
     */
    protected final Worldgen worldgen() {
        return Bridge.host().services().worldgen();
    }

    /**
     * Группа клиента, только чтение.
     *
     * @return группа
     */
    protected final Party party() {
        return Bridge.host().services().party();
    }

    /**
     * Повороты головы. Приоритет скрипта зажимается так, чтобы боевые модули
     * клиента всегда были старше и не воевали со скриптом за голову.
     *
     * @return повороты
     */
    protected final Rotations rotations() {
        return Bridge.host().services().rotations();
    }

    /**
     * Удары, использование предметов, работа с блоками и точки прицеливания.
     *
     * @return взаимодействие
     */
    protected final Interaction interaction() {
        return Bridge.host().services().interaction();
    }

    /**
     * Предсказание движения игрока и полёта снарядов.
     *
     * @return предсказание
     */
    protected final Prediction prediction() {
        return Bridge.host().services().prediction();
    }

    /**
     * Реестр своих шейдеров: исходники кладутся в пак скриптов, пайплайн
     * собирается на первой отрисовке.
     *
     * @return реестр шейдеров
     */
    protected final Shaders shaders() {
        return Bridge.host().services().shaders();
    }

    /**
     * Конфиг скрипта по умолчанию.
     *
     * @return конфиг с именем storage
     */
    protected final Storage storage() {
        return storage("storage");
    }

    /**
     * Именованный конфиг скрипта.
     *
     * @param name имя файла без расширения
     * @return конфиг
     */
    protected final Storage storage(String name) {
        return Bridge.host().services().storage(this, name);
    }

    /**
     * Планировщик скрипта. Задачи снимаются при выключении.
     *
     * @return планировщик
     */
    protected final Tasks tasks() {
        return Bridge.host().services().tasks(this);
    }

    /**
     * Чтение файлов из папки {@code Hex/scripts/assets}.
     *
     * @return доступ к ассетам
     */
    protected final Assets assets() {
        return Bridge.host().services().assets();
    }

    /**
     * Системный буфер обмена. Чтение отмечается в консоли скриптов — там
     * видно, какой скрипт полез в буфер.
     *
     * @return системный буфер обмена
     */
    protected final Clipboard clipboard() {
        return Bridge.host().services().clipboard(this);
    }

    /**
     * @return всплывающие уведомления клиента
     */
    protected final Notifications notifications() {
        return Bridge.host().services().notifications();
    }

    private <T extends Setting<?>> T add(T setting) {
        for (Setting<?> existing : settings) {
            if (existing.parent() == setting.parent() && existing.name().equals(setting.name())) {
                throw new IllegalArgumentException("setting " + setting.name() + " already exist");
            }
        }
        settings.add(setting);
        return setting;
    }
}
