package shape_editor;

import editor.ShapeEditor;
import shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class ShapeObjectsEditor extends JPanel {

        public ShapeEditor currentEditor;
        public Shape[] showedShapes = new Shape[122];
        public int index = 0;
        public boolean isDrawing;

        public void drawShape() {

            addMouseListener(new MouseAdapter() {

                public void mousePressed(java.awt.event.MouseEvent evt) {
                    currentEditor.processMouseEvent(evt);
                    if(index+1 > 122) index = 0;
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