package shape;

import java.awt.*;

public class LineShape extends Shape {

    private Color drawColor = Color.black;

    public LineShape(int startX, int startY, Graphics g) {
        super(startX, startY, g);
    }

    public void show() {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2)); // Set the line thickness to 3
        g2d.setColor(drawColor);
        g2d.drawLine(startX, startY, endX, endY);
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }
}