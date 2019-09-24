package studio.avis.juikit.test;

import studio.avis.juikit.Juikit;
import studio.avis.juikit.internal.Button;

import javax.swing.*;
import java.awt.*;

public class JuikitMenuButtonTest {

    private static final int INIT_BUTTON_WIDTH = 50;
    private static final int INIT_BUTTON_HEIGHT = 200 / 5;

    public static void main(String args[]) {
        Juikit juikit = Juikit.createFrame()
                .title("Juikit Menu Button Test")
                .size(200, 200)
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

            studio.avis.juikit.internal.Button.Builder builder = Button.builder()
                    .id(finalI)
                    .sizeDynamic((jk, size) -> {
                        size.x = x;
                        size.y = y;
                        size.width = width;
                        size.height = height;
                        return size;
                    })
                    .background((jk, graphics) -> {
                        int expand = jk.data(finalI);
                        if(--expand < 0) {
                            expand = 0;
                        }
                        jk.data(finalI, expand);
                        graphics.setColor(new Color(Integer.valueOf("0abde3", 16)));
                        graphics.fillRect(x, y, width + expand, height);
                    })
                    .hover((jk, graphics) -> {
                        int expand = jk.data(finalI);
                        if(++expand > 50) {
                            expand = 50;
                        }
                        jk.data(finalI, expand);
                        graphics.setColor(new Color(Integer.valueOf("48dbfb", 16)));
                        graphics.fillRect(x, y, width + expand, height);
                    })
                    .press((jk, graphics) -> {
                        int expand = jk.data(finalI);
                        if(++expand > 100) {
                            expand = 100;
                        }
                        jk.data(finalI, expand);
                        graphics.setColor(new Color(Integer.valueOf("54a0ff", 16)));
                        graphics.fillRect(x, y, width + expand, height);
                    });

            juikit.data(finalI, 0).button(builder);
        }

        juikit.visibility(true);
    }

}
