package hex.script.api.event;

/**
 * Клиентский тик. Приходит двадцать раз в секунду, пока игрок в мире.
 */
public final class TickEvent extends Event {

    @Override
    public String name() {
        return "tick";
    }
}
