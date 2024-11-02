package shape;

import java.awt.*;

public class PointShape extends Shape {

    public PointShape(int x, int y, Graphics g) {
        super(x, y, x, y, g);
    }

    public void show() {

        final int width = 5;
        final int height = 5;
        g.setColor(Color.black);
        g.fillOval(startX, startY, width, height);
    }
}
