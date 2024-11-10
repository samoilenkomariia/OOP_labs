package shape_editor;

import shape_editor.shapes.Shape;
import shape_editor.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyEditor extends JPanel {

    public enum objects { NONE, POINT, LINE, RECTANGLE, ELLIPSE, LINEOO, CUBE}
    public objects currentObject = objects.NONE;
    public boolean isDrawing;
    private Point startPoint, endPoint;
    public ArrayList<Shape> showedShapes = new ArrayList<>();
    public ArrayList<Shape> lastDeletedShapes = new ArrayList<>();
    private Shape shape;
    public boolean deletedAll = false;

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

    private void chooseShapeToDraw() {
        switch (currentObject) {
            case POINT:
                shape = new PointShape(startPoint.x, startPoint.y, getGraphics());
                break;
            case LINE:
                shape = new LineShape(startPoint.x, startPoint.y, getGraphics());
                break;
            case RECTANGLE:
                shape = new RectangleShape(startPoint.x, startPoint.y, getGraphics());
                break;
            case ELLIPSE:
                shape = new EllipseShape(startPoint.x, startPoint.y, getGraphics());
                break;
            case LINEOO:
                shape = new LineOOShape(startPoint.x, startPoint.y, getGraphics());
                break;
            case CUBE:
                shape = new CubeShape(startPoint.x, startPoint.y, getGraphics());
                break;
        }
    }

    @Override
    public void processMouseEvent(MouseEvent event) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!isDrawing) {
                lastDeletedShapes.clear();
                isDrawing = true;
                startPoint = event.getPoint();
                chooseShapeToDraw();
                showedShapes.add(shape);
                showedShapes.get(showedShapes.size()-1).show();
                repaint();
                if (shape.isTrackable()) trackMouseMoving();
                else {
                    isDrawing = false;
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

    protected void saveShape() {
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