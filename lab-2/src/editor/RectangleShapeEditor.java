package editor;

import shape.RectangleShape;
import shape_editor.ShapeObjectsEditor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectangleShapeEditor extends ShapeEditor {

        public RectangleShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
            super(shapeObjectsEditor);
            currentObject = chosenObject.RECTANGLE;
        }

        public Point startPoint, endPoint;

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);

        RectangleShape rectangleShape;

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                rectangleShape = new RectangleShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                drawTireMark(rectangleShape);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = rectangleShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endX = endPoint.x;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endY = endPoint.y;
                rectangleShape = (RectangleShape) shapeObjectsEditor.showedShapes[shapeObjectsEditor.index];
                drawTireMark(rectangleShape);
                saveShape();
            }
        }
    }

    public void saveShape() {

        shapeObjectsEditor.removeMouseMotionListener(shapeObjectsEditor.getMouseMotionListeners()[0]);
        shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
        shapeObjectsEditor.repaint();
        shapeObjectsEditor.index++;
    }

    public void trackMouseMoving() {

        shapeObjectsEditor.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endX = endPoint.x;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endY = endPoint.y;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
            }
        });
    }

    public void drawTireMark(RectangleShape tireShape) {

        if (shapeObjectsEditor.isDrawing) tireShape.setDrawColor(Color.blue);
        else tireShape.setDrawColor(Color.black);
    }
}