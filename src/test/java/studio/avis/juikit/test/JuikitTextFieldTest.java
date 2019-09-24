package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;
import studio.avis.juikit.internal.TextField;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JuikitTextFieldTest {

    private static final byte X = 0;
    private static final byte Y = 1;
    private static final byte WIDTH = 2;
    private static final byte HEIGHT = 3;
    private static final byte COLOR = 4;
    private static final byte TEXT = 5;

    private static final byte COLORS = 6;
    private static final byte STRINGS = 8;

    private static final byte RANDOM = 9;

    public static void main(String args[]) {
        Juikit.createFrame()
                .size(200, 200)
                .antialiasing(true)
                .resizable(false)
                .background(Color.WHITE)
                .repaintInterval(10)
                .centerAlign()
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)

                .data(X, 10)
                .data(Y, 10)
                .data(WIDTH, 200 - 20)
                .data(HEIGHT, 20)
                .data(COLOR, Color.YELLOW)
                .data(TEXT, "")
                .data(COLORS, Arrays.asList(Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE))
                .data(STRINGS, Arrays.asList("Peter", "Foo", "Bar", "Black"))
                .data(RANDOM, new Random())

                .textField(TextField.builder()
                                   .id("text1")
                                   .text("Init text")
                                   .background((juikit, graphics) -> {
                                       graphics.setColor(juikit.data(COLOR));
                                       graphics.fillRect(juikit.data(X), juikit.data(Y), juikit.data(WIDTH), juikit.data(HEIGHT));
                                   })
                                   .sizeDynamic((juikit, size) -> {
                                       size.x = juikit.data(X);
                                       size.y = juikit.data(Y);
                                       size.width = juikit.data(WIDTH);
                                       size.height = juikit.data(HEIGHT);
                                       return size;
                                   })
                                   .painter((juikit, graphics, jTextField) -> {
                                       jTextField.setText(juikit.data(TEXT));
                                       jTextField.revalidate();
                                       jTextField.repaint();
                                   }))

                // Button for changing bounds
                .button(studio.avis.juikit.internal.Button.builder()
                                .sizeFixed(100 - 5 - 20,  150, 10, 10)
                                .background(Color.RED)
                                .hover(Color.ORANGE)
                                .press(Color.YELLOW)
                                .processReleased((juikit, graphics) -> {
                                    int x = juikit.data(X);
                                    if(x == 10) {
                                        juikit.data(X, 20);
                                    } else {
                                        juikit.data(X, 10);
                                    }
                                }))

                // Button for changing color
                .button(studio.avis.juikit.internal.Button.builder()
                                .sizeFixed(100 - 5,  150, 10, 10)
                                .background(Color.RED)
                                .hover(Color.ORANGE)
                                .press(Color.YELLOW)
                                .processReleased((juikit, graphics) -> {
                                    List<Color> colors = juikit.data(COLORS);
                                    Random random = juikit.data(RANDOM);
                                    juikit.data(COLOR, colors.get(random.nextInt(colors.size())));
                                }))

                // Button for changing text
                .button(studio.avis.juikit.internal.Button.builder()
                                .sizeFixed(100 - 5 + 20,  150, 10, 10)
                                .background(Color.RED)
                                .hover(Color.ORANGE)
                                .press(Color.YELLOW)
                                .processReleased((juikit, graphics) -> {
                                    List<String> strings = juikit.data(STRINGS);
                                    Random random = juikit.data(RANDOM);
                                    juikit.data(TEXT, strings.get(random.nextInt(strings.size())));
                                }))
                .visibility(true);
    }

}
