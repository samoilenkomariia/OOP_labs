package shape_editor.editor;

import shape_editor.ShapeObjectsEditor;
import shape_editor.shapes.LineShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LineShapeEditor extends ShapeEditor {

    public LineShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = objects.LINE;
    }
    private Point startPoint, endPoint;
    private LineShape lineShape;

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                lineShape = new LineShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = lineShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndX(endPoint.x);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndY(endPoint.y);
                lineShape = (LineShape) shapeObjectsEditor.showedShapes
                        [shapeObjectsEditor.index];
                lineShape.setDottedDrawing(false);
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
                lineShape.setDottedDrawing(true);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
            }
        });
    }
}
