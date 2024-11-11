package shape_editor.shapes;

import java.awt.*;

public class PointShape extends Shape {

    public PointShape(int x, int y, Graphics g) {
        super(x, y, g);
        isTrackable = false;
    }

    public void show() {

        final int width = 8;
        final int height = 8;
        g.setColor(Color.black);
        g.fillOval(startX, startY, width, height);
    }
}