package hex.script.api;

/**
 * Снимок одного модуля клиента: только чтение, только имена.
 * <p>
 * Настоящий объект модуля скриптам не отдаётся — через него можно было бы
 * включать чужие функции и лезть во внутренности клиента. Вместо него
 * {@link Script#modules()} на каждый вызов собирает свежий массив таких
 * снимков. Менять в нём нечего: поля финальные, сеттеров нет, а массив
 * одноразовый — правки в нём ни на что не влияют.
 *
 * <pre>{@code
 * for (ModuleInfo module : modules()) {
 *     if (module.animation() < 0.01f) {
 *         continue;
 *     }
 *     render.drawClientText(module.name() + " [" + module.bind() + "]",
 *             4f, y, 7f, render.themeColor("hudText"));
 * }
 * }</pre>
 */
public final class ModuleInfo {

    private String name = "";
    private String bind = "";
    private String category = "";
    private boolean enabled;
    private boolean bound;
    private float animation;

    public ModuleInfo() {
    }

    public ModuleInfo(String name, String bind, String category,
                      boolean enabled, boolean bound, float animation) {
        set(name, bind, category, enabled, bound, animation);
    }

    /**
     * Перезаполняет снимок. Вызывает клиент: список модулей отдаётся каждый
     * кадр, и переиспользование объектов убирает мусор на ровном месте.
     *
     * @param name      имя модуля
     * @param bind      имя клавиши
     * @param category  категория
     * @param enabled   включён ли
     * @param bound     назначена ли видимая клавиша
     * @param animation прогресс анимации включения от 0 до 1
     */
    public void set(String name, String bind, String category,
                    boolean enabled, boolean bound, float animation) {
        this.name = name == null ? "" : name;
        this.bind = bind == null ? "" : bind;
        this.category = category == null ? "" : category;
        this.enabled = enabled;
        this.bound = bound;
        this.animation = animation;
    }

    /**
     * Отображаемое имя модуля, как в меню клиента.
     *
     * @return имя модуля
     */
    public String name() {
        return name;
    }

    /**
     * Клавиша бинда буквами — {@code "R"}, {@code "LShift"}, {@code "Mouse4"}.
     * Если бинда нет, вернётся имя пустой клавиши, а {@link #bound()} будет
     * {@code false}.
     *
     * @return название клавиши
     */
    public String bind() {
        return bind;
    }

    /**
     * Категория модуля буквами — {@code "Combat"}, {@code "Render"} и так далее.
     *
     * @return имя категории
     */
    public String category() {
        return category;
    }

    /**
     * Включён ли модуль прямо сейчас.
     *
     * @return состояние модуля
     */
    public boolean enabled() {
        return enabled;
    }

    /**
     * Назначен ли модулю бинд.
     *
     * @return {@code true}, если клавиша выбрана
     */
    public boolean bound() {
        return bound;
    }

    /**
     * Анимация включения от 0 до 1. Клиент ведёт её сам: сразу после включения
     * ползёт к единице, после выключения — к нулю. Пока значение больше нуля,
     * модуль ещё стоит рисовать — так делаются плавные списки.
     *
     * @return текущее значение анимации
     */
    public float animation() {
        return animation;
    }

    @Override
    public String toString() {
        return name + " [" + bind + "]";
    }
}
