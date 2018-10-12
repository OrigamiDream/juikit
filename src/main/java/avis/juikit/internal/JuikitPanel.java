package avis.juikit.internal;

import avis.juikit.Juikit;

import javax.swing.*;
import java.awt.*;

public class JuikitPanel extends JPanel {

    private final Juikit juikit;
    private Image backgroundImage;

    public JuikitPanel(Juikit juikit) {
        this.juikit = juikit;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
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
    }
}
