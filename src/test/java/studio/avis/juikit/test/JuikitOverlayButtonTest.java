package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;
import studio.avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitOverlayButtonTest {

    private static final int INIT_BUTTON_WIDTH = 100;
    private static final int INIT_BUTTON_HEIGHT = 200 / 5;

    private static final String[] TEXTS = { "Foo", "Bar", "Forum", "Contact", "Play now" };

    private static final byte PREV_MOUSE_X = 0;
    private static final byte PREV_MOUSE_Y = 1;
    private static final byte MOVING = 2;

    public static void main(String args[]) {
        Juikit juikit = Juikit.createFrame()
                .title("Juikit Overlay Button Test")
                .size(200, 250)
                .centerAlign()
                .background(Color.WHITE)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true)
                .alwaysOnTop(true)
                .undecorated(true)

                .data(PREV_MOUSE_X, 0)
                .data(PREV_MOUSE_Y, 0)
                .data(MOVING, false)

                .mouseMoved((jk, mouseEvent) -> {
                    int x = mouseEvent.getXOnScreen();
                    int y = mouseEvent.getYOnScreen();
                    if(jk.data(MOVING)) {
                        int prevX = jk.data(PREV_MOUSE_X);
                        int prevY = jk.data(PREV_MOUSE_Y);

                        int diffX = x - prevX;
                        int diffY = y - prevY;
                        jk.frame().setLocation(jk.frame().getX() + diffX, jk.frame().getY() + diffY);
                    }

                    jk.data(PREV_MOUSE_X, x);
                    jk.data(PREV_MOUSE_Y, y);
                })
                .mouseDragged((jk, mouseEvent) -> {
                    int x = mouseEvent.getXOnScreen();
                    int y = mouseEvent.getYOnScreen();
                    if(jk.data(MOVING)) {
                        int prevX = jk.data(PREV_MOUSE_X);
                        int prevY = jk.data(PREV_MOUSE_Y);

                        int diffX = x - prevX;
                        int diffY = y - prevY;
                        jk.frame().setLocation(jk.frame().getX() + diffX, jk.frame().getY() + diffY);
                    }

                    jk.data(PREV_MOUSE_X, x);
                    jk.data(PREV_MOUSE_Y, y);
                })
                .mousePressed((jk, mouseEvent) -> {
                    jk.data(MOVING, true);
                })
                .mouseReleased((jk, mouseEvent) -> {
                    jk.data(MOVING, false);
                });

        for(int i = 0; i < 5; i++) {
            int x = 0;
            int y = i * INIT_BUTTON_HEIGHT;
            int width = INIT_BUTTON_WIDTH;
            int height = INIT_BUTTON_HEIGHT;

            int finalI = i;

            juikit.data(finalI, 0d);
            juikit.button(studio.avis.juikit.internal.Button.builder()
                                  .priority(2)
                                  .sizeDynamic((jk, size) -> {
                                      size.x = x;
                                      size.y = y;
                                      size.width = width;
                                      size.height = height;
                                      return size;
                                  })
                                  .background((jk, graphics) -> {
                                      graphics.setColor(new Color(Integer.valueOf("0abde3", 16)));
                                      graphics.fillRect(x, y, ((Double) jk.data(finalI)).intValue(), height);
                                  })
                                  .hover((jk, graphics) -> {
                                      graphics.setColor(new Color(Integer.valueOf("48dbfb", 16)));
                                      graphics.fillRect(x, y, ((Double) jk.data(finalI)).intValue(), height);
                                  })
                                  .press((jk, graphics) -> {
                                      graphics.setColor(new Color(Integer.valueOf("54a0ff", 16)));
                                      graphics.fillRect(x, y, INIT_BUTTON_WIDTH, height);
                                  }));

            juikit.button(Button.builder()
                                  .priority(1)
                                  .sizeDynamic((jk, size) -> {
                                      size.x = x;
                                      size.y = y;
                                      size.width = width;
                                      size.height = height;
                                      return size;
                                  })
                                  .background((jk, graphics) -> {
                                      double expand = jk.data(finalI);
                                      if((expand = (Math.max(expand, 1)) / 1.1) < 0) {
                                          expand = 0;
                                      }
                                      jk.data(finalI, expand);
                                      graphics.setColor(Color.LIGHT_GRAY);
                                      graphics.fillRect(x, y, width, height);
                                  })
                                  .hover((jk, graphics) -> {
                                      double expand = jk.data(finalI);
                                      if((expand = (Math.max(expand, 1)) * 1.1) > INIT_BUTTON_WIDTH) {
                                          expand = INIT_BUTTON_WIDTH;
                                      }
                                      jk.data(finalI, expand);
                                      graphics.setColor(Color.LIGHT_GRAY);
                                      graphics.fillRect(x, y, width, height);

                                      graphics.setColor(Color.BLACK);
                                      graphics.drawString(TEXTS[finalI], x + INIT_BUTTON_WIDTH + 10, y + (INIT_BUTTON_HEIGHT / 2) + 4);
                                  })
                                  .press((jk, graphics) -> {
                                      double expand = jk.data(finalI);
                                      if((expand = (Math.max(expand, 1)) * 1.1) > INIT_BUTTON_WIDTH) {
                                          expand = INIT_BUTTON_WIDTH;
                                      }
                                      jk.data(finalI, expand);
                                      graphics.setColor(Color.LIGHT_GRAY);
                                      graphics.fillRect(x, y, width, height);
                                  }));
        }

        juikit.visibility(true);
    }
}
