package studio.avis.juikit.internal;

import studio.avis.juikit.Juikit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class JuikitListener {

    private final Juikit juikit;

    private List<JuikitConsumer<KeyEvent>> keyTyped = new ArrayList<>();
    private List<JuikitConsumer<KeyEvent>> keyPressed = new ArrayList<>();
    private List<JuikitConsumer<KeyEvent>> keyReleased = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseClicked = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mousePressed = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseReleased = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseEntered = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseExited = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseDragged = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseMoved = new ArrayList<>();
    private List<JuikitConsumer<MouseEvent>> mouseWheelMoved = new ArrayList<>();

    public JuikitListener(Juikit juikit) {
        this.juikit = juikit;
        this.juikit.keyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                keyTyped.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyReleased.forEach(k -> k.acceptOptional(juikit, e));
            }
        });
        this.juikit.mouseListener(new MouseListenerDelegate() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleased.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDragged.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMoved.forEach(k -> k.acceptOptional(juikit, e));
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseWheelMoved.forEach(k -> k.acceptOptional(juikit, e));
            }
        });
    }

    public Juikit mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        this.mouseClicked.add(mouseClicked);
        return juikit;
    }

    public Juikit mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        this.mousePressed.add(mousePressed);
        return juikit;
    }

    public Juikit mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        this.mouseReleased.add(mouseReleased);
        return juikit;
    }

    public Juikit mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        this.mouseEntered.add(mouseEntered);
        return juikit;
    }

    public Juikit mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        this.mouseExited.add(mouseExited);
        return juikit;
    }

    public Juikit mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        this.mouseDragged.add(mouseDragged);
        return juikit;
    }

    public Juikit mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        this.mouseMoved.add(mouseMoved);
        return juikit;
    }

    public Juikit mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        this.mouseWheelMoved.add(mouseWheelMoved);
        return juikit;
    }

    public Juikit keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        this.keyTyped.add(keyTyped);
        return juikit;
    }

    public Juikit keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        this.keyPressed.add(keyPressed);
        return juikit;
    }

    public Juikit keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        this.keyReleased.add(keyReleased);
        return juikit;
    }

}
