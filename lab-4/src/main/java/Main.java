import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import shape_editor.MyEditor;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private final JPanel contentPane;
    private final JMenuItem exitItem, pointItem, lineItem, rectangularItem, ellipseItem,
            lineOOItem, cubeItem, eraseItem, undoItem, redoItem;
    private final JToolBar toolBar;
    private boolean isDrawingEnabled = false;
    private final JButton pointButton, lineButton, rectangleButton, ellipseButton,
            eraseButton, undoButton, redoButton, lineOOButton, cubeButton;
    private final MyEditor myEditor = new MyEditor();

    public Main() {
        setTitle("Lab4 - Вибраний об'єкт: Немає");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        {
            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            JMenu fileMenu = new JMenu("Файл");
            JMenu objectsMenu = new JMenu("Об'єкти");
            JMenu helpMenu = new JMenu("Довідка");

            menuBar.add(fileMenu);
            menuBar.add(objectsMenu);
            menuBar.add(helpMenu);

            exitItem = new JMenuItem("Закрити програму");
            eraseItem = new JMenuItem("Очистити");
            undoItem = new JMenuItem("Скасувати");
            redoItem = new JMenuItem("Повернути");
            fileMenu.add(eraseItem);
            fileMenu.add(undoItem);
            fileMenu.add(redoItem);
            fileMenu.add(exitItem);

            pointItem = new JMenuItem("Крапка");
            lineItem = new JMenuItem("Лінія");
            rectangularItem = new JMenuItem("Прямокутник");
            ellipseItem = new JMenuItem("Еліпс");
            lineOOItem = new JMenuItem("Відрізок");
            cubeItem = new JMenuItem("Куб");

            objectsMenu.add(pointItem);
            objectsMenu.add(lineItem);
            objectsMenu.add(rectangularItem);
            objectsMenu.add(ellipseItem);
            objectsMenu.add(lineOOItem);
            objectsMenu.add(cubeItem);
        }

        {
            toolBar = new JToolBar();
            toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            toolBar.setFloatable(false);

            eraseButton = createButton(FontIcon.of(MaterialDesign.MDI_ERASER, 24), "Очистити");
            undoButton = createButton(FontIcon.of(MaterialDesign.MDI_UNDO, 24), "Скасувати");
            redoButton = createButton(FontIcon.of(MaterialDesign.MDI_REDO, 24), "Повернути");
            pointButton = createButton(new PointIcon(), "Крапка");
            lineButton = createButton(new LineIcon(), "Лінія");
            rectangleButton = createButton(new RectangleIcon(), "Прямокутник");
            ellipseButton = createButton(new EllipseIcon(), "Еліпс");
            lineOOButton = createButton(new LineOOIcon(), "Відрізок");
            cubeButton = createButton(FontIcon.of(MaterialDesign.MDI_CUBE_OUTLINE, 34), "Куб");

            contentPane.add(toolBar, BorderLayout.NORTH);
        }

        setVisible(true);

        handleMenuItemsActionListeners();
        manageToolBarEvents();
    }

    private JButton createButton(Icon icon, String tooltip) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        toolBar.add(button);
        return button;
    }

    private void handleMenuItemsActionListeners() {
        exitItem.addActionListener(event -> System.exit(0));

        eraseItem.addActionListener(event -> {
            myEditor.lastDeletedShapes.addAll(myEditor.showedShapes);
            myEditor.showedShapes.clear();
            myEditor.deletedAll = true;
            contentPane.repaint();
        });

        undoItem.addActionListener(event -> {
            if (!myEditor.showedShapes.isEmpty()) {
                myEditor.lastDeletedShapes.add(myEditor.showedShapes
                        .get(myEditor.showedShapes.size()-1));
                myEditor.showedShapes.remove(myEditor.showedShapes.size()-1);
                contentPane.repaint();
            }
        });

        redoItem.addActionListener(event -> {
            if (!myEditor.lastDeletedShapes.isEmpty()) {
                if (!myEditor.deletedAll) {
                    myEditor.showedShapes.add(myEditor.lastDeletedShapes
                            .get(myEditor.lastDeletedShapes.size() - 1));
                    myEditor.lastDeletedShapes.remove(myEditor.lastDeletedShapes.size() - 1);
                    contentPane.repaint();
                }
                else {
                    myEditor.showedShapes.addAll(myEditor.lastDeletedShapes);
                    myEditor.lastDeletedShapes.clear();
                    myEditor.deletedAll = false;
                    contentPane.repaint();
                }
            }
        });

        pointItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.POINT));

        lineItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.LINE));

        rectangularItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.RECTANGLE));

        ellipseItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.ELLIPSE));

        lineOOItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.LINEOO));

        cubeItem.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.CUBE));
    }

    private void manageToolBarEvents() {

        pointButton.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.POINT));

        lineButton.addActionListener(event ->chooseEditorDrawObject(MyEditor.objects.LINE));

        rectangleButton.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.RECTANGLE));

        ellipseButton.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.ELLIPSE));

        lineOOButton.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.LINEOO));

        cubeButton.addActionListener(event -> chooseEditorDrawObject(MyEditor.objects.CUBE));

        eraseButton.addActionListener(event -> {
            myEditor.lastDeletedShapes.addAll(myEditor.showedShapes);
            myEditor.showedShapes.clear();
            myEditor.deletedAll = true;
            contentPane.repaint();
        });

        undoButton.addActionListener(event -> {
            if (!myEditor.showedShapes.isEmpty()) {
                myEditor.lastDeletedShapes.add(myEditor.showedShapes
                        .get(myEditor.showedShapes.size()-1));
                myEditor.showedShapes.remove(myEditor.showedShapes.size()-1);
                contentPane.repaint();
            }
        });

        redoButton.addActionListener(event -> {
            if (!myEditor.lastDeletedShapes.isEmpty()) {
                if (!myEditor.deletedAll) {
                    myEditor.showedShapes.add(myEditor.lastDeletedShapes
                            .get(myEditor.lastDeletedShapes.size() - 1));
                    myEditor.lastDeletedShapes.remove(myEditor.lastDeletedShapes.size() - 1);
                    contentPane.repaint();
                }
                else {
                    myEditor.showedShapes.addAll(myEditor.lastDeletedShapes);
                    myEditor.lastDeletedShapes.clear();
                    myEditor.deletedAll = false;
                    contentPane.repaint();
                }
            }
        });
    }

    private void chooseEditorDrawObject(MyEditor.objects currentObj) {

        if (!isDrawingEnabled) {
            myEditor.setBounds(0, toolBar.getHeight(), getWidth(),
                    getHeight() - toolBar.getHeight());
            contentPane.add(myEditor);
            myEditor.currentObject = currentObj;
            myEditor.drawShape();
            enableDrawing(myEditor);
        }
        else if (myEditor.currentObject != MyEditor.objects.NONE &&
                myEditor.currentObject != currentObj)
        {
            disableDrawing(myEditor);
            myEditor.currentObject = currentObj;
            enableDrawing(myEditor);
        }
        else {
            disableDrawing(myEditor);
            contentPane.remove(myEditor);
        }
    }

    private void enableDrawing(MyEditor myEditor) {
        isDrawingEnabled = true;
        setTitle("Lab4 - " + myEditor.getChosenObject());
        if (myEditor.getMouseListeners().length == 0) {
            myEditor.drawShape();
        }
        else {
            myEditor.removeMouseListener(myEditor.getMouseListeners()[0]);
            myEditor.drawShape();
        }
    }

    private void disableDrawing(MyEditor myEditor) {
        isDrawingEnabled = false;
        myEditor.endDrawing();
        setTitle("Lab4 - Вибраний об'єкт: Немає");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}