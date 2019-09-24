package studio.avis.juikit.internal;

import java.util.Objects;

public class Size {

    public int x;
    public int y;
    public int width;
    public int height;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        return x == size.x && y == size.y && width == size.width && height == size.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public String toString() {
        return "Size{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public Size copy() {
        Size size = new Size();
        size.x = x;
        size.y = y;
        size.width = width;
        size.height = height;
        return size;
    }
}
