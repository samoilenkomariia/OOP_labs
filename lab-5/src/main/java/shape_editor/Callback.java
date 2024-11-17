package shape_editor;

import shape_editor.shapes.Shape;

public interface Callback {

    void onShapeAdded(Shape shape);
    void onShapeSelected();
    void deleteSelectedShapes();
}
