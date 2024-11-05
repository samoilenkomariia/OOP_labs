package shape;

import java.awt.*;

public class EllipseShape extends Shape {

    private Color drawColor = Color.black;
    private int x, y, width, height;

    public EllipseShape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public EllipseShape(int startX, int startY, int endX, int endY, Graphics g) {

        super(startX, startY, endX, endY, g);
        show();
    }

    @Override
    public void show() {

        if (Math.abs(startX - endX)*2 <= startX*2 && Math.abs(startY - endY)*2 <= startY*2) {
            width = Math.abs(startX - endX)*2;
            height = Math.abs(startY - endY)*2;
            x = Math.abs(startX - width/2);
            y = Math.abs(startY - height/2);
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(drawColor);
        g2.drawOval(x, y, width, height);
    }

    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }
}