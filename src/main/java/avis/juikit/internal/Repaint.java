package avis.juikit.internal;

import avis.juikit.Juikit;

import java.awt.*;

@FunctionalInterface
public interface Repaint {

    void repaint(Juikit juikit, Graphics graphics);

}
