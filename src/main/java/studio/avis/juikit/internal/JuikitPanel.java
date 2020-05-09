package studio.avis.juikit.internal;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class JuikitPanel extends JPanel {

    private final JuikitView view;
    private Image backgroundImage;

    private final List<studio.avis.juikit.internal.Button> buttons = new CopyOnWriteArrayList<>();
    private final List<studio.avis.juikit.internal.TextField> textFields = new CopyOnWriteArrayList<>();

    private AtomicBoolean defaultPainter = new AtomicBoolean(false);

    public JuikitPanel(JuikitView view) {
        this.view = view;
    }

    public AtomicBoolean defaultPainter() {
        return defaultPainter;
    }

    public void setDefaultPainter(boolean defaultPainter) {
        this.defaultPainter.set(defaultPainter);
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void addButton(studio.avis.juikit.internal.Button.Builder button) {
        if(buttons.size() == 0) {
            view.mouseMoved((jk, mouseEvent) -> {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                for(int i = buttons.size() - 1; i >= 0; i--) {
                    studio.avis.juikit.internal.Button btn = buttons.get(i);
                    if(btn.isHoverable() && !btn.pressed) {
                        btn.hovered = btn.checkHover(jk, x, y);
                        if(btn.hovered) {
                            jk.repaint();
                        }
                    }
                }
            });
            view.mousePressed((jk, mouseEvent) -> {
                for(int i = buttons.size() - 1; i >= 0; i--) {
                    studio.avis.juikit.internal.Button btn = buttons.get(i);
                    if(btn.checkHover(jk, mouseEvent.getX(), mouseEvent.getY())) {
                        btn.deferredPressed = true;
                        btn.pressed = true;
                        jk.repaint();

                        if(btn.highPriorityOnly) {
                            break;
                        }
                    }
                }
            });
            view.mouseReleased((jk, mouseEvent) -> {
                for(int i = buttons.size() - 1; i >= 0; i--) {
                    studio.avis.juikit.internal.Button btn = buttons.get(i);
                    if(btn.pressed) {
                        btn.deferredReleased = true;
                    }
                    btn.pressed = false;
                    jk.repaint();
                }
            });
        }
        buttons.add(button.button);
        buttons.sort(Comparator.comparingInt(a -> a.priority));
    }

    public void removeButton(Object id) {
        buttons.removeIf(button -> button.id != null && button.id.equals(id));
    }

    public void clearButtons() {
        buttons.clear();
    }

    public void addTextField(studio.avis.juikit.internal.TextField.Builder builder) {
        JTextField field = new JTextField();
        studio.avis.juikit.internal.TextField textField = builder.field;
        if(textField.initText != null) {
            field.setText(textField.initText);
        }
        Size size = textField.chooseSize(view);
        field.setBounds(size.x, size.y, size.width, size.height);
        field.setSize(size.width, size.height);
        field.setVisible(true);

        textField.field = field;
        textField.prev = size.copy();

        textFields.add(textField);
        add(field);
    }

    public void removeTextField(Object id) {
        Predicate<studio.avis.juikit.internal.TextField> predicate = textField -> textField.id != null && textField.id.equals(id);
        textFields.stream().filter(predicate).map(studio.avis.juikit.internal.TextField::getField).forEach(this::remove);
        textFields.removeIf(predicate);
    }

    public void clearTextFields() {
        textFields.stream().map(studio.avis.juikit.internal.TextField::getField).forEach(this::remove);
        textFields.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(defaultPainter.get()) {
            super.paintComponent(g);
        } else {
            if(backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this);
            }

            if(view.beforePainter() != null) {
                view.beforePainter().repaint(view, g);
            }

            if(view.antialiasing()) {
                RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                ((Graphics2D) g).setRenderingHints(hints);
            }

            if(view.painter() != null) {
                view.painter().repaint(view, g);
            }

            for(Button button : buttons) {
                if(button.pressed) {
                    if(button.deferredPressed) {
                        button.deferredPressed = false;
                        button.activateBefore(view, g);
                    }
                    button.renderPress(view, g, this);
                    button.activateWhile(view, g);
                } else if(button.hovered) {
                    button.renderHover(view, g, this);
                } else {
                    button.renderDefault(view, g, this);
                }
                if(button.deferredReleased) {
                    button.deferredReleased = false;
                    button.activateAfter(view, g);
                }
            }

            for(TextField textField : textFields) {
                textField.renderDefault(view, g, this);
            }

            if(view.afterPainter() != null) {
                view.afterPainter().repaint(view, g);
            }
        }
    }
}
