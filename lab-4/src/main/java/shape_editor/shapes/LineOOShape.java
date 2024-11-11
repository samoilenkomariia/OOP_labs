package shape_editor.shapes;

import java.awt.*;

public class LineOOShape extends Shape implements LineShapeInterface, EllipseShapeInterface {

    public LineOOShape(int startX, int startY, Graphics g) {

        super(startX, startY, g);
        fillColor = Color.orange;
    }

    public void show() {

        showEllipse(startX, startY, endX, endY, g, fillColor);
        showLine(startX, startY, endX, endY, g);
        showEllipse(endX, endY, startX, startY, g, fillColor);
    }

    public void showLine(int startX, int startY, int endX, int endY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        g2d.setColor(Color.black);
        g2d.drawLine(startX, startY, endX, endY);
        showEllipse(startX, startY, endX, endY, g, fillColor);
    }

    public void showEllipse(int startX, int startY, int endX, int endY, Graphics g, Color fillColor) {
        int width = 30;
        int height = 30;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.black);
        g2d.drawOval(startX - width/2, startY-height/2, width+1, height+1);
        g2d.setColor(fillColor);
        g2d.fillOval(startX - width/2, startY-height/2, width+1, height+1);
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