package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;
import studio.avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitIntroTest {

    private static final byte LOADING_TIMEOUT = 0;
    private static final byte LOADING = 1;
    private static final byte OPACITY = 2;
    private static final byte CHANGE = 3;
    private static final byte DONE = 4;

    private static final Color[] COLORS = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };

    public static void main(String args[]) {
        Juikit.createFrame()
                .size(1000, 1000)
                .centerAlign()
                .title("Juikit Intro")
                .background(Color.WHITE)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true)
                .visibility(true)

                .data(LOADING_TIMEOUT, System.currentTimeMillis())
                .data(LOADING, 0)
                .data(OPACITY, 0)
                .data(CHANGE, false)
                .data(DONE, true)

                .afterPainter((juikit, graphics) -> {
                    graphics.setColor(Color.MAGENTA);
                    graphics.fillRect(250, 250, 500, 500);
                    if(!juikit.data(DONE, Boolean.class)) {
                        long time = juikit.data(LOADING_TIMEOUT);
                        displayLoading(juikit, graphics, juikit.data(CHANGE, Boolean.class) && System.currentTimeMillis() - time < 3000L, () -> {
                            juikit.data(CHANGE, false);
                        });
                    }
                })

                .button(Button.builder()
                                .sizeFixed(0, 500, 500, 100)
                                .background(Color.LIGHT_GRAY)
                                .hover(Color.GRAY)
                                .press(Color.DARK_GRAY)
                                .processReleased((juikit, graphics) -> {
                                    juikit.data(DONE, false);
                                    juikit.data(CHANGE, true);
                                    juikit.data(LOADING_TIMEOUT, System.currentTimeMillis());
                                }));
    }

    public static void initIntro(Juikit juikit) {
        juikit.data(LOADING_TIMEOUT, System.currentTimeMillis()).data(LOADING, 0).data(OPACITY, 0).data(CHANGE, false).data(DONE, true);
    }

    public static void startDimming(Juikit juikit) {
        juikit.data(DONE, false);
        juikit.data(CHANGE, true);
        juikit.data(LOADING_TIMEOUT, System.currentTimeMillis());
    }

    public static void displayLoading(Juikit juikit, Graphics graphics, boolean running, Runnable callback) {
        int centerX = juikit.width() / 2;
        int centerY = juikit.height() / 2;

        int loading = juikit.data(LOADING);
        int opacity = juikit.data(OPACITY);

        boolean done = false;

        if(running) {
            opacity = Math.min(255, opacity + 7);
        } else {
            if(opacity == 255) {
                callback.run();
            }
            opacity = Math.max(0, opacity - 7);
            if(opacity == 0) {
                juikit.data(DONE, true);
                loading = 0;
                done = true;
            }
        }
        graphics.setColor(new Color(255, 255, 255, opacity));
        graphics.fillRect(0, 0, juikit.width(), juikit.height());

        int i = loading / 20;
        Color color = COLORS[i % COLORS.length];
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), running ? 255 : opacity);
        graphics.setColor(newColor);
        graphics.fillRect(centerX - 10, centerY - 10, 20, 20);

        if(!done && running) {
            loading++;
        }

        juikit.data(LOADING, loading);
        juikit.data(OPACITY, opacity);
    }

}
