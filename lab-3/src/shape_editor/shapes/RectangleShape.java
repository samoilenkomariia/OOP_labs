package shape_editor.shapes;

import java.awt.*;

public class RectangleShape extends Shape {

    private boolean isDottedDrawing;
    private int x, y, width, height;

    public RectangleShape(int startX, int startY, Graphics g) {
        this(startX, startY, startX, startY, g);
    }

    public RectangleShape(int startX, int startY, int endX, int endY, Graphics g) {

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
        Graphics2D g2d = (Graphics2D) g;
        setStrokeToG(g2d);
        g2d.setColor(Color.black);
        g2d.drawRect(x, y, width, height);
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
}