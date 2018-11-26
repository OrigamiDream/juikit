package avis.juikit.test;

import avis.juikit.Juikit;
import avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitLayoutTest {

    private static final byte LAYER_0 = 0;
    private static final byte LAYER_1 = 1;
    private static final byte FADING = 2;
    private static final byte DIRECTION = 3;

    private static final int FADING_SPEED = 8;

    public static void main(String args[]) {
        Juikit.createFrame()
                .size(500, 500)
                .centerAlign()
                .title("Juikit Layout Test")
                .background(Color.LIGHT_GRAY)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true)

                .data(LAYER_0, 0)
                .data(LAYER_1, 100)
                .data(FADING, false)
                .data(DIRECTION, 0)

                .painter((juikit, graphics) -> {
                    if(juikit.data(FADING)) {
                        int direction = juikit.data(DIRECTION);
                        int layer0 = juikit.data(LAYER_0);
                        int layer1 = juikit.data(LAYER_1);
                        if(direction == 0) {
                            // layer_0 | 0 -> 100
                            // layer_1 | 100 -> 0
                            juikit.data(LAYER_0, Math.min(100, layer0 + FADING_SPEED));
                            juikit.data(LAYER_1, Math.max(0, layer1 - FADING_SPEED));
                            if(layer0 + FADING_SPEED >= 100 || layer1 - FADING_SPEED <= 0) {
                                juikit.data(FADING, false);
                                juikit.data(DIRECTION, 1);
                            }
                        } else {
                            // layer_0 | 100 -> 0
                            // layer_1 | 0 -> 100
                            juikit.data(LAYER_0, Math.max(0, layer0 - FADING_SPEED));
                            juikit.data(LAYER_1, Math.min(100, layer1 + FADING_SPEED));
                            if(layer0 - FADING_SPEED <= 0 || layer1 + FADING_SPEED >= 100) {
                                juikit.data(FADING, false);
                                juikit.data(DIRECTION, 0);
                            }
                        }
                    }

                    int cX = juikit.width() / 2;
                    int cY = juikit.height() / 2;

                    // layer #0
                    {
                        int layer0 = juikit.data(LAYER_0);
                        for(int x = 0; x < 9; x++) {
                            for(int y = 0; y < 9; y++) {
                                int pX = x * 50 + 30;
                                int pY = y * 50 + 30;

                                int diffX = pX - cX;
                                int diffY = pY - cY;

                                diffX *= (double) layer0 * 0.01;
                                diffY *= (double) layer0 * 0.01;

                                graphics.setColor(new Color(64, 64, 64, 100 - layer0));
                                graphics.fillRect(pX + diffX, pY + diffY, 25, 25);
                            }
                        }
                    }

                    // layer #1
                    {
                        int layer1 = juikit.data(LAYER_1);
                        for(int x = 1; x < 4; x++) {
                            for(int y = 3; y < 6; y++) {
                                int pX = x * 70 + 30;
                                int pY = y * 70 + 30;

                                int diffX = pX - cX;
                                int diffY = pY - cY;

                                diffX *= (double) layer1 * 0.01;
                                diffY *= (double) layer1 * 0.01;

                                graphics.setColor(new Color(64, 64, 64, 100 - layer1));
                                graphics.fillRect(pX + diffX, pY + diffY, 40, 40);
                            }
                        }
                    }
                })

                .button(Button.builder()
                .sizeFixed(0, 450, 100, 50)
                .background(Color.GREEN)
                .hover(Color.RED)
                .press(Color.BLUE)
                .processReleased((juikit, graphics) -> {
                    juikit.data(FADING, true);
                }))
                .visibility(true);

    }

}
