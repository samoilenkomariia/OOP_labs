package shape_editor.shapes;

import java.awt.*;

public abstract class Shape {

    protected int startX, startY;
    protected int endX, endY;
    public Graphics g;
    protected boolean isDottedDrawing;
    protected boolean isTrackable = true;
    protected boolean isFillAble = false;
    protected Color fillColor = new Color(0 ,0, 0, 0);

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

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setDottedDrawing(boolean dottedDrawing) {
        isDottedDrawing = dottedDrawing;
    }

    public boolean isTrackable() {
        return isTrackable;
    }

    public boolean isFillAble() {
        return isFillAble;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public abstract void show();
}