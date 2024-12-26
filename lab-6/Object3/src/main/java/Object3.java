import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Object3 extends JFrame {

    File indicator = new File("/home/shoni/IdeaProjects/OOPLab6Gradle/indicator.flag");
    private String data = "";
    private boolean isTableClear = true;
    JTable table;
    DefaultTableModel tableModel;
    String currentTableData = "";
    private String object3 = "/home/shoni/IdeaProjects/OOPLab6Gradle/object3.txt";

    public Object3() {
        setTitle("Object3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        monitorIndicator();
        setSize(400, 300);
        setLocation(800, 400);
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("");
        tableModel.addColumn("");
        try {
            data = getClipboardData();
            if (data != null) {
                createTable(data);
            }
            startPolling(object3);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to read data " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }

    private void monitorIndicator() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            if (!indicator.exists()) {
                System.exit(0); // Завершення програми
            }
        }, 0, 1, TimeUnit.SECONDS); // Перевірка кожну секунду
    }


    private void writeFile(String filePath, String message) {
        try (FileOutputStream fos = new FileOutputStream(filePath, false);
             FileChannel channel = fos.getChannel();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            FileLock lock = channel.lock();
            try {
                writer.write(message);
                writer.newLine();
            } finally {
                lock.release();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to the file: "
                    + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String readFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            return reader.readLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read data" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void createTable(String data) {
        createTableFromData(data);
        if (isTableClear) {
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(200, 100));
            add(scrollPane, BorderLayout.CENTER);
            isTableClear = false;
        }
    }

    private void clearTable (JTable table) {
        if (table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            writeFile(object3, "");
        }
    }

    private String getClipboardData() throws UnsupportedFlavorException, IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        }
        return null;
    }

    private void createTableFromData(String data) {
        currentTableData = data;
        String[] params = data.split("\\s+"); // {n, Min, Max}
        double[] vector = new double[Integer.parseInt(params[0])];
        for (int i = 1; i < params.length; i++) {
            try {
                vector[i-1] = Double.parseDouble(params[i]);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Failed to parse data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        Arrays.sort(vector);

        for (int i = 1; i <= vector.length; i++) {
            tableModel.addRow(new Object[]{i, vector[i-1]});
        }

        table = new JTable(tableModel);
    }


    private final ExecutorService exS = Executors.newSingleThreadExecutor();
    private volatile long lastModifiedTime = 0;

    public void startPolling(String filePath) {
        exS.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(500);
                    checkFileChanges(filePath);
                }
            } catch (InterruptedException e) {
                System.out.println("Polling interrupted.");
            }
        });
    }

    private void checkFileChanges(String filePath) {
        Path path = Paths.get(filePath);
        try {
            long currentModifiedTime = Files.getLastModifiedTime(path).toMillis();
            if (currentModifiedTime != lastModifiedTime) {
                lastModifiedTime = currentModifiedTime;
                onFileChange(filePath);
            }
        } catch (IOException e) {
            System.err.println("Error checking file changes: " + e.getMessage());
        }
    }

    public void stopPolling() {
        exS.shutdownNow();
    }

    private void onFileChange(String filePath) {
        String content = null;
        String file = readFile(filePath);
        try {
            content = getClipboardData();
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (content != null) {
            try {
                if (file.equals("REFRESH") && !currentTableData.equals(content)) {
                    clearTable(table);
                    createTableFromData(getClipboardData());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to create table" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Object3::new);
    }
}
