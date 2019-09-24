package studio.avis.juikit.internal;

import studio.avis.juikit.Juikit;

import java.awt.*;

@FunctionalInterface
public interface Repaint {

    void repaint(Juikit juikit, Graphics graphics);

}
