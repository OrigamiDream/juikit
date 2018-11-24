package avis.juikit.internal;

import avis.juikit.Juikit;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class JuikitPanel extends JPanel {

    private final Juikit juikit;
    private Image backgroundImage;

    private final List<Button> buttons = new CopyOnWriteArrayList<>();
    private final List<TextField> textFields = new CopyOnWriteArrayList<>();

    public JuikitPanel(Juikit juikit) {
        this.juikit = juikit;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void addButton(Button.Builder button) {
        if(buttons.size() == 0) {
            juikit.mouseMoved((jk, mouseEvent) -> {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                for(Button btn : buttons) {
                    if(btn.isHoverable() && !btn.pressed) {
                        btn.hovered = btn.checkHover(jk, x, y);
                        if(btn.hovered) {
                            jk.repaint();
                        }
                    }
                }
            });
            juikit.mousePressed((jk, mouseEvent) -> {
                for(Button btn : buttons) {
                    if(btn.checkHover(jk, mouseEvent.getX(), mouseEvent.getY())) {
                        btn.deferredPressed = true;
                        btn.pressed = true;
                        jk.repaint();
                    }
                }
            });
            juikit.mouseReleased((jk, mouseEvent) -> {
                for(Button btn : buttons) {
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

    public void addTextField(TextField.Builder builder) {
        JTextField field = new JTextField();
        TextField textField = builder.field;
        if(textField.initText != null) {
            field.setText(textField.initText);
        }
        Size size = textField.chooseSize(juikit);
        field.setBounds(size.x, size.y, size.width, size.height);
        field.setSize(size.width, size.height);
        field.setVisible(true);

        textField.field = field;
        textField.prev = size.copy();

        textFields.add(textField);
        add(field);
    }

    public void removeTextField(Object id) {
        Predicate<TextField> predicate = textField -> textField.id != null && textField.id.equals(id);
        textFields.stream().filter(predicate).map(TextField::getField).forEach(this::remove);
        textFields.removeIf(predicate);
    }

    public void clearTextFields() {
        textFields.stream().map(TextField::getField).forEach(this::remove);
        textFields.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }

        if(juikit.antialiasing()) {
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                      RenderingHints.VALUE_ANTIALIAS_ON);

            ((Graphics2D) g).setRenderingHints(hints);
        }

        if(juikit.painter() != null) {
            juikit.painter().repaint(juikit, g);
        }

        for(Button button : buttons) {
            if(button.pressed) {
                if(button.deferredPressed) {
                    button.deferredPressed = false;
                    button.activateBefore(juikit, g);
                }
                button.renderPress(juikit, g, this);
                button.activateWhile(juikit, g);
            } else if(button.hovered) {
                button.renderHover(juikit, g, this);
            } else {
                button.renderDefault(juikit, g, this);
            }
            if(button.deferredReleased) {
                button.deferredReleased = false;
                button.activateAfter(juikit, g);
            }
        }

        for(TextField textField : textFields) {
            textField.renderDefault(juikit, g, this);
        }
    }
}
