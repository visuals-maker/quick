package hex.script.api;

import java.util.List;

/**
 * Снимок поиска структур: что уже найдено и сколько осталось. Пока {@link #done()} ложно, повторный
 * запрос с теми же параметрами отдаёт более полный снимок.
 */
public final class StructureSearch {

    private final List<FoundStructure> found;
    private final boolean done;
    private final float progress;
    private final String error;

    public StructureSearch(List<FoundStructure> found, boolean done, float progress, String error) {
        this.found = List.copyOf(found);
        this.done = done;
        this.progress = progress;
        this.error = error == null ? "" : error;
    }

    /** @return структуры, которые уже посчитаны и встанут */
    public List<FoundStructure> found() {
        return found;
    }

    /** @return все чанки квадрата проверены */
    public boolean done() {
        return done;
    }

    /** @return доля проверенных чанков от 0 до 1 */
    public float progress() {
        return progress;
    }

    /** @return почему поиск невозможен; пустая строка, если всё в порядке */
    public String error() {
        return error;
    }

    public boolean failed() {
        return !error.isEmpty();
    }
}
