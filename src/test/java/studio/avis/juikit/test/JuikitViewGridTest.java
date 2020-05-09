package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;

import javax.swing.*;
import java.awt.*;

public class JuikitViewGridTest {

    public static void main(String[] args) {
        Juikit.createFrame()
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .defaultPainter(true)
                .layout(juikit -> {
                    FlowLayout layout = new FlowLayout();
                    layout.setHgap(0);
                    layout.setVgap(0);
                    layout.setAlignment(FlowLayout.CENTER);
                    return layout;
                })
                .newPanel((juikit, view) -> {
                    view.size(100, 100)
                            .defaultPainter(true)
                            .background(Color.RED)
                            .visibility(true);
                })
                .newPanel((juikit, view) -> {
                    view.size(100, 100)
                            .defaultPainter(true)
                            .background(Color.BLUE)
                            .visibility(true);
                })
                .newPanel((juikit, view) -> {
                    view.size(100, 100)
                            .defaultPainter(true)
                            .background(Color.YELLOW)
                            .visibility(true);
                })
                .newPanel((juikit, view) -> {
                    view.size(100, 100)
                            .defaultPainter(true)
                            .background(Color.GREEN)
                            .visibility(true);
                })
                .size(400, 300)
                .centerAlign()
                .title("JuikitViewGridTest")
                .visibility(true)
                .repaint();
    }

}
