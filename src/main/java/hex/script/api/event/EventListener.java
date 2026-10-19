package hex.script.api.event;

/**
 * Обработчик типизированного события.
 *
 * @param <E> тип события
 */
@FunctionalInterface
public interface EventListener<E extends Event> {

    /**
     * Вызывается на каждую отправку события, пока скрипт включён.
     *
     * @param event событие; валидно только внутри вызова
     */
    void handle(E event);
}
