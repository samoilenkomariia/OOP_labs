package shape_editor.shapes;

import java.awt.*;

public abstract class Shape {

    protected int startX, startY;
    protected int endX, endY;
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

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public abstract void show();
}