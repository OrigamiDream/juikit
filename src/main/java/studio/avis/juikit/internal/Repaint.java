package studio.avis.juikit.internal;

import studio.avis.juikit.Juikit;

import java.awt.*;

@FunctionalInterface
public interface Repaint {

    void repaint(JuikitView juikit, Graphics graphics);

}
