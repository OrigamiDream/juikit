package studio.avis.juikit.internal;

import studio.avis.juikit.Juikit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class JuikitView {

    private final Juikit juikit;
    private final JuikitPanel panel;
    private final JuikitListener listener;
    private final List<JTextField> textFields = new ArrayList<>();

    private final AtomicReference<Repaint> beforePainter = new AtomicReference<>(null);
    private final AtomicReference<Repaint> repaint = new AtomicReference<>(null);
    private final AtomicReference<Repaint> afterPainter = new AtomicReference<>(null);

    private final AtomicBoolean REPAINT = new AtomicBoolean(false);
    private final AtomicLong REPAINT_INTEVAL = new AtomicLong();

    private final AtomicBoolean antialiasing = new AtomicBoolean(false);

    public JuikitView(Juikit juikit) {
        this.juikit = juikit;
        this.panel = new JuikitPanel(this);
        this.listener = new JuikitListener(this);
        this.panel.setLayout(null);
    }

    public JuikitView defaultPainter(boolean defaultPainter) {
        panel.setDefaultPainter(defaultPainter);
        return this;
    }

    public JFrame frame() {
        return juikit.frame();
    }

    public JuikitView data(Object key, Object value) {
        juikit.data(key, value);
        return this;
    }

    public JuikitView removeData(Object key) {
        juikit.removeData(key);
        return this;
    }

    public JuikitView ifData(Object key, TernaryConsumer<Juikit, Object, Object> consumer) {
        juikit.ifData(key, consumer);
        return this;
    }

    public JuikitView ifNoData(Object key, BiConsumer<Juikit, Object> consumer) {
        juikit.ifNoData(key, consumer);
        return this;
    }

    public <T> T data(Object key) {
        return juikit.data(key);
    }

    public <T> T data(Object key, Class<T> type) {
        return data(key);
    }

    public Juikit juikit() {
        return juikit;
    }

    public JuikitPanel panel() {
        return panel;
    }

    public JuikitView layout(Function<JuikitView, LayoutManager> function) {
        this.panel.setLayout(function.apply(this));
        return this;
    }

    public JuikitView layout(LayoutManager layout) {
        this.panel.setLayout(layout);
        return this;
    }

    public LayoutManager layout() {
        return panel.getLayout();
    }

    public JuikitView newPanel(BiConsumer<JuikitView, JuikitView> consumer) {
        JuikitView view = new JuikitView(juikit);
        this.panel.add(view.panel());
        consumer.accept(this, view);
        return this;
    }

    public boolean antialiasing() {
        return antialiasing.get();
    }

    public JuikitView antialiasing(boolean antialiasing) {
        this.antialiasing.set(antialiasing);
        return this;
    }

    public JuikitView size(int width, int height) {
        panel.setSize(new Dimension(width, height));
        panel.setPreferredSize(panel.getSize());
        return this;
    }

    public int width() {
        return panel.getSize().width;
    }

    public int height() {
        return panel.getSize().height;
    }

    public Repaint beforePainter() {
        return beforePainter.get();
    }

    public JuikitView beforePainter(Repaint repaint) {
        this.beforePainter.set(repaint);
        return this;
    }

    public Repaint afterPainter() {
        return afterPainter.get();
    }

    public JuikitView afterPainter(Repaint repaint) {
        this.afterPainter.set(repaint);
        return this;
    }

    public Repaint painter() {
        return repaint.get();
    }

    public JuikitView painter(Repaint repaint) {
        this.repaint.set(repaint);
        return this;
    }

    public JuikitView repaint() {
        panel.repaint();
        return this;
    }

    public JuikitView repaintInterval(long milliseconds) {
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

    public JuikitView stopRepainting() {
        REPAINT.set(false);
        REPAINT_INTEVAL.set(0);
        return this;
    }

    public boolean isVisible() {
        return panel.isVisible();
    }

    public JuikitView visibility(boolean visible) {
        panel.setVisible(visible);
        return this;
    }

    public JuikitView background(Color color) {
        panel.setBackground(color);
        return this;
    }

    public JuikitView background(Image image) {
        panel.setBackgroundImage(image);
        return this;
    }

    public JuikitView keyListener(KeyListener keyListener) {
        panel.addKeyListener(keyListener);
        return this;
    }

    public JuikitView mouseListener(MouseListenerDelegate mouseListener) {
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseListener);
        panel.addMouseWheelListener(mouseListener);
        return this;
    }

    public JuikitView textField(String initText, int x, int y, int width, int height) {
        JTextField field = new JTextField();
        if(initText != null) {
            field.setText(initText);
        }
        field.setBounds(x, y, width, height);
        field.setSize(width, height);
        field.setVisible(true);

        textFields.add(field);
        panel().add(field);

        return this;
    }

    public JuikitView textField(TextField.Builder builder) {
        panel.addTextField(builder);
        return this;
    }

    public JuikitView removeTextField(Object id) {
        panel.removeTextField(id);
        return this;
    }

    public JuikitView clearTextField() {
        panel.clearTextFields();
        return this;
    }

    public JuikitView button(Button.Builder builder) {
        panel.addButton(builder);
        return this;
    }

    public JuikitView removeButton(Object id) {
        panel.removeButton(id);
        return this;
    }

    public JuikitView clearButtons() {
        panel.clearButtons();
        return this;
    }

    public JuikitView mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        return listener.mouseClicked(mouseClicked);
    }

    public JuikitView mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        return listener.mousePressed(mousePressed);
    }

    public JuikitView mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        return listener.mouseReleased(mouseReleased);
    }

    public JuikitView mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        return listener.mouseEntered(mouseEntered);
    }

    public JuikitView mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        return listener.mouseExited(mouseExited);
    }

    public JuikitView mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        return listener.mouseDragged(mouseDragged);
    }

    public JuikitView mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        return listener.mouseMoved(mouseMoved);
    }

    public JuikitView mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        return listener.mouseWheelMoved(mouseWheelMoved);
    }

    public JuikitView keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        return listener.keyTyped(keyTyped);
    }

    public JuikitView keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        return listener.keyPressed(keyPressed);
    }

    public JuikitView keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        return listener.keyReleased(keyReleased);
    }

}
