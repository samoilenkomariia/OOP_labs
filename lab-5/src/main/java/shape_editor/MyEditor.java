package shape_editor;

import shape_editor.shapes.Shape;
import shape_editor.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyEditor extends JPanel {

    private static MyEditor instance;
    private Callback callback;

    private MyEditor() {}

    public static MyEditor getInstance() {
        if (instance == null) {
            instance = new MyEditor();
        }
        return instance;
    }

    public enum objects { NONE, POINT, LINE, RECTANGLE, ELLIPSE, LINEOO, CUBE}
    public objects currentObject = objects.NONE;
    public boolean isDrawing;
    private Point startPoint, endPoint;
    public ArrayList<Shape> showedShapes = new ArrayList<>();
    public ArrayList<Shape> lastDeletedShapes = new ArrayList<>();
    private Shape shape;
    public boolean deletedAll = false;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void drawShape() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                processMouseEvent(evt);
            }
        });
    }

    public void endDrawing() {
        if (getMouseListeners().length > 0) {
            removeMouseListener(getMouseListeners()[0]);
        }
    }

    public Shape createShape(objects obj, Point start, Graphics g) {
        return switch (obj) {
            case POINT -> new PointShape(start.x, start.y, g);
            case LINE -> new LineShape(start.x, start.y, g);
            case RECTANGLE -> new RectangleShape(start.x, start.y, g);
            case ELLIPSE -> new EllipseShape(start.x, start.y, g);
            case LINEOO -> new LineOOShape(start.x, start.y, g);
            case CUBE -> new CubeShape(start.x, start.y, g);
            default -> null;
        };
    }

    @Override
    public void processMouseEvent(MouseEvent event) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!isDrawing) {
                lastDeletedShapes.clear();
                isDrawing = true;
                startPoint = event.getPoint();
                shape = createShape(currentObject, startPoint, getGraphics());
                showedShapes.add(shape);
                showedShapes.get(showedShapes.size()-1).show();
                repaint();
                if (shape.isTrackable()) trackMouseMoving();
                else {
                    isDrawing = false;
                    initCallback();
                }
            } else {
                isDrawing = false;
                endPoint = event.getPoint();
                showedShapes.get(showedShapes.size()-1).setEndX(endPoint.x);
                showedShapes.get(showedShapes.size()-1).setEndY(endPoint.y);
                shape.setDottedDrawing(false);
                saveShape();
            }
        }
    }

    private void initCallback() {
        if (callback != null) {
            callback.onShapeAdded(shape);
        }
    }

    protected void saveShape() {
        initCallback();
        removeMouseMotionListener(getMouseMotionListeners()[0]);
        if (shape.isFillAble()) shape.setFillColor(Color.orange);
        showedShapes.get(showedShapes.size()-1).show();
        repaint();
    }

    protected void trackMouseMoving() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                endPoint = event.getPoint();
                showedShapes.get(showedShapes.size()-1).setEndX(endPoint.x);
                showedShapes.get(showedShapes.size()-1).setEndY(endPoint.y);
                shape.setDottedDrawing(true);
                showedShapes.get(showedShapes.size()-1).show();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : showedShapes) {
            if (shape != null) {
                shape.g = g;
                shape.show();
            }
        }
    }

    public void drawFileContent(objects obj, int startX, int startY, int endX, int endY) {
        shape = createShape(obj, new Point(startX, startY), getGraphics());
        shape.setEndX(endX);
        shape.setEndY(endY);
        shape.setFillColor(Color.orange);
        showedShapes.add(shape);
        shape.show();
        repaint();
    }

    public String getChosenObject() {

        return switch(currentObject) {
            case POINT -> "Вибраний об'єкт: Крапка";
            case LINE -> "Вибраний об'єкт: Лінія";
            case RECTANGLE -> "Вибраний об'єкт: Прямокутник";
            case ELLIPSE -> "Вибраний об'єкт: Еліпс";
            case LINEOO -> "Вибраний об'єкт: Відрізок";
            case CUBE -> "Вибраний об'єкт: Куб";
            default -> "Вибраний об'єкт: Немає";
        };
    }
}