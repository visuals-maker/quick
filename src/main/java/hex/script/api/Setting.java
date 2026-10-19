package hex.script.api;

import java.util.function.BooleanSupplier;

/**
 * Общая часть всех настроек скрипта. Настройки создаются в конструкторе
 * скрипта фабриками {@link Script} и попадают в {@link Script#settings()}
 * в порядке создания.
 * <p>
 * Клиент сам сохраняет значения на диск и восстанавливает их при загрузке —
 * ключом служит {@link #id()}.
 */
public abstract class Setting<S extends Setting<S>> {

    private final String name;
    private final CheckBox parent;

    private String id;
    private String description = "";
    private BooleanSupplier visible = () -> true;

    Setting(CheckBox parent, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("the setting has an empty name");
        }
        this.parent = parent;
        this.name = name;
        this.id = name;
    }

    /**
     * @return отображаемое имя
     */
    public final String name() {
        return name;
    }

    /**
     * @return ключ, под которым значение лежит в конфиге
     */
    public final String id() {
        return id;
    }

    /**
     * Задаёт стабильный ключ конфига: имя можно потом менять, не теряя
     * сохранённое значение.
     *
     * @param stableId ключ
     * @return эта же настройка
     */
    public final S id(String stableId) {
        if (stableId != null && !stableId.isEmpty()) {
            this.id = stableId;
        }
        return self();
    }

    /**
     * @return подсказка под настройкой
     */
    public final String description() {
        return description;
    }

    /**
     * @param value подсказка
     * @return эта же настройка
     */
    public final S description(String value) {
        this.description = value == null ? "" : value;
        return self();
    }

    /**
     * Настройка, внутрь которой вложена эта.
     *
     * @return родительский чекбокс или {@code null}
     */
    public final CheckBox parent() {
        return parent;
    }

    /**
     * @return видна ли настройка сейчас; учитывает и родителя
     */
    public final boolean visible() {
        if (parent != null && (!parent.value() || !parent.visible())) {
            return false;
        }
        try {
            return visible.getAsBoolean();
        } catch (Throwable ignored) {
            return true;
        }
    }

    /**
     * Показывать настройку только когда условие истинно.
     *
     * @param condition условие
     * @return эта же настройка
     */
    public final S visibleWhen(BooleanSupplier condition) {
        if (condition != null) {
            this.visible = condition;
        }
        return self();
    }

    @SuppressWarnings("unchecked")
    private S self() {
        return (S) this;
    }

    /**
     * Значение строкой для конфига. Клиент зовёт это сам.
     *
     * @return сериализованное значение
     */
    public abstract String write();

    /**
     * Разбирает значение из конфига. Клиент зовёт это сам.
     *
     * @param raw строка из файла
     */
    public abstract void read(String raw);

    @Override
    public String toString() {
        return name + "=" + write();
    }
}
