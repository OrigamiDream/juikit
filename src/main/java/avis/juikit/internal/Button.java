package avis.juikit.internal;

import avis.juikit.Juikit;

import java.awt.*;
import java.awt.image.ImageObserver;

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
    private Function2d<Juikit, Size, Size> dynamicSize;

    // Backgrounds
    private Image image;
    private Color color;
    private Consumer2d<Juikit, Graphics> painter;

    // Events
    private Image hoverImage;
    private Color hoverColor;
    private Consumer2d<Juikit, Graphics> hoverPainter;
    boolean hovered;

    private Image pressImage;
    private Color pressColor;
    private Consumer2d<Juikit, Graphics> pressPainter;
    boolean pressed;

    boolean deferredReleased = false;
    boolean deferredPressed = false;
    private Consumer2d<Juikit, Graphics> runnableReleased;
    private Consumer2d<Juikit, Graphics> runnablePressed;
    private Consumer2d<Juikit, Graphics> runnableWhile;

    boolean highPriorityOnly = true;

    void renderDefault(Juikit juikit, Graphics graphics, ImageObserver observer) {
        drawInternal(juikit, graphics, observer, image, color, painter);
    }

    void renderHover(Juikit juikit, Graphics graphics, ImageObserver observer) {
        drawInternal(juikit, graphics, observer, hoverImage, hoverColor, hoverPainter);
    }

    void renderPress(Juikit juikit, Graphics graphics, ImageObserver observer) {
        drawInternal(juikit, graphics, observer, pressImage, pressColor, pressPainter);
    }

    boolean isHoverable() {
        return hoverImage != null || hoverColor != null || hoverPainter != null;
    }

    void activateBefore(Juikit juikit, Graphics graphics) {
        if(runnablePressed != null) {
            runnablePressed.accept(juikit, graphics);
        }
    }

    void activateWhile(Juikit juikit, Graphics graphics) {
        if(runnableWhile != null) {
            runnableWhile.accept(juikit, graphics);
        }
    }

    void activateAfter(Juikit juikit, Graphics graphics) {
        if(runnableReleased != null) {
            runnableReleased.accept(juikit, graphics);
        }
    }

    private void drawInternal(Juikit juikit, Graphics graphics, ImageObserver observer, Image image, Color color, Consumer2d<Juikit, Graphics> painter) {
        Size size = chooseSize(juikit);
        if(image != null) {
            graphics.drawImage(image, size.x, size.y, size.width, size.height, observer);
        } else if(color != null) {
            Color original = graphics.getColor();
            graphics.setColor(color);
            graphics.fillRect(size.x, size.y, size.width, size.height);
            graphics.setColor(original);
        } else if(painter != null) {
            painter.accept(juikit, graphics);
        }
    }

    Size chooseSize(Juikit juikit) {
        if(size != null) {
            return size;
        } else if(delegateSize != null) {
            delegateSize = dynamicSize.apply(juikit, delegateSize);
            return delegateSize;
        }
        return empty;
    }

    boolean checkHover(Juikit juikit, int x, int y) {
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

        public Builder sizeDynamic(Function2d<Juikit, Size, Size> function) {
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

        public Builder background(Consumer2d<Juikit, Graphics> painter) {
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

        public Builder hover(Consumer2d<Juikit, Graphics> hoverPainter) {
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

        public Builder press(Consumer2d<Juikit, Graphics> pressPainter) {
            button.pressPainter = pressPainter;
            return this;
        }

        public Builder processPressed(Consumer2d<Juikit, Graphics> runnable) {
            button.runnablePressed = runnable;
            return this;
        }

        public Builder processReleased(Consumer2d<Juikit, Graphics> runnable) {
            button.runnableReleased = runnable;
            return this;
        }

        public Builder processWhile(Consumer2d<Juikit, Graphics> runnable) {
            button.runnableWhile = runnable;
            return this;
        }

        public Builder highPriorityOnly(boolean highPriorityOnly) {
            button.highPriorityOnly = highPriorityOnly;
            return this;
        }
    }
}
