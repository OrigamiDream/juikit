package studio.avis.juikit.internal;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Button {

    public static Builder builder() {
        return new Builder();
    }

    private Button() {
    }

    Object id;
    int priority;

    private Size empty = new Size();
    private Size size;
    private Size delegateSize;
    private BiFunction<JuikitView, Size, Size> dynamicSize;

    // Backgrounds
    private Image image;
    private Color color;
    private BiConsumer<JuikitView, Graphics> painter;

    // Events
    private Image hoverImage;
    private Color hoverColor;
    private BiConsumer<JuikitView, Graphics> hoverPainter;
    boolean hovered;

    private Image pressImage;
    private Color pressColor;
    private BiConsumer<JuikitView, Graphics> pressPainter;
    boolean pressed;

    boolean deferredReleased = false;
    boolean deferredPressed = false;
    private BiConsumer<JuikitView, Graphics> runnableReleased;
    private BiConsumer<JuikitView, Graphics> runnablePressed;
    private BiConsumer<JuikitView, Graphics> runnableWhile;

    boolean highPriorityOnly = true;

    void renderDefault(JuikitView view, Graphics graphics, ImageObserver observer) {
        drawInternal(view, graphics, observer, image, color, painter);
    }

    void renderHover(JuikitView view, Graphics graphics, ImageObserver observer) {
        drawInternal(view, graphics, observer, hoverImage, hoverColor, hoverPainter);
    }

    void renderPress(JuikitView view, Graphics graphics, ImageObserver observer) {
        drawInternal(view, graphics, observer, pressImage, pressColor, pressPainter);
    }

    boolean isHoverable() {
        return hoverImage != null || hoverColor != null || hoverPainter != null;
    }

    void activateBefore(JuikitView juikit, Graphics graphics) {
        if(runnablePressed != null) {
            runnablePressed.accept(juikit, graphics);
        }
    }

    void activateWhile(JuikitView juikit, Graphics graphics) {
        if(runnableWhile != null) {
            runnableWhile.accept(juikit, graphics);
        }
    }

    void activateAfter(JuikitView juikit, Graphics graphics) {
        if(runnableReleased != null) {
            runnableReleased.accept(juikit, graphics);
        }
    }

    private void drawInternal(JuikitView view, Graphics graphics, ImageObserver observer, Image image, Color color, BiConsumer<JuikitView, Graphics> painter) {
        Size size = chooseSize(view);
        if(image != null) {
            graphics.drawImage(image, size.x, size.y, size.width, size.height, observer);
        } else if(color != null) {
            Color original = graphics.getColor();
            graphics.setColor(color);
            graphics.fillRect(size.x, size.y, size.width, size.height);
            graphics.setColor(original);
        } else if(painter != null) {
            painter.accept(view, graphics);
        }
    }

    Size chooseSize(JuikitView juikit) {
        if(size != null) {
            return size;
        } else if(delegateSize != null) {
            delegateSize = dynamicSize.apply(juikit, delegateSize);
            return delegateSize;
        }
        return empty;
    }

    boolean checkHover(JuikitView juikit, int x, int y) {
        Size size = chooseSize(juikit);
        return size.x <= x && size.y <= y && (size.width + size.x) >= x && (size.height + size.y) >= y;
    }

    public static class Builder {

        final Button button = new Button();

        Builder() {
        }

        public Builder priority(int priority) {
            button.priority = priority;
            return this;
        }

        public Builder id(Object id) {
            button.id = id;
            return this;
        }

        public Builder sizeFixed(int x, int y, int width, int height) {
            button.size = new Size();
            button.size.x = x;
            button.size.y = y;
            button.size.width = width;
            button.size.height = height;
            return this;
        }

        public Builder sizeDynamic(BiFunction<JuikitView, Size, Size> function) {
            button.delegateSize = new Size();
            button.dynamicSize = function;
            return this;
        }

        public Builder background(Image image) {
            button.image = image;
            return this;
        }

        public Builder background(Color color) {
            button.color = color;
            return this;
        }

        public Builder background(BiConsumer<JuikitView, Graphics> painter) {
            button.painter = painter;
            return this;
        }

        public Builder hover(Image image) {
            button.hoverImage = image;
            return this;
        }

        public Builder hover(Color color) {
            button.hoverColor = color;
            return this;
        }

        public Builder hover(BiConsumer<JuikitView, Graphics> hoverPainter) {
            button.hoverPainter = hoverPainter;
            return this;
        }

        public Builder press(Image image) {
            button.pressImage = image;
            return this;
        }

        public Builder press(Color color) {
            button.pressColor = color;
            return this;
        }

        public Builder press(BiConsumer<JuikitView, Graphics> pressPainter) {
            button.pressPainter = pressPainter;
            return this;
        }

        public Builder processPressed(BiConsumer<JuikitView, Graphics> runnable) {
            button.runnablePressed = runnable;
            return this;
        }

        public Builder processReleased(BiConsumer<JuikitView, Graphics> runnable) {
            button.runnableReleased = runnable;
            return this;
        }

        public Builder processWhile(BiConsumer<JuikitView, Graphics> runnable) {
            button.runnableWhile = runnable;
            return this;
        }

        public Builder highPriorityOnly(boolean highPriorityOnly) {
            button.highPriorityOnly = highPriorityOnly;
            return this;
        }
    }
}
