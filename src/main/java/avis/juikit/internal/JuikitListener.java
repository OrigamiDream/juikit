package avis.juikit.internal;

import avis.juikit.Juikit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class JuikitListener {

    private final Juikit juikit;

    private JuikitConsumer<KeyEvent> keyTyped = JuikitConsumer.empty();
    private JuikitConsumer<KeyEvent> keyPressed = JuikitConsumer.empty();
    private JuikitConsumer<KeyEvent> keyReleased = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseClicked = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mousePressed = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseReleased = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseEntered = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseExited = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseDragged = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseMoved = JuikitConsumer.empty();
    private JuikitConsumer<MouseEvent> mouseWheelMoved = JuikitConsumer.empty();

    public JuikitListener(Juikit juikit) {
        this.juikit = juikit;
        this.juikit.keyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                keyTyped.acceptOptional(juikit, e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed.acceptOptional(juikit, e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyReleased.acceptOptional(juikit, e);
            }
        });
        this.juikit.mouseListener(new MouseListenerDelegate() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked.acceptOptional(juikit, e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed.acceptOptional(juikit, e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleased.acceptOptional(juikit, e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.acceptOptional(juikit, e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited.acceptOptional(juikit, e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDragged.acceptOptional(juikit, e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMoved.acceptOptional(juikit, e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseWheelMoved.acceptOptional(juikit, e);
            }
        });
    }

    public Juikit mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        this.mouseClicked = mouseClicked;
        return juikit;
    }

    public Juikit mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        this.mousePressed = mousePressed;
        return juikit;
    }

    public Juikit mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        this.mouseReleased = mouseReleased;
        return juikit;
    }

    public Juikit mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        this.mouseEntered = mouseEntered;
        return juikit;
    }

    public Juikit mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        this.mouseExited = mouseExited;
        return juikit;
    }

    public Juikit mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        this.mouseDragged = mouseDragged;
        return juikit;
    }

    public Juikit mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        this.mouseMoved = mouseMoved;
        return juikit;
    }

    public Juikit mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        this.mouseWheelMoved = mouseWheelMoved;
        return juikit;
    }

    public Juikit keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        this.keyTyped = keyTyped;
        return juikit;
    }

    public Juikit keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        this.keyPressed = keyPressed;
        return juikit;
    }

    public Juikit keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        this.keyReleased = keyReleased;
        return juikit;
    }

}
