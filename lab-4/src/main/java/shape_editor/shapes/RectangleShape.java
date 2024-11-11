package shape_editor.shapes;

import java.awt.*;

public class RectangleShape extends Shape implements RectangleShapeInterface {

    private int x, y, width, height;

    public RectangleShape(int startX, int startY, Graphics g) {
        super(startX, startY, g);
    }

    @Override
    public void show() {
        showRectangle(startX, startY, endX, endY, g);
    }

    @Override
    public void showRectangle(int startX, int startY, int endX, int endY, Graphics g) {
        width = Math.abs(startX - endX)*2;
        height = Math.abs(startY - endY)*2;
        x = startX - width/2;
        y = startY - height/2;
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        g2d.setColor(Color.black);
        g2d.drawRect(x, y, width, height);
    }

    private void setStrokeToG(Graphics2D g2d) {
        if (isDottedDrawing) {
            float[] dashPattern = {10, 10};
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
        }
        else g2d.setStroke(new BasicStroke(3));
    }
}