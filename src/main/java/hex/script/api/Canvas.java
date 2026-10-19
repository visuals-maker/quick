package hex.script.api;

/**
 * Прямоугольник, выданный настройке {@link Custom} в меню клиента: своя
 * область, позиция мыши и та же рисовалка, что и на HUD.
 *
 * <pre>{@code
 * custom("Strip", 18f).onDraw(canvas -> {
 *     Render2D render = canvas.render();
 *     render.drawClientRect(canvas.x(), canvas.y(), canvas.width(), canvas.height(), 3f, 0x40000000);
 *     render.drawText("sf_rounded_bold", "hello", canvas.x() + 4f, canvas.y() + 4f, 7f, 0f, -1);
 * });
 * }</pre>
 */
public final class Canvas {

    private final Render2D render;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final float mouseX;
    private final float mouseY;
    private final float alpha;

    public Canvas(Render2D render, float x, float y, float width, float height,
                  float mouseX, float mouseY, float alpha) {
        this.render = render;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.alpha = alpha;
    }

    /**
     * @return рисовалка клиента: прямоугольники, обводки, тексты, шрифты
     */
    public Render2D render() {
        return render;
    }

    /**
     * @return левый край выданной области
     */
    public float x() {
        return x;
    }

    /**
     * @return верхний край выданной области
     */
    public float y() {
        return y;
    }

    /**
     * @return ширина выданной области
     */
    public float width() {
        return width;
    }

    /**
     * @return высота выданной области
     */
    public float height() {
        return height;
    }

    /**
     * @return позиция курсора по X
     */
    public float mouseX() {
        return mouseX;
    }

    /**
     * @return позиция курсора по Y
     */
    public float mouseY() {
        return mouseY;
    }

    /**
     * Прозрачность окна настроек: цвета стоит умножать на неё, иначе настройка
     * не будет плавно появляться вместе с остальными.
     *
     * @return множитель прозрачности от 0 до 1
     */
    public float alpha() {
        return alpha;
    }

    /**
     * @return находится ли курсор внутри выданной области
     */
    public boolean hovered() {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    /**
     * @param color цвет {@code 0xAARRGGBB}
     * @return тот же цвет, умноженный на {@link #alpha()}
     */
    public int fade(int color) {
        int value = (int) (((color >>> 24) & 0xFF) * alpha);
        return (Math.max(0, Math.min(255, value)) << 24) | (color & 0xFFFFFF);
    }
}
