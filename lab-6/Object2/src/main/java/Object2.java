import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Object2 extends JFrame {

    File indicator = new File("/home/shoni/IdeaProjects/OOPLab6Gradle/indicator.flag");
    JTable table;
    DefaultTableModel tableModel;
    boolean isTableClear = true;
    String currentTableData = "";
    private String object2 = "/home/shoni/IdeaProjects/OOPLab6Gradle/object2.txt";
    private String object3 = "/home/shoni/IdeaProjects/OOPLab6Gradle/object3.txt";
    private String update = "REFRESH";

    public Object2() {
        setTitle("Object2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        monitorIndicator();
        setSize(400, 300);
        setLocation(800, 0);
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("");
        tableModel.addColumn("");
        try {
            String message = readFile(object2);
            if (message != null) {
                createTable(message);
            }
            startPolling(object2);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to read data" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private void clearTable (JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
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

    private void createTableFromData(String data) {
        currentTableData = data;
        String[] params = data.split("\\s+"); // {n, Min, Max}
        int n = Integer.parseInt(params[0]);
        double min = Double.parseDouble(params[1]);
        double max = Double.parseDouble(params[2]);
        double[] vector = new double[n];

        for (int i = 1; i <= n; i++) {
            double r = min + (max - min) * Math.random();
            DecimalFormat df = new DecimalFormat("#.00");
            r = Double.parseDouble(df.format(r));
            vector[i-1] = r;
            tableModel.addRow(new Object[]{i, r});
        }

        StringBuilder result = new StringBuilder();
        result.append(n).append(" ");
        for(int i = 0; i < n; i++) {
            result.append(vector[i]).append(" ");
        }
        StringSelection stringSelection = new StringSelection(result.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        table = new JTable(tableModel);
        writeFile(object3, update);
    }

    private final ExecutorService exS = Executors.newSingleThreadExecutor();
    private volatile long lastModifiedTime = 0;

    public void startPolling(String filePath) {
        exS.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    checkFileChanges(filePath);
                    Thread.sleep(250);
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
        String content = readFile(filePath);
        if (content != null && content.contains(" ")) {
            try {
                if (!currentTableData.equals(content)) {
                    clearTable(table);
                    createTableFromData(content);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to create table" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Object2::new);
    }
}


