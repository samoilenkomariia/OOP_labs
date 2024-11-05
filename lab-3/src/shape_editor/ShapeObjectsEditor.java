package shape_editor;

import shape_editor.editor.ShapeEditor;
import shape_editor.shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class ShapeObjectsEditor extends JPanel {

    public ShapeEditor currentEditor;
    public Shape[] showedShapes = new Shape[123];
    public int index = 0;
    public boolean isDrawing;

    public void drawShape() {

        addMouseListener(new MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                currentEditor.processMouseEvent(evt);
                if(index+1 > 123) index = 0;
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
}