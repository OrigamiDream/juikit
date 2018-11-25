package avis.juikit.test;

import avis.juikit.Juikit;
import avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitOverlayButtonTest {

    private static final int INIT_BUTTON_WIDTH = 100;
    private static final int INIT_BUTTON_HEIGHT = 200 / 5;

    private static final String[] TEXTS = { "Foo", "Bar", "Forum", "Contact", "Play now" };

    public static void main(String args[]) {
        Juikit juikit = Juikit.createFrame()
                .title("Juikit Overlay Button Test")
                .size(200, 250)
                .centerAlign()
                .background(Color.WHITE)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true);

        for(int i = 0; i < 5; i++) {
            int x = 0;
            int y = i * INIT_BUTTON_HEIGHT;
            int width = INIT_BUTTON_WIDTH;
            int height = INIT_BUTTON_HEIGHT;

            int finalI = i;

            juikit.data(finalI, 0d);
            juikit.button(Button.builder()
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
