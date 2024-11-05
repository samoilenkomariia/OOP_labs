import shape_editor.ShapeObjectsEditor;
import shape_editor.editor.*;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private final JPanel contentPane;
    private final JMenuItem exitItem, pointItem, lineItem, rectangularItem, ellipseItem;
    private final ShapeObjectsEditor shapeObjectsEditor;
    private boolean isDrawingEnabled = false;
    private final JButton pointButton, lineButton, rectangleButton, ellipseButton;

    public Main() {

        setTitle("Lab3 - Вибраний об'єкт: Немає");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        contentPane = new JPanel(new BorderLayout());
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


        objectsMenu.add(pointItem);
        objectsMenu.add(lineItem);
        objectsMenu.add(rectangularItem);
        objectsMenu.add(ellipseItem);

        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        toolBar.setFloatable(false);
        pointButton = createIconButton("resources/pointIcon.png", "Крапка");
        lineButton = createIconButton("resources/lineIcon.png", "Лінія");
        rectangleButton = createIconButton("resources/rectangleIcon.png", "Прямокутник");
        ellipseButton = createIconButton("resources/ellipseIcon.png", "Еліпс");

        toolBar.add(pointButton);
        toolBar.add(lineButton);
        toolBar.add(rectangleButton);
        toolBar.add(ellipseButton);

        contentPane.add(toolBar, BorderLayout.NORTH);

        setVisible(true);

        shapeObjectsEditor = new ShapeObjectsEditor();
        shapeObjectsEditor.setBounds(0, toolBar.getHeight(), getWidth(),
                getHeight() - toolBar.getHeight());

        handleMenuItemsActionListeners();
        manageToolBarEvents();
    }

    private JButton createIconButton(String iconPath, String tooltipText) {

        JButton button = new JButton(new ImageIcon(new ImageIcon(iconPath).getImage()
                .getScaledInstance(getWidth()/33, getHeight()/25, Image.SCALE_SMOOTH)));
        button.setToolTipText(tooltipText);
        return button;
    }

    private void handleMenuItemsActionListeners() {

        exitItem.addActionListener(event -> System.exit(0));

        pointItem.addActionListener(event -> chooseEditorDrawObject(new PointShapeEditor(shapeObjectsEditor)));

        lineItem.addActionListener(event -> chooseEditorDrawObject(new LineShapeEditor(shapeObjectsEditor)));

        rectangularItem.addActionListener(event -> chooseEditorDrawObject(new RectangleShapeEditor(shapeObjectsEditor)));

        ellipseItem.addActionListener(event -> chooseEditorDrawObject(new EllipseShapeEditor(shapeObjectsEditor)));
    }

    private void manageToolBarEvents() {

        pointButton.addActionListener(event -> chooseEditorDrawObject(new PointShapeEditor(shapeObjectsEditor)));

        lineButton.addActionListener(event -> chooseEditorDrawObject(new LineShapeEditor(shapeObjectsEditor)));

        rectangleButton.addActionListener(event -> chooseEditorDrawObject(new RectangleShapeEditor(shapeObjectsEditor)));

        ellipseButton.addActionListener(event -> chooseEditorDrawObject(new EllipseShapeEditor(shapeObjectsEditor)));
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
        setTitle("Lab3 - " + shapeObjectsEditor.currentEditor.getChosenObject());
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
        setTitle("Lab3 - Вибраний об'єкт: Немає");
        shapeObjectsEditor.removeMouseListener(shapeObjectsEditor.getMouseListeners()[0]);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::new);
    }
}