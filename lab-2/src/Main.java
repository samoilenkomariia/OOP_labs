import editor.*;
import shape_editor.ShapeObjectsEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private final JPanel contentPane;
    private final JMenuItem exitItem;
    private final JMenuItem pointItem;
    private final JMenuItem lineItem;
    private final JMenuItem rectangularItem;
    private final JMenuItem ellipseItem;
    private final JMenuItem selectedShapeItem;
    private final ShapeObjectsEditor shapeObjectsEditor;
    private boolean isDrawingEnabled = false;

    public Main() {

        setTitle("Lab2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        JMenu objectsMenu = new JMenu("Об'єкти");
        JMenu helpMenu = new JMenu("Довідка");

        menuBar.add(fileMenu);
        menuBar.add(objectsMenu);
        menuBar.add(helpMenu);

        exitItem = new JMenuItem("Закрити програму");
        fileMenu.add(exitItem);

        pointItem = new JMenuItem("Крапка");
        lineItem = new JMenuItem("Лінія");
        rectangularItem = new JMenuItem("Прямокутник");
        ellipseItem = new JMenuItem("Еліпс");

        selectedShapeItem = new JMenuItem("Вибраний об'єкт: Немає");
        selectedShapeItem.setEnabled(false);

        objectsMenu.add(pointItem);
        objectsMenu.add(lineItem);
        objectsMenu.add(rectangularItem);
        objectsMenu.add(ellipseItem);
        objectsMenu.add(selectedShapeItem);

        setVisible(true);

        shapeObjectsEditor = new ShapeObjectsEditor();
        shapeObjectsEditor.setBounds(0, 0, 800, 600);
        handleMenuItemsActionListeners();
    }
    private void handleMenuItemsActionListeners() {

        exitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        pointItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                chooseEditorDrawObject(new PointShapeEditor(shapeObjectsEditor));
            }
        });

        lineItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                chooseEditorDrawObject(new LineShapeEditor(shapeObjectsEditor));
            }
        });

        rectangularItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                chooseEditorDrawObject(new RectangleShapeEditor(shapeObjectsEditor));
            }
        });

        ellipseItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                chooseEditorDrawObject(new EllipseShapeEditor(shapeObjectsEditor));
            }
        });
    }

    private void chooseEditorDrawObject(ShapeEditor shapeEditor) {

        if (!isDrawingEnabled) {
            shapeObjectsEditor.currentEditor = shapeEditor;
            enableDrawing();
        }
        else if (shapeObjectsEditor.currentEditor != null && shapeObjectsEditor.currentEditor.
                getCurrentObject() != ShapeEditor.objects.NONE &&
        shapeObjectsEditor.currentEditor.getCurrentObject() != shapeEditor.getCurrentObject())
        {
            disableDrawing();
            shapeObjectsEditor.currentEditor = shapeEditor;
            enableDrawing();
        }
        else {
            disableDrawing();
        }
    }

    private void enableDrawing() {

        isDrawingEnabled = true;
        selectedShapeItem.setText(shapeObjectsEditor.currentEditor.onInitMenuPopUp());
        if (shapeObjectsEditor.getMouseListeners().length == 0) {
            shapeObjectsEditor.drawShape();
        }
        else {
            shapeObjectsEditor.removeMouseListener(shapeObjectsEditor.getMouseListeners()[0]);
            shapeObjectsEditor.drawShape();
        }
        contentPane.add(shapeObjectsEditor);
    }

    private void disableDrawing() {

        isDrawingEnabled = false;
        selectedShapeItem.setText("Вибраний об'єкт: Немає");
        shapeObjectsEditor.removeMouseListener(shapeObjectsEditor.getMouseListeners()[0]);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::new);
    }
}