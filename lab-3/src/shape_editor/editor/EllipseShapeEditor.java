package shape_editor.editor;

import shape_editor.ShapeObjectsEditor;
import shape_editor.shapes.EllipseShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EllipseShapeEditor extends ShapeEditor{

    public EllipseShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = objects.ELLIPSE;
    }

    private Point startPoint, endPoint;
    private EllipseShape ellipseShape;

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                ellipseShape = new EllipseShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = ellipseShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndX(endPoint.x);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndY(endPoint.y);
                ellipseShape = (EllipseShape) shapeObjectsEditor.showedShapes
                        [shapeObjectsEditor.index];
                ellipseShape.setDottedDrawing(false);

                saveShape();
            }
        }
    }
    protected void saveShape() {

        shapeObjectsEditor.removeMouseMotionListener(shapeObjectsEditor.getMouseMotionListeners()[0]);
        ellipseShape.setFillColor(Color.orange);
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
                ellipseShape.setDottedDrawing(true);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
            }
        });
    }
}
