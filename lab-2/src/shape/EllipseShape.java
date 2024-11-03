package shape;

import java.awt.*;

public class EllipseShape extends Shape {

    private Color drawColor = Color.black;

    public EllipseShape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public EllipseShape(int startX, int startY, int endX, int endY, Graphics g) {

        super(startX, startY, endX, endY, g);
        show();
    }

    @Override
    public void show() {

        int width = 2 * Math.abs(startX - endX);
        int height = 2 * Math.abs(startY - endY);
        int x = Math.abs(startX - width/2);
        int y = Math.abs(startY - height/2);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(drawColor);
        g2.drawOval(x, y, width, height);
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }
}