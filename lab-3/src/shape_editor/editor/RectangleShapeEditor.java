package shape_editor.editor;

import shape_editor.shapes.RectangleShape;
import shape_editor.ShapeObjectsEditor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectangleShapeEditor extends ShapeEditor {

    public RectangleShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = objects.RECTANGLE;
    }

    private Point startPoint, endPoint;
    private RectangleShape rectangleShape;

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                rectangleShape = new RectangleShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = rectangleShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndX(endPoint.x);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndY(endPoint.y);
                rectangleShape = (RectangleShape) shapeObjectsEditor.showedShapes
                        [shapeObjectsEditor.index];
                rectangleShape.setDottedDrawing(false);

                saveShape();
            }
        }
    }

    protected void saveShape() {

        shapeObjectsEditor.removeMouseMotionListener(shapeObjectsEditor.getMouseMotionListeners()[0]);
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
        shapeObjectsEditor.repaint();
        shapeObjectsEditor.index++;
    }

    protected void trackMouseMoving() {

        shapeObjectsEditor.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndX(endPoint.x);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndY(endPoint.y);
                rectangleShape.setDottedDrawing(true);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
            }
        });
    }
}