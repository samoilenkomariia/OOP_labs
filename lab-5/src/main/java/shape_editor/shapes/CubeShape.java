package shape_editor.shapes;

import java.awt.*;

public class CubeShape extends Shape implements RectangleShapeInterface, LineShapeInterface {

    private int x, y, width;

    public CubeShape(int startX, int startY, Graphics g) {
        super(startX, startY, g);
        shapeName = "Куб";
        show();
    }

    @Override
    public void show() {
            showRectangle(startX, startY, endX, endY, g);
            showRectangle(startX + width / 3, startY - width / 3,
                    endX + width / 3, endY - width / 3, g);
            int xA = startX - width / 2, yA = startY - width / 2;
            int xB = xA + width / 3, yB = yA - width / 3;
            showLine(xA, yA, xB, yB, g);
            showLine(xA, yA + width, xB, yB + width, g);
            showLine(xA + width, yA, xB + width, yB, g);
            showLine(xA + width, yA + width, xB + width, yB + width, g);
    }

    @Override
    public void showRectangle(int startX, int startY, int endX, int endY, Graphics g) {
        width = Math.abs(startX - endX)*2;
        x = startX - width/2;
        y = startY - width/2;
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        chooseColor();
        g2d.drawRect(x, y, width, width);
    }

    @Override
    public void showLine(int startX, int startY, int endX, int endY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        chooseColor();
        g2d.drawLine(startX, startY, endX, endY);
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