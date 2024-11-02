package shape;

import java.awt.*;

public abstract class Shape {

    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public Graphics g;

    public Shape(int startX, int startY, int endX, int endY, Graphics g) {

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.g = g;
    }

    public Shape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public abstract void show();
}