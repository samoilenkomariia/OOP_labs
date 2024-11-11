package shape_editor.shapes;

import java.awt.*;

public class LineShape extends Shape implements LineShapeInterface {

    public LineShape(int startX, int startY, Graphics g) {
        super(startX, startY, g);
    }

    public void show() {
        showLine(startX, startY, endX, endY, g);
    }

    @Override
    public void showLine(int startX, int startY, int endX, int endY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        g2d.setColor(Color.black);
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