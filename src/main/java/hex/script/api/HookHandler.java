package hex.script.api;

/**
 * Обработчик хука из миксина, см. {@link Script#hook(String, HookHandler)}.
 */
@FunctionalInterface
public interface HookHandler {

    /**
     * @param hook вызов: аргументы, отмена и результат
     */
    void handle(Hook hook);
}
