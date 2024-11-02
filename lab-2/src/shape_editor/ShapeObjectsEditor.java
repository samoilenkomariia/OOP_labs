package shape_editor;

import editor.ShapeEditor;
import shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class ShapeObjectsEditor extends JPanel {

        public ShapeEditor currentEditor;
        public Shape[] showedShapes = new Shape[100];
        public int index = 0;
        public boolean isDrawing;
        public Graphics g;


        public void drawShape() {

            addMouseListener(new MouseAdapter() {

                public void mousePressed(java.awt.event.MouseEvent evt) {
                    g = getGraphics();
                    currentEditor.processMouseEvent(evt);
                }
            });
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : showedShapes) {
                if (shape != null) {
                    shape.g = g;
                    shape.show();
                }
            }
        }
}
