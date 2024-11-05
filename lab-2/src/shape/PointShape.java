package shape;

import java.awt.*;

public class PointShape extends Shape {

    public PointShape(int x, int y, Graphics g) {
        super(x, y, x, y, g);
    }

    public void show() {

        final int width = 7;
        final int height = 7;
        g.setColor(Color.black);
        g.fillOval(startX, startY, width, height);
    }
}