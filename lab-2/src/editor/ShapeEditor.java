package editor;

import shape_editor.ShapeObjectsEditor;

import java.awt.event.MouseEvent;

public class ShapeEditor extends Editor {

    protected ShapeObjectsEditor shapeObjectsEditor;
    public enum objects { NONE, POINT, LINE, RECTANGLE, ELLIPSE }
    protected objects currentObject = objects.NONE;


    public ShapeEditor (ShapeObjectsEditor shapeObjectsEditor) {
        this.shapeObjectsEditor = shapeObjectsEditor;
    }

    public objects getCurrentObject() {
        return currentObject;
    }



    @Override
    public void processMouseEvent(MouseEvent event) {

    }

    protected void saveShape() {

    }

    protected void trackMouseMoving() {

    }

    public String onInitMenuPopUp() {

        return switch(currentObject) {
            case POINT -> "Вибраний об'єкт: Крапка";
            case LINE -> "Вибраний об'єкт: Лінія";
            case RECTANGLE -> "Вибраний об'єкт: Прямокутник";
            case ELLIPSE -> "Вибраний об'єкт: Еліпс";
            default -> "Вибраний об'єкт: Немає";
        };
    }
}
