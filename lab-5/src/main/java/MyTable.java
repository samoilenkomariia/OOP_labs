import shape_editor.Callback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyTable extends JFrame {

    private static MyTable instance;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private Callback callback;
    private final ArrayList<Object[]> tableData = new ArrayList<>();
    private final File defaultFile = new File("myFile.txt");
    private File file = defaultFile;

    private MyTable() {
        setTitle("Таблиця об'єктів");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Об'єкт", "x1", "y1", "x2", "y2"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Видалити");
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        deleteButton.addActionListener(e -> {
            if (this.callback != null) {
                this.callback.deleteSelectedShapes();
            }
        });
        writeFile(defaultFile);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
        tableData.add(rowData);
        writeFile(defaultFile);
    }

    public void writeFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, false))) {
            writer.write("Об'єкт\tx1\ty1\tx2\ty2");
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to the file: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        for (Object[] rowData : tableData) {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file, true))) {
                StringBuilder row = new StringBuilder();
                for (Object cell : rowData) {
                    row.append(cell.toString()).append("\t");
                }
                writer.write(row.toString().trim());
                writer.newLine();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to the file: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void deleteRow(int index) {
        tableModel.removeRow(index);
        tableData.remove(index);
        writeFile(defaultFile);
    }

    public JTable getTable() {
        return table;
    }

    public void clearTable() {
        tableModel.setRowCount(0);
        tableData.clear();
        writeFile(defaultFile);
    }

    public static MyTable getInstance() {
        if (instance == null) {
            instance = new MyTable();
        }
        return instance;
    }
}