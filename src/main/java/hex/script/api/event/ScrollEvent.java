package hex.script.api.event;

import hex.script.api.Screen;

/**
 * Прокрутка колеса мыши.
 */
public final class ScrollEvent extends Event {

    private final Screen screen = new Screen();

    private double amount;
    private double horizontal;

    /**
     * Заполняет событие. Вызывает клиент.
     *
     * @param amount     вертикальная прокрутка, вверх положительная
     * @param horizontal горизонтальная прокрутка
     */
    public void set(double amount, double horizontal) {
        this.amount = amount;
        this.horizontal = horizontal;
    }

    @Override
    public String name() {
        return "scroll";
    }

    /**
     * @return вертикальная прокрутка, вверх положительная
     */
    public double amount() {
        return amount;
    }

    /**
     * @return горизонтальная прокрутка
     */
    public double horizontal() {
        return horizontal;
    }

    /**
     * @return крутили ли вверх
     */
    public boolean up() {
        return amount > 0d;
    }

    /**
     * @return экран, открытый в момент прокрутки
     */
    public Screen screen() {
        return screen;
    }
}
