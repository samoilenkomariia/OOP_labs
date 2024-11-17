import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;
import shape_editor.Callback;
import shape_editor.MyEditor;
import shape_editor.shapes.Shape;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class Main extends JFrame implements Callback {

    private final String labNum = "Lab5";
    private final JPanel contentPane;
    private final JMenuItem exitItem, pointItem, lineItem,
            rectangularItem, ellipseItem,
            lineOOItem, cubeItem, eraseItem, undoItem,
            redoItem, openTableItem, closeTableItem,
            saveFileItem,openFileItem, newFileItem;
    private final JToolBar toolBar;
    private boolean isDrawingEnabled = false;
    private final JButton pointButton, lineButton, rectangleButton,
            ellipseButton, eraseButton, undoButton, redoButton,
            lineOOButton, cubeButton, tableButton;
    private final MyTable myTable = MyTable.getInstance();
    private final MyEditor myEditor = MyEditor.getInstance();

    public Main() {
        setTitle(labNum + " - Вибраний об'єкт: Немає");
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
            JMenu tableMenu = new JMenu("Таблиця");

            menuBar.add(fileMenu);
            menuBar.add(objectsMenu);
            menuBar.add(helpMenu);
            menuBar.add(tableMenu);

            openFileItem = new JMenuItem("Відкрити файл");
            saveFileItem = new JMenuItem("Зберегти файл");
            newFileItem = new JMenuItem("Новий файл");
            exitItem = new JMenuItem("Закрити програму");
            eraseItem = new JMenuItem("Очистити");
            undoItem = new JMenuItem("Скасувати");
            redoItem = new JMenuItem("Повернути");
            fileMenu.add(saveFileItem);
            fileMenu.add(openFileItem);
            fileMenu.add(newFileItem);
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

            openTableItem = new JMenuItem("Відкрити таблицю");
            closeTableItem = new JMenuItem("Закрити таблицю");
            tableMenu.add(openTableItem);
            tableMenu.add(closeTableItem);
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
            tableButton = createButton(FontIcon.of(MaterialDesign.MDI_TABLE, 24), "Таблиця");

            contentPane.add(toolBar, BorderLayout.NORTH);
        }

        setVisible(true);

        myEditor.setCallback(this);
        myEditor.setBounds(0, toolBar.getHeight(), getWidth(),
                getHeight() - toolBar.getHeight());
        contentPane.add(myEditor);

        manageMenuItemsActions();
        manageToolBarEvents();
    }

    private JButton createButton(Icon icon, String tooltip) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        toolBar.add(button);
        return button;
    }

    private void manageMenuItemsActions() {
        exitItem.addActionListener(event -> System.exit(0));

        openTableItem.addActionListener(event -> {
            manageTable(MyTable.getInstance());
            MyTable.getInstance().setVisible(true);
        });

        closeTableItem.addActionListener(event -> {
            MyTable.getInstance().clearTable();
            MyTable.getInstance().dispose();
        });

        openFileItem.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Оберіть файл");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Текстові файли", "txt"));
            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    processSelectedFile(selectedFile);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Будь ласка, виберіть файл з розширенням .txt",
                            "Неправильний файл",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveFileItem.addActionListener(event -> {
            MyTable myTable = MyTable.getInstance();
            try {
                if (!myTable.getFile().getPath().equals("myFile.txt")) {
                    myTable.writeFile(myTable.getFile());
                } else {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Зберегти файл");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Текстові файли", "txt"));
                    int userSelection = fileChooser.showSaveDialog(this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                            fileToSave = new File(fileToSave.getPath() + ".txt");
                        }
                        myTable.setFile(fileToSave);
                        myTable.writeFile(myTable.getFile());
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Сталася несподівана помилка: " + e.getMessage(),
                        "Помилка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        newFileItem.addActionListener(event -> {
            myTable.setFile(new File("myFile.txt"));
            myTable.clearTable();
            myEditor.showedShapes.clear();
            myEditor.lastDeletedShapes.clear();
            myEditor.repaint();
        });

        {
            eraseItem.addActionListener(event -> eraseAllObjects(myEditor));

            undoItem.addActionListener(event -> undoDeletingLastShape(myEditor));

            redoItem.addActionListener(event -> redoDeletingShape(myEditor));

            pointItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.POINT, myEditor));

            lineItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.LINE, myEditor));

            rectangularItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.RECTANGLE, myEditor));

            ellipseItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.ELLIPSE, myEditor));

            lineOOItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.LINEOO, myEditor));

            cubeItem.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.CUBE, myEditor));
        }
    }

    private void processSelectedFile(File file) {
        myTable.setFile(file);
        myEditor.showedShapes.clear();
        myEditor.lastDeletedShapes.clear();
        myEditor.repaint();
        myTable.clearTable();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (line.contains("\t")) {
                    String[] data = line.split("\t");
                    MyEditor.objects shapeName = getEnumShapeName(data[0]);
                    int startX = Integer.parseInt(data[1]);
                    int startY = Integer.parseInt(data[2]);
                    int endX = Integer.parseInt(data[3]);
                    int endY = Integer.parseInt(data[4]);
                    myEditor.drawFileContent(shapeName, startX, startY, endX, endY);
                    myTable.addRow(new Object[]{data[0], startX, startY, endX, endY});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MyEditor.objects getEnumShapeName(String shapeName) {
        switch (shapeName) {
            case "Крапка" -> {
                return MyEditor.objects.POINT;
            }
            case "Лінія" -> {
                return MyEditor.objects.LINE;
            }
            case "Прямокутник" -> {
                return MyEditor.objects.RECTANGLE;
            }
            case "Еліпс" -> {
                return MyEditor.objects.ELLIPSE;
            }
            case "Відрізок" -> {
                return MyEditor.objects.LINEOO;
            }
            case "Куб" -> {
                return MyEditor.objects.CUBE;
            }
            default -> {
                return MyEditor.objects.NONE;
            }
        }
    }

    private void eraseAllObjects(MyEditor myEditor) {
        myEditor.lastDeletedShapes.addAll(myEditor.showedShapes);
        myEditor.showedShapes.clear();
        myEditor.deletedAll = true;
        contentPane.repaint();
        manageTable(myTable);
    }

    private void undoDeletingLastShape(MyEditor myEditor) {
        if (!myEditor.showedShapes.isEmpty()) {
            myEditor.lastDeletedShapes.add(myEditor.showedShapes
                    .get(myEditor.showedShapes.size()-1));
            myEditor.showedShapes.remove(myEditor.showedShapes.size()-1);
            contentPane.repaint();
        }
        manageTable(myTable);
    }

    private void redoDeletingShape(MyEditor myEditor) {
        if (!myEditor.lastDeletedShapes.isEmpty()) {
            if (!myEditor.deletedAll) {
                myEditor.showedShapes.add(myEditor.lastDeletedShapes
                        .get(myEditor.lastDeletedShapes.size() - 1));
                myEditor.lastDeletedShapes.remove(myEditor.lastDeletedShapes.size() - 1);
                contentPane.repaint();
            } else {
                myEditor.showedShapes.addAll(myEditor.lastDeletedShapes);
                myEditor.lastDeletedShapes.clear();
                myEditor.deletedAll = false;
                contentPane.repaint();
            }
        }
        manageTable(myTable);
    }

    private void manageTable(MyTable myTable) {
        myTable.setCallback(this);
        onShapeSelected();
        myTable.clearTable();
        for(Shape shape: MyEditor.getInstance().showedShapes) {
            myTable.addRow(new Object[]{ shape.getShapeName(), shape.getStartX(),
                    shape.getStartY(), shape.getEndX(), shape.getEndY() });
        }
    }

    @Override
    public void onShapeSelected() {
        myTable.getTable().getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int[] selectedRows = myTable.getTable().getSelectedRows();
                MyEditor myEditor = MyEditor.getInstance();
                for (Shape shape : myEditor.showedShapes) {
                    shape.isSelected(false);
                }
                for (int row : selectedRows) {
                    if (row >= 0 && row < myEditor.showedShapes.size()) {
                        myEditor.showedShapes.get(row).isSelected(true);
                    }
                }
                myEditor.repaint();
            }
        });
    }

    @Override
    public void onShapeAdded(Shape shape) {
        myTable.addRow(new Object[]{
                shape.getShapeName(),
                shape.getStartX(),
                shape.getStartY(),
                shape.getEndX(),
                shape.getEndY()
        });
        manageTable(myTable);
    }

    @Override
    public void deleteSelectedShapes() {
        MyEditor myEditor = MyEditor.getInstance();
        for (int i = myEditor.showedShapes.size() - 1; i >= 0; i--) {
            if (myEditor.showedShapes.get(i).isSelected()) {
                myEditor.showedShapes.get(i).isSelected(false);
                myEditor.lastDeletedShapes.add(myEditor.showedShapes.get(i));
                myEditor.showedShapes.remove(i);
                myTable.deleteRow(i);
            }
        }
    }

    private void manageToolBarEvents() {
        tableButton.addActionListener(event -> {
            if (!myTable.isVisible()) {
                manageTable(myTable);
                myTable.setVisible(true);
            }
            else {
                myTable.clearTable();
                myTable.dispose();
            }
        });

        pointButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.POINT, myEditor));

        lineButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.LINE, myEditor));

        rectangleButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.RECTANGLE, myEditor));

        ellipseButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.ELLIPSE, myEditor));

        lineOOButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.LINEOO, myEditor));

        cubeButton.addActionListener(event -> chooseObjectToDraw(MyEditor.objects.CUBE, myEditor));

        eraseButton.addActionListener(event -> eraseAllObjects(myEditor));

        undoButton.addActionListener(event -> undoDeletingLastShape(myEditor));

        redoButton.addActionListener(event -> redoDeletingShape(myEditor));
    }

    private void chooseObjectToDraw(MyEditor.objects currentObj, MyEditor myEditor) {
        if (!isDrawingEnabled) {
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
        else disableDrawing(myEditor);
    }

    private void enableDrawing(MyEditor myEditor) {
        isDrawingEnabled = true;
        setTitle(labNum + " - " + myEditor.getChosenObject());
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
        setTitle(labNum + " - Вибраний об'єкт: Немає");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}