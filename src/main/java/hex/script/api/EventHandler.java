package hex.script.api;

/**
 * Обработчик события клиента. Подписка — {@link Script#on(String, EventHandler)}.
 * <p>
 * Вызывается из главного потока игры: тяжёлая работа здесь тормозит клиент.
 * Исключение из обработчика гасит скрипт целиком, поэтому рискованные места
 * проверяй сам.
 */
@FunctionalInterface
public interface EventHandler {

    /**
     * @param event событие, валидное только на время этого вызова
     */
    void handle(ScriptEvent event);
}
