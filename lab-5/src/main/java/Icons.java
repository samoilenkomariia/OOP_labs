import javax.swing.*;
import java.awt.*;

class EllipseIcon implements Icon {
    private final int width = 30;
    private final int height = 24;

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(x, y, width, height);
        g2d.dispose();
    }
}

class PointIcon implements Icon {

    private final int width = 24;
    private final int height = 24;

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(1));
        g2d.fillOval(x, y, width, height);
        g2d.dispose();
    }
}

class LineIcon implements Icon {

    private final int width = 24;
    private final int height = 24;

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(x, y, x + width, y + height);
        g2d.dispose();
    }
}

class RectangleIcon implements Icon {

    private final int width = 30;
    private final int height = 24;

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(x, y, width, height);
        g2d.dispose();
    }
}

class LineOOIcon implements Icon {

    private final int width = 30;
    private final int height = 30;

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.black);
        g2d.drawLine(x+3, y+3, x + width, y + height);
        g2d.drawOval(x, y, 12, 12);
        g2d.drawOval(27, 27, 12, 12);
        g2d.setColor(Color.white);
        g2d.fillOval(x, y, 12, 12);
        g2d.fillOval(27, 27, 12, 12);
        g2d.dispose();
    }
}