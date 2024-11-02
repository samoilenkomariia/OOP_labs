package shape;

import java.awt.*;

public class RectangleShape extends Shape {

    public int x, y, width, height;
    public Color drawColor = Color.black;

    public RectangleShape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public RectangleShape(int startX, int startY, int endX, int endY, Graphics g) {

        super(startX, startY, endX, endY, g);
        show();
    }

    @Override
    public void show() {

        x = Math.min(startX, endX);
        y = Math.min(startY, endY);
        width = Math.abs(startX - endX);
        height = Math.abs(startY - endY);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(drawColor);
        g2.drawRect(x, y, width+2, height+2);
        g2.setColor(Color.orange);
        g2.fillRect(x+1, y+1, width, height);
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }
}
