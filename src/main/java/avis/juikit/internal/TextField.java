package avis.juikit.internal;

import avis.juikit.Juikit;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class TextField {

    public static Builder builder() {
        return new Builder();
    }

    private TextField() {
    }

    JTextField field = new JTextField();

    Object id;
    String initText;

    Size prev = null;
    private Size empty = new Size();
    private Size size;
    private Size delegateSize;
    private Function2d<Juikit, Size, Size> dynamicSize;

    // Backgrounds
    private Image image;
    private Color color;
    private Consumer2d<Juikit, Graphics> painter;

    private Consumer3d<Juikit, Graphics, JTextField> updater;

    public JTextField getField() {
        return field;
    }

    public void renderDefault(Juikit juikit, Graphics graphics, ImageObserver observer) {
        drawInternal(juikit, graphics, observer, image, color, painter);
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
        if(!size.equals(prev)) {
            field.setBounds(size.x, size.y, size.width, size.height);
            field.setSize(size.width, size.height);
            prev = size.copy();
        }
        if(updater != null) {
            updater.accept(juikit, graphics, field);
        }
    }

    public Size chooseSize(Juikit juikit) {
        if(size != null) {
            return size;
        } else if(delegateSize != null) {
            delegateSize = dynamicSize.apply(juikit, delegateSize);
            return delegateSize;
        }
        return empty;
    }

    public static class Builder {

        final TextField field = new TextField();

        Builder() {
            field.field.setOpaque(false);
        }

        public Builder id(Object id) {
            field.id = id;
            return this;
        }

        public Builder sizeFixed(int x, int y, int width, int height) {
            field.size = new Size();
            field.size.x = x;
            field.size.y = y;
            field.size.width = width;
            field.size.height = height;
            return this;
        }

        public Builder sizeDynamic(Function2d<Juikit, Size, Size> function) {
            field.delegateSize = new Size();
            field.dynamicSize = function;
            return this;
        }

        public Builder background(Image image) {
            field.image = image;
            return this;
        }

        public Builder background(Color color) {
            field.color = color;
            return this;
        }

        public Builder background(Consumer2d<Juikit, Graphics> painter) {
            field.painter = painter;
            return this;
        }

        public Builder text(String text) {
            field.initText = text;
            return this;
        }

        public Builder painter(Consumer3d<Juikit, Graphics, JTextField> painter) {
            field.updater = painter;
            return this;
        }

    }

}
