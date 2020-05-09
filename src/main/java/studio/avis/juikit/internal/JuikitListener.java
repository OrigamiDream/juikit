package studio.avis.juikit.internal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class JuikitListener {

    private final JuikitView view;

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

    public JuikitListener(JuikitView view) {
        this.view = view;
        this.view.keyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                keyTyped.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyReleased.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }
        });
        this.view.mouseListener(new MouseListenerDelegate() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleased.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseExited.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDragged.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMoved.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseWheelMoved.forEach(k -> k.acceptOptional(JuikitListener.this.view, e));
            }
        });
    }

    public JuikitView mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        this.mouseClicked.add(mouseClicked);
        return view;
    }

    public JuikitView mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        this.mousePressed.add(mousePressed);
        return view;
    }

    public JuikitView mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        this.mouseReleased.add(mouseReleased);
        return view;
    }

    public JuikitView mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        this.mouseEntered.add(mouseEntered);
        return view;
    }

    public JuikitView mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        this.mouseExited.add(mouseExited);
        return view;
    }

    public JuikitView mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        this.mouseDragged.add(mouseDragged);
        return view;
    }

    public JuikitView mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        this.mouseMoved.add(mouseMoved);
        return view;
    }

    public JuikitView mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        this.mouseWheelMoved.add(mouseWheelMoved);
        return view;
    }

    public JuikitView keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        this.keyTyped.add(keyTyped);
        return view;
    }

    public JuikitView keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        this.keyPressed.add(keyPressed);
        return view;
    }

    public JuikitView keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        this.keyReleased.add(keyReleased);
        return view;
    }

}
