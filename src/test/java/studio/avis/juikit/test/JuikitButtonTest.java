package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;
import studio.avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitButtonTest {

    private static final byte MOUSE_X = 0;
    private static final byte MOUSE_Y = 1;
    private static final byte HANGING = 2;

    public static void main(String args[]) {
        Juikit.createFrame()
                .title("Juikit Button Test")
                .size(200, 200)
                .centerAlign()
                .background(Color.WHITE)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true)

                .data(MOUSE_X, 0)
                .data(MOUSE_Y, 0)
                .data(HANGING, false)

                .mouseMoved((juikit, mouseEvent) -> {
                    juikit.data(MOUSE_X, mouseEvent.getX());
                    juikit.data(MOUSE_Y, mouseEvent.getY());
                })
                .mouseDragged((juikit, mouseEvent) -> {
                    juikit.data(MOUSE_X, mouseEvent.getX());
                    juikit.data(MOUSE_Y, mouseEvent.getY());
                })

                // Fixed button
                .button(studio.avis.juikit.internal.Button.builder()
                                .priority(40)
                                .sizeFixed(25, 25, 25, 25)
                                .background(Color.RED)
                                .hover(Color.BLUE)
                                .press(Color.BLACK))

                // Center-aligned button
                .button(studio.avis.juikit.internal.Button.builder()
                                .id("CENTER_ALIGNED_BUTTON")
                                .priority(50)
                                .sizeDynamic((juikit, size) -> {
                                    int width = juikit.width();
                                    int height = juikit.height();

                                    size.x = width / 2 - 10;
                                    size.y = height / 2 - 10;
                                    size.width = 10;
                                    size.height = 10;
                                    return size;
                                })
                                .background(Color.RED)
                                .hover(Color.BLUE)
                                .press(Color.BLACK)
                                .processPressed((juikit, graphics) -> {
                                    System.out.println("Pressed.");
                                })
                                .processReleased((juikit, graphics) -> {
                                    System.out.println("Released.");
                                    juikit.removeButton("CENTER_ALIGNED_BUTTON");
                                })
                                .processWhile((juikit, graphics) -> {
                                    System.out.println("While");
                                }))

                // Draggable button
                .button(Button.builder()
                                .priority(1)
                                .sizeDynamic((juikit, size) -> {
                                    size.width = 30;
                                    size.height = 30;
                                    if(juikit.data(HANGING)) {
                                        int mouseX = juikit.data(MOUSE_X);
                                        int mouseY = juikit.data(MOUSE_Y);

                                        size.x = mouseX - size.width / 2;
                                        size.y = mouseY - size.height / 2;
                                    }
                                    return size;
                                })
                                .background(Color.RED)
                                .hover(Color.BLUE)
                                .press(Color.BLACK)
                                .processPressed((juikit, graphics) -> {
                                    juikit.data(HANGING, true);
                                })
                                .processReleased((juikit, graphics) -> {
                                    juikit.data(HANGING, false);
                                }))
                .visibility(true);
    }

}
