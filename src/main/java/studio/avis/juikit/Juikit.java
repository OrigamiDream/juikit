package studio.avis.juikit;

import studio.avis.juikit.internal.*;
import studio.avis.juikit.internal.Button;
import studio.avis.juikit.internal.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Juikit {

    private static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();

    private final JFrame frame;
    private final JuikitView legacyView;

    private Map<Object, Object> storage = new ConcurrentHashMap<>();
    
    private boolean size = false;

    private Juikit(JFrame frame) {
        this.frame = frame;
        this.legacyView = new JuikitView(this);
        this.frame.setContentPane(legacyView.panel());
    }

    public boolean macOS() {
        return OPERATING_SYSTEM.contains("mac");
    }

    public boolean unix() {
        return OPERATING_SYSTEM.contains("nix") || OPERATING_SYSTEM.contains("nux") || OPERATING_SYSTEM.contains("aix");
    }

    public boolean solaris() {
        return OPERATING_SYSTEM.contains("sunos");
    }

    public boolean windows() {
        return OPERATING_SYSTEM.contains("win");
    }

    public static Juikit createFrame() {
        return new Juikit(new JFrame());
    }

    public Juikit dockIcon(Image image) {
        if(!macOS()) {
            return this;
        }
        try {
            Class<?> clazz = Class.forName("com.apple.eawt.Application");
            Method getApplication = clazz.getDeclaredMethod("getApplication");
            getApplication.setAccessible(true);
            Object application = getApplication.invoke(null);

            Class<?> applicationType = application.getClass();
            Method setDockIconImage = applicationType.getDeclaredMethod("setDockIconImage", Image.class);
            setDockIconImage.setAccessible(true);
            setDockIconImage.invoke(application, image);
        } catch (Exception ignored) {
        }
        return this;
    }

    public Juikit layout(Function<Juikit, LayoutManager> function) {
        panel().setLayout(function.apply(this));
        return this;
    }

    public Juikit layout(LayoutManager layout) {
        panel().setLayout(layout);
        return this;
    }

    public LayoutManager layout() {
        return panel().getLayout();
    }

    public Juikit dockIcon(String path) {
        return dockIcon(new ImageIcon(path).getImage());
    }

    public Juikit dockIcon(URL url) {
        return dockIcon(new ImageIcon(url).getImage());
    }

    public Juikit dockIcon(byte[] imageData) {
        return dockIcon(new ImageIcon(imageData).getImage());
    }

    public Juikit icon(Image image) {
        frame().setIconImage(image);
        return this;
    }

    public Juikit icon(String path) {
        return icon(new ImageIcon(path).getImage());
    }

    public Juikit icon(URL url) {
        return icon(new ImageIcon(url).getImage());
    }

    public Juikit icon(byte[] imageData) {
        return icon(new ImageIcon(imageData).getImage());
    }

    public Juikit data(Object key, Object value) {
        storage.put(key, value);
        return this;
    }

    public Juikit removeData(Object key) {
        storage.remove(key);
        return this;
    }

    public Juikit ifData(Object key, TernaryConsumer<Juikit, Object, Object> consumer) {
        Object value = storage.get(key);
        if(value != null) {
            consumer.accept(this, key, value);
        }
        return this;
    }

    public Juikit ifNoData(Object key, BiConsumer<Juikit, Object> consumer) {
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

    public Juikit prefer(BiConsumer<Juikit, JFrame> consumer) {
        consumer.accept(this, frame);
        return this;
    }

    public Juikit undecorated(boolean undecorated) {
        frame.setUndecorated(undecorated);
        return this;
    }

    public boolean undecorated() {
        return frame.isUndecorated();
    }

    public Juikit alwaysOnTop(boolean alwaysOnTop) {
        frame.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    public boolean alwaysOnTop() {
        return frame.isAlwaysOnTop();
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

    public Juikit defaultPainter(boolean defaultPainter) {
        legacyView.defaultPainter(defaultPainter);
        return this;
    }

    public Juikit newPanel(BiConsumer<Juikit, JuikitView> consumer) {
        JuikitView view = new JuikitView(this);
        consumer.accept(this, view);
        legacyView.panel().add(view.panel());
        return this;
    }

    @Deprecated
    public JuikitPanel panel() {
        return legacyView.panel();
    }

    @Deprecated
    public boolean antialiasing() {
        return legacyView.antialiasing();
    }

    @Deprecated
    public Juikit antialiasing(boolean antialiasing) {
        legacyView.antialiasing(antialiasing);
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

    public Juikit closeOperation(int operation) {
        frame.setDefaultCloseOperation(operation);
        return this;
    }

    @Deprecated
    public Repaint beforePainter() {
        return legacyView.beforePainter();
    }

    @Deprecated
    public Juikit beforePainter(Repaint repaint) {
        legacyView.beforePainter(repaint);
        return this;
    }

    @Deprecated
    public Repaint afterPainter() {
        return legacyView.afterPainter();
    }

    @Deprecated
    public Juikit afterPainter(Repaint repaint) {
        legacyView.afterPainter(repaint);
        return this;
    }

    @Deprecated
    public Repaint painter() {
        return legacyView.painter();
    }

    @Deprecated
    public Juikit painter(Repaint repaint) {
        legacyView.painter(repaint);
        return this;
    }

    public Juikit repaint() {
        legacyView.repaint();
        return this;
    }

    @Deprecated
    public Juikit repaintInterval(long milliseconds) {
        legacyView.repaintInterval(milliseconds);
        return this;
    }

    @Deprecated
    public Juikit stopRepainting() {
        legacyView.stopRepainting();
        return this;
    }

    @Deprecated
    public boolean isVisible() {
        return frame.isVisible() && legacyView.isVisible();
    }

    public Juikit visibility(boolean visible) {
        frame.setVisible(visible);
        legacyView.visibility(visible);
        return this;
    }

    public Juikit pack() {
        frame.pack();
        return this;
    }

    @Deprecated
    public Juikit background(Color color) {
        legacyView.background(color);
        return this;
    }

    @Deprecated
    public Juikit background(Image image) {
        legacyView.background(image);
        return this;
    }

    @Deprecated
    public Juikit keyListener(KeyListener keyListener) {
        legacyView.keyListener(keyListener);
        return this;
    }

    @Deprecated
    public Juikit mouseListener(MouseListenerDelegate mouseListener) {
        legacyView.mouseListener(mouseListener);
        return this;
    }

    @Deprecated
    public Juikit textField(String initText, int x, int y, int width, int height) {
        legacyView.textField(initText, x, y, width, height);
        return this;
    }

    @Deprecated
    public Juikit textField(TextField.Builder builder) {
        legacyView.textField(builder);
        return this;
    }

    @Deprecated
    public Juikit removeTextField(Object id) {
        legacyView.removeTextField(id);
        return this;
    }

    @Deprecated
    public Juikit clearTextField() {
        legacyView.clearTextField();
        return this;
    }

    @Deprecated
    public Juikit button(Button.Builder builder) {
        legacyView.button(builder);
        return this;
    }

    @Deprecated
    public Juikit removeButton(Object id) {
        legacyView.removeButton(id);
        return this;
    }

    @Deprecated
    public Juikit clearButtons() {
        legacyView.clearButtons();
        return this;
    }

    @Deprecated
    public Juikit mouseClicked(JuikitConsumer<MouseEvent> mouseClicked) {
        legacyView.mouseClicked(mouseClicked);
        return this;
    }

    @Deprecated
    public Juikit mousePressed(JuikitConsumer<MouseEvent> mousePressed) {
        legacyView.mousePressed(mousePressed);
        return this;
    }

    @Deprecated
    public Juikit mouseReleased(JuikitConsumer<MouseEvent> mouseReleased) {
        legacyView.mouseReleased(mouseReleased);
        return this;
    }

    @Deprecated
    public Juikit mouseEntered(JuikitConsumer<MouseEvent> mouseEntered) {
        legacyView.mouseEntered(mouseEntered);
        return this;
    }

    @Deprecated
    public Juikit mouseExited(JuikitConsumer<MouseEvent> mouseExited) {
        legacyView.mouseExited(mouseExited);
        return this;
    }

    @Deprecated
    public Juikit mouseDragged(JuikitConsumer<MouseEvent> mouseDragged) {
        legacyView.mouseDragged(mouseDragged);
        return this;
    }

    @Deprecated
    public Juikit mouseMoved(JuikitConsumer<MouseEvent> mouseMoved) {
        legacyView.mouseMoved(mouseMoved);
        return this;
    }

    @Deprecated
    public Juikit mouseWheelMoved(JuikitConsumer<MouseEvent> mouseWheelMoved) {
        legacyView.mouseWheelMoved(mouseWheelMoved);
        return this;
    }

    @Deprecated
    public Juikit keyTyped(JuikitConsumer<KeyEvent> keyTyped) {
        legacyView.keyTyped(keyTyped);
        return this;
    }

    @Deprecated
    public Juikit keyPressed(JuikitConsumer<KeyEvent> keyPressed) {
        legacyView.keyPressed(keyPressed);
        return this;
    }

    @Deprecated
    public Juikit keyReleased(JuikitConsumer<KeyEvent> keyReleased) {
        legacyView.keyReleased(keyReleased);
        return this;
    }
}
