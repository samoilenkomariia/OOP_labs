package editor;

import shape.LineShape;
import shape_editor.ShapeObjectsEditor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LineShapeEditor extends ShapeEditor {

    public LineShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = chosenObject.LINE;
    }
    public Point startPoint, endPoint;

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);

        LineShape lineShape;

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                lineShape = new LineShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                drawTireMark(lineShape);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = lineShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endX = endPoint.x;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].endY = endPoint.y;
                lineShape = (LineShape) shapeObjectsEditor.showedShapes[shapeObjectsEditor.index];
                drawTireMark(lineShape);
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

    public void drawTireMark(LineShape tireShape) {

        if (shapeObjectsEditor.isDrawing) tireShape.setDrawColor(Color.blue);
        else tireShape.setDrawColor(Color.black);
    }
}