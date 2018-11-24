package avis.juikit;

import avis.juikit.internal.*;
import avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Juikit {

    private final JFrame frame;
    private final JuikitPanel panel;
    private final JuikitListener listener;

    private AtomicReference<Repaint> repaint = new AtomicReference<>(null);

    private AtomicBoolean REPAINT = new AtomicBoolean(false);
    private AtomicLong REPAINT_INTEVAL = new AtomicLong();

    private Map<Object, Object> storage = new ConcurrentHashMap<>();

    private AtomicBoolean antialiasing = new AtomicBoolean(false);
    
    private boolean size = false;

    private Juikit(JFrame frame) {
        this.frame = frame;
        this.panel = new JuikitPanel(this);
        this.frame.setContentPane(panel);
        this.listener = new JuikitListener(this);
    }

    public static Juikit createFrame() {
        return new Juikit(new JFrame());
    }

    public Juikit data(Object key, Object value) {
        storage.put(key, value);
        return this;
    }

    public Juikit removeData(Object key) {
        storage.remove(key);
        return this;
    }

    public Juikit ifData(Object key, Consumer3d<Juikit, Object, Object> consumer) {
        Object value = storage.get(key);
        if(value != null) {
            consumer.accept(this, key, value);
        }
        return this;
    }

    public Juikit ifNoData(Object key, Consumer2d<Juikit, Object> consumer) {
        Object value = storage.get(key);
        if(value == null) {
            consumer.accept(this, key);
        }
        return this;
    }

    public <T> T data(Object key) {
        return (T) storage.get(key);
    }

    public <T> T data(Object key, Class<T> type) {
        return data(key);
    }

    public Juikit title(String title) {
        frame.setTitle(title);
        return this;
    }
    
    public Juikit centerAlign() {
        if(!size) {
            throw new IllegalStateException("Size have to be defined first before invoke center align");
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width / 2 - width() / 2, dimension.height / 2 - height() / 2);
        return this;
    }

    public JFrame frame() {
        return frame;
    }

    public JuikitPanel panel() {
        return panel;
    }

    public boolean antialiasing() {
        return antialiasing.get();
    }

    public Juikit antialiasing(boolean antialiasing) {
        this.antialiasing.set(antialiasing);
        return this;
    }

    public Juikit resizable(boolean resizable) {
        frame.setResizable(resizable);
        return this;
    }

    public Juikit size(int width, int height) {
        size = true;
        frame.setSize(new Dimension(width, height));
        return this;
    }

    public int width() {
        return frame.getSize().width;
    }

    public int height() {
        return frame.getSize().height;
    }

    public Repaint painter() {
        return repaint.get();
    }

    public Juikit painter(Repaint repaint) {
        this.repaint.set(repaint);
        return this;
    }

    public Juikit repaint() {
        panel.repaint();
        return this;
    }

    public Juikit repaintInterval(long milliseconds) {
        REPAINT.set(true);
        REPAINT_INTEVAL.set(milliseconds);
        new Thread(() -> {
            while(REPAINT.get()) {
                try {
                    Thread.sleep(REPAINT_INTEVAL.get());
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();
        return this;
    }

    public Juikit stopRepainting() {
        REPAINT.set(false);
        REPAINT_INTEVAL.set(0);
        return this;
    }

    public Juikit closeOperation(int operation) {
        frame.setDefaultCloseOperation(operation);
        return this;
    }

    public boolean isVisible() {
        return frame.isVisible() && panel.isVisible();
    }

    public Juikit visibility(boolean visible) {
        frame.setVisible(visible);
        panel.setVisible(visible);
        return this;
    }

    public Juikit background(Color color) {
        panel.setBackground(color);
        return this;
    }

    public Juikit background(Image image) {
        panel.setBackgroundImage(image);
        return this;
    }

    public Juikit keyListener(KeyListener keyListener) {
        frame.addKeyListener(keyListener);
        return this;
    }

    public Juikit mouseListener(MouseListenerDelegate mouseListener) {
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseListener);
        panel.addMouseWheelListener(mouseListener);
        return this;
    }

    public Juikit button(Button.Builder builder) {
        panel.addButton(builder);
        return this;
    }

    public Juikit removeButton(Object id) {
        panel.removeButton(id);
        return this;
    }

    public Juikit clearButtons() {
        panel.clearButtons();
        return this;
    }

    public Juikit mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        return listener.mouseClicked(mouseClicked);
    }

    public Juikit mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        return listener.mousePressed(mousePressed);
    }

    public Juikit mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        return listener.mouseReleased(mouseReleased);
    }

    public Juikit mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        return listener.mouseEntered(mouseEntered);
    }

    public Juikit mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        return listener.mouseExited(mouseExited);
    }

    public Juikit mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        return listener.mouseDragged(mouseDragged);
    }

    public Juikit mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        return listener.mouseMoved(mouseMoved);
    }

    public Juikit mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        return listener.mouseWheelMoved(mouseWheelMoved);
    }

    public Juikit keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        return listener.keyTyped(keyTyped);
    }

    public Juikit keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        return listener.keyPressed(keyPressed);
    }

    public Juikit keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        return listener.keyReleased(keyReleased);
    }
}
