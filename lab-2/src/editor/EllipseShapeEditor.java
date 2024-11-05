package editor;

import shape.EllipseShape;
import shape_editor.ShapeObjectsEditor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EllipseShapeEditor extends ShapeEditor{

    public EllipseShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = objects.ELLIPSE;
    }

    private Point startPoint, endPoint;

    @Override
    public void processMouseEvent(MouseEvent event) {

        EllipseShape ellipseShape;
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            if (!shapeObjectsEditor.isDrawing) {
                shapeObjectsEditor.isDrawing = true;
                startPoint = event.getPoint();
                ellipseShape = new EllipseShape(startPoint.x,
                        startPoint.y, shapeObjectsEditor.getGraphics());
                drawTireMark(ellipseShape);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] = ellipseShape;
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
                trackMouseMoving();
            } else {
                shapeObjectsEditor.isDrawing = false;
                endPoint = event.getPoint();
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndX(endPoint.x);
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].setEndY(endPoint.y);
                ellipseShape = (EllipseShape) shapeObjectsEditor.showedShapes[shapeObjectsEditor.index];
                drawTireMark(ellipseShape);
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
                shapeObjectsEditor.showedShapes[shapeObjectsEditor.index].show();
                shapeObjectsEditor.repaint();
            }
        });
    }

    private void drawTireMark(EllipseShape tireShape) {

        if (shapeObjectsEditor.isDrawing) tireShape.setDrawColor(Color.blue);
        else tireShape.setDrawColor(Color.black);
    }
}