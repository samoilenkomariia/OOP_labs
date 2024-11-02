package editor;

import shape.PointShape;
import shape_editor.ShapeObjectsEditor;

import java.awt.event.MouseEvent;

public class PointShapeEditor extends ShapeEditor {

    public PointShapeEditor(ShapeObjectsEditor shapeObjectsEditor) {
        super(shapeObjectsEditor);
        currentObject = chosenObject.POINT;
    }

    @Override
    public void processMouseEvent(MouseEvent event) {

        super.processMouseEvent(event);
        int x = event.getX();
        int y = event.getY();

        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            shapeObjectsEditor.showedShapes[shapeObjectsEditor.index] =
                    new PointShape(x, y, shapeObjectsEditor.getGraphics());
            shapeObjectsEditor.repaint();
            shapeObjectsEditor.index++;
        }
    }
}
