package hex.script.api;

/**
 * Набор клиентских сервисов, доступных скрипту. Собирается клиентом из
 * отдельных функций — как и {@link ScriptHost}, чтобы пережить обфускацию.
 */
public final class ScriptServices {

    /** Конфиг скрипта по имени. */
    @FunctionalInterface
    public interface StorageOp {
        Storage run(Object owner, String name);
    }

    /** Планировщик скрипта. */
    @FunctionalInterface
    public interface TasksOp {
        Tasks run(Object owner);
    }

    /** Чтение папки assets. */
    @FunctionalInterface
    public interface AssetsOp {
        Assets run();
    }

    /** Системный буфер обмена. */
    @FunctionalInterface
    public interface ClipboardOp {
        Clipboard run(Object owner);
    }

    /** Уведомления клиента. */
    @FunctionalInterface
    public interface NotificationsOp {
        Notifications run();
    }

    /** Реестр своей геометрии. */
    @FunctionalInterface
    public interface GpuOp {
        Gpu run();
    }

    /** Путевые точки клиента. */
    @FunctionalInterface
    public interface WaypointsOp {
        Waypoints run();
    }

    /** Генерация мира по сиду. */
    @FunctionalInterface
    public interface WorldgenOp {
        Worldgen run();
    }

    /** Группа клиента. */
    @FunctionalInterface
    public interface PartyOp {
        Party run();
    }

    /** Повороты головы. */
    @FunctionalInterface
    public interface RotationsOp {
        Rotations run();
    }

    /** Удары, использование, блоки. */
    @FunctionalInterface
    public interface InteractionOp {
        Interaction run();
    }

    /** Предсказание движения и снарядов. */
    @FunctionalInterface
    public interface PredictionOp {
        Prediction run();
    }

    /** Реестр своих шейдеров. */
    @FunctionalInterface
    public interface ShadersOp {
        Shaders run();
    }

    /** Мир и блоки. */
    @FunctionalInterface
    public interface WorldOp {
        World run();
    }

    /** Локальный игрок. */
    @FunctionalInterface
    public interface PlayerOp {
        Player run();
    }

    /** Инвентарь игрока. */
    @FunctionalInterface
    public interface InventoryOp {
        Inventory run();
    }

    /** Звуки клиента. */
    @FunctionalInterface
    public interface SoundsOp {
        Sounds run();
    }

    /** Частицы мира. */
    @FunctionalInterface
    public interface ParticlesOp {
        Particles run();
    }

    /** Статистика клиента и эффекты. */
    @FunctionalInterface
    public interface ClientOp {
        ClientInfo run();
    }

    /** Реестр шрифтов. */
    @FunctionalInterface
    public interface FontsOp {
        FontRegistry run();
    }

    /** Открытый контейнер. */
    @FunctionalInterface
    public interface ContainerOp {
        Container run();
    }

    /** Список игроков и команды скорборда. */
    @FunctionalInterface
    public interface TabOp {
        Tab run();
    }

    /** Боковое табло сервера. */
    @FunctionalInterface
    public interface ScoreboardOp {
        Scoreboard run();
    }

    /** Сущности мира. */
    @FunctionalInterface
    public interface EntitiesOp {
        Entities run();
    }

    /** Сборка и отправка пакетов. */
    @FunctionalInterface
    public interface PacketsOp {
        Packets run();
    }

    /** Что зажато прямо сейчас. */
    @FunctionalInterface
    public interface KeysOp {
        Keys run();
    }

    /** Прямые записи в игрока. */
    @FunctionalInterface
    public interface ControlOp {
        Control run();
    }

    /** Слот в руке и броня. */
    @FunctionalInterface
    public interface SlotsOp {
        Slots run(Object owner);
    }

    /** Печать в чат. */
    @FunctionalInterface
    public interface ChatOp {
        Chat run(Object owner);
    }

    /** Запись в консоль скриптов. */
    @FunctionalInterface
    public interface LogOp {
        Log run(Object owner);
    }

    /** Регистрация своей команды клиента. */
    @FunctionalInterface
    public interface CommandOp {
        void run(Object owner, Command command);
    }

    private final StorageOp storageOp;
    private final TasksOp tasksOp;
    private final AssetsOp assetsOp;
    private final ClipboardOp clipboardOp;
    private final NotificationsOp notificationsOp;
    private final CommandOp commandOp;
    private final ShadersOp shadersOp;
    private final RotationsOp rotationsOp;
    private final InteractionOp interactionOp;
    private final PredictionOp predictionOp;
    private final WaypointsOp waypointsOp;
    private final PartyOp partyOp;
    private final GpuOp gpuOp;
    private final WorldOp worldOp;
    private final PlayerOp playerOp;
    private final InventoryOp inventoryOp;
    private final SoundsOp soundsOp;
    private final ParticlesOp particlesOp;
    private final ClientOp clientOp;
    private final FontsOp fontsOp;
    private final ContainerOp containerOp;
    private final TabOp tabOp;
    private final ScoreboardOp scoreboardOp;
    private final EntitiesOp entitiesOp;
    private final PacketsOp packetsOp;
    private final KeysOp keysOp;
    private final ControlOp controlOp;
    private final SlotsOp slotsOp;
    private final ChatOp chatOp;
    private final LogOp logOp;
    private final WorldgenOp worldgenOp;

    public ScriptServices(StorageOp storageOp,
                          TasksOp tasksOp,
                          AssetsOp assetsOp,
                          ClipboardOp clipboardOp,
                          NotificationsOp notificationsOp,
                          CommandOp commandOp,
                          ShadersOp shadersOp,
                          RotationsOp rotationsOp,
                          InteractionOp interactionOp,
                          PredictionOp predictionOp,
                          WaypointsOp waypointsOp,
                          PartyOp partyOp,
                          GpuOp gpuOp,
                          WorldOp worldOp,
                          PlayerOp playerOp,
                          InventoryOp inventoryOp,
                          SoundsOp soundsOp,
                          ParticlesOp particlesOp,
                          ClientOp clientOp,
                          FontsOp fontsOp,
                          ContainerOp containerOp,
                          TabOp tabOp,
                          ScoreboardOp scoreboardOp,
                          EntitiesOp entitiesOp,
                          PacketsOp packetsOp,
                          KeysOp keysOp,
                          ControlOp controlOp,
                          SlotsOp slotsOp,
                          ChatOp chatOp,
                          LogOp logOp,
                          WorldgenOp worldgenOp) {
        this.storageOp = storageOp;
        this.tasksOp = tasksOp;
        this.assetsOp = assetsOp;
        this.clipboardOp = clipboardOp;
        this.notificationsOp = notificationsOp;
        this.commandOp = commandOp;
        this.shadersOp = shadersOp;
        this.rotationsOp = rotationsOp;
        this.interactionOp = interactionOp;
        this.predictionOp = predictionOp;
        this.waypointsOp = waypointsOp;
        this.partyOp = partyOp;
        this.gpuOp = gpuOp;
        this.worldOp = worldOp;
        this.playerOp = playerOp;
        this.inventoryOp = inventoryOp;
        this.soundsOp = soundsOp;
        this.particlesOp = particlesOp;
        this.clientOp = clientOp;
        this.fontsOp = fontsOp;
        this.containerOp = containerOp;
        this.tabOp = tabOp;
        this.scoreboardOp = scoreboardOp;
        this.entitiesOp = entitiesOp;
        this.packetsOp = packetsOp;
        this.keysOp = keysOp;
        this.controlOp = controlOp;
        this.slotsOp = slotsOp;
        this.chatOp = chatOp;
        this.logOp = logOp;
        this.worldgenOp = worldgenOp;
    }

    /**
     * @param owner скрипт-владелец
     * @param name  имя конфига
     * @return конфиг скрипта
     */
    public Storage storage(Object owner, String name) {
        return storageOp.run(owner, name);
    }

    /**
     * @param owner скрипт-владелец
     * @return планировщик скрипта
     */
    public Tasks tasks(Object owner) {
        return tasksOp.run(owner);
    }

    /**
     * @return чтение папки {@code scripts/assets}
     */
    public Assets assets() {
        return assetsOp.run();
    }

    /**
     * @param owner скрипт-владелец
     * @return системный буфер обмена
     */
    public Clipboard clipboard(Object owner) {
        return clipboardOp.run(owner);
    }

    /**
     * @return уведомления клиента
     */
    public Notifications notifications() {
        return notificationsOp.run();
    }

    /**
     * Регистрирует команду скрипта.
     *
     * @param owner   скрипт-владелец
     * @param command готовое дерево команд
     */
    public void command(Object owner, Command command) {
        commandOp.run(owner, command);
    }

    /**
     * @return реестр своих шейдеров
     */
    public Shaders shaders() {
        return shadersOp.run();
    }

    /**
     * @return повороты головы
     */
    public Rotations rotations() {
        return rotationsOp.run();
    }

    /**
     * @return удары, использование предметов, блоки
     */
    public Interaction interaction() {
        return interactionOp.run();
    }

    /**
     * @return предсказание движения и снарядов
     */
    public Prediction prediction() {
        return predictionOp.run();
    }

    /**
     * @return путевые точки клиента
     */
    public Waypoints waypoints() {
        return waypointsOp.run();
    }

    /**
     * @return генерация мира по сиду
     */
    public Worldgen worldgen() {
        return worldgenOp.run();
    }

    /**
     * @return группа клиента
     */
    public Party party() {
        return partyOp.run();
    }

    /**
     * @return реестр своей геометрии
     */
    public Gpu gpu() {
        return gpuOp.run();
    }

    /**
     * @return мир и блоки
     */
    public World world() {
        return worldOp.run();
    }

    /**
     * @return локальный игрок
     */
    public Player player() {
        return playerOp.run();
    }

    /**
     * @return инвентарь игрока
     */
    public Inventory inventory() {
        return inventoryOp.run();
    }

    /**
     * @return звуки клиента
     */
    public Sounds sounds() {
        return soundsOp.run();
    }

    /**
     * @return частицы мира
     */
    public Particles particles() {
        return particlesOp.run();
    }

    /**
     * @return статистика клиента и активные эффекты
     */
    public ClientInfo client() {
        return clientOp.run();
    }

    /**
     * @return реестр шрифтов
     */
    public FontRegistry fonts() {
        return fontsOp.run();
    }

    /**
     * @return открытый контейнер
     */
    public Container container() {
        return containerOp.run();
    }

    /**
     * @return список игроков и команды скорборда
     */
    public Tab tab() {
        return tabOp.run();
    }

    /**
     * @return боковое табло сервера
     */
    public Scoreboard scoreboard() {
        return scoreboardOp.run();
    }

    /**
     * @return сущности мира
     */
    public Entities entities() {
        return entitiesOp.run();
    }

    /**
     * @return сборка и отправка пакетов
     */
    public Packets packets() {
        return packetsOp.run();
    }

    /**
     * @return что зажато прямо сейчас
     */
    public Keys keys() {
        return keysOp.run();
    }

    /**
     * @return прямые записи в игрока и настройки игры
     */
    public Control control() {
        return controlOp.run();
    }

    /**
     * @param owner скрипт-владелец
     * @return слот в руке и броня
     */
    public Slots slots(Object owner) {
        return slotsOp.run(owner);
    }

    /**
     * @param owner скрипт-владелец
     * @return печать в чат
     */
    public Chat chat(Object owner) {
        return chatOp.run(owner);
    }

    /**
     * @param owner скрипт-владелец
     * @return запись в консоль скриптов
     */
    public Log log(Object owner) {
        return logOp.run(owner);
    }
}
