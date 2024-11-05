package shape_editor.shapes;

import java.awt.*;

public class EllipseShape extends Shape {

    private Color fillColor = new Color(0 ,0, 0, 0);
    private boolean isDottedDrawing;

    public EllipseShape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public EllipseShape(int startX, int startY, int endX, int endY, Graphics g) {

        super(startX, startY, endX, endY, g);
        show();
    }

    @Override
    public void show() {

        int width = Math.abs(startX - endX);
        int height = Math.abs(startY - endY);
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        g2d.setColor(Color.black);
        g2d.drawOval(x, y, width+1, height+1);
        g2d.setColor(fillColor);
        g2d.fillOval(x+1, y+1, width-1, height-1);
    }

    private void setStrokeToG(Graphics2D g2d) {
        if (isDottedDrawing) {
            float[] dashPattern = {10, 10};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
        }
        else g2d.setStroke(new BasicStroke(2));
    }

    public void setDottedDrawing(boolean dottedDrawing) {
        isDottedDrawing = dottedDrawing;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}