package avis.juikit;

import javax.swing.*;
import java.awt.*;

public class JuikitTest {

    public static void main(String args[]) {
        Juikit.createFrame()
                .size(100, 100)
                .background(Color.WHITE)
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .repaintInterval(10)
                .antialiasing(true)
                .data(1, 0)
                .data(2, false)
                .painter((jk, graphics) -> {
                    int width = jk.width();
                    int height = jk.height();
                    int index = jk.data(1);

                    if(index % 50 == 0) {
                        jk.data(2, true);
                        jk.data(3, 0);
                    }

                    if(jk.data(2)) {
                        int j = jk.data(3);
                        Color[] raindow = new Color[] {
                                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                                Color.CYAN, Color.BLUE, Color.MAGENTA
                        };

                        for(int i = 0; i < raindow.length; i++) {
                            graphics.setColor(raindow[i]);
                            graphics.fillRect(width / raindow.length * i,
                                              0, width / raindow.length, height);
                        }

                        if(j == 5) {
                            jk.removeData(3);
                            jk.data(2, false);
                        } else {
                            jk.data(3, j + 1);
                        }
                    }
                    jk.data(1, ++index);
                })
                .visibility(true);
    }

}
