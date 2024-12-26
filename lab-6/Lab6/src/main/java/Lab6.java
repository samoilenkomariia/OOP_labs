import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab6 extends JFrame {

    private final ProcessBuilder pb2 = new ProcessBuilder("java", "-jar",
            "/home/shoni/IdeaProjects/OOPLab6Gradle/Object2/build/libs/Object2.jar");
    private final ProcessBuilder pb3 = new ProcessBuilder("java", "-jar",
            "/home/shoni/IdeaProjects/OOPLab6Gradle/Object3/build/libs/Object3.jar");
    private Process process2 = null;
    private Process process3 = null;
    private String object2 = "/home/shoni/IdeaProjects/OOPLab6Gradle/object2.txt";
    private String object3 = "/home/shoni/IdeaProjects/OOPLab6Gradle/object3.txt";
    private String indicator = "/home/shoni/IdeaProjects/OOPLab6Gradle/indicator.flag";

    public Lab6() {
        setTitle("Lab6");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 500);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu workMenu = new JMenu("Робота");
        menuBar.add(workMenu);

        JMenuItem startMenuItem = new JMenuItem("Почати");
        workMenu.add(startMenuItem);

        setVisible(true);
        openInputDialog(workMenu);
        writeFile(object2, "");
        writeFile(object3, "");
        writeFile(indicator, "");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        Lab6.this,
                        "Закрити програму?",
                        "Вихід",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cleanUp();
                    System.exit(0);
                }
            }
        });
    }

    private void cleanUp() {
        File indicatorF = new File(indicator);
        if (indicatorF.exists()) indicatorF.delete();
    }

    private void openInputDialog(JMenu menu) {
        menu.getItem(0).addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Уведіть дані", true);
            dialog.setSize(250, 150);
            dialog.setLayout(new BorderLayout());
            dialog.setLocationRelativeTo(this);

            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel labelN = new JLabel("Введіть n:");
            JTextField inputN = new JTextField();
            JLabel labelMin = new JLabel("Введіть Min:");
            JTextField inputMin = new JTextField();
            JLabel labelMax = new JLabel("Введіть Max:");
            JTextField inputMax = new JTextField();

            inputPanel.add(labelN);
            inputPanel.add(inputN);
            inputPanel.add(labelMin);
            inputPanel.add(inputMin);
            inputPanel.add(labelMax);
            inputPanel.add(inputMax);

            JPanel buttonPanel = new JPanel(new BorderLayout());
            JButton cancelButton = new JButton("Скасувати");
            JButton execButton = new JButton("Виконати");

            buttonPanel.add(cancelButton, BorderLayout.WEST);
            buttonPanel.add(execButton, BorderLayout.EAST);

            cancelButton.addActionListener(e1 -> dialog.dispose());
            execButton.addActionListener(e1 -> {
                try {
                    int n = Integer.parseInt(inputN.getText().trim());
                    double min = Double.parseDouble(inputMin.getText().trim());
                    double max = Double.parseDouble(inputMax.getText().trim());
                    if (min <= max) {
                        if (process2 != null && !process2.isAlive()) {
                            deleteProcess(process2);
                            process2 = null;
                        }
                        if (process3 != null && !process3.isAlive()) {
                            deleteProcess(process3);
                            process3 = null;
                        }
                        String result = n + " " + min + " " + max;
                        sendData(result);
                        dialog.dispose();
                        startProcesses();
                        startPolling(object2, result);
                        writeFile(object2, result);
                        startPolling(object3, "REFRESH");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Введіть коректні дані", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.add(inputPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            dialog.setVisible(true);
        });
    }

    private void deleteProcess(Process process) {
        if (process != null && !process.isAlive()) {
            process.destroy();
        }
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

    private void startProcess1() {
        try {
            if (process2 == null) {
                process2 = pb2.start();
            }
            else if (!process2.isAlive()) {
                deleteProcess(process2);
                process2 = pb2.start();
            }
//            writeFile(object4, "REFRESH");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void startProcess2() {
        try {
            if (process3 == null) {
                process3 = pb3.start();
            }
            else if (!process3.isAlive()) {
                deleteProcess(process3);
                process3 = pb3.start();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void startProcesses() {
        startProcess1();
        startProcess2();
    }

    private void sendData (String data) {
        StringSelection stringSelection = new StringSelection(data);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private final ExecutorService exS = Executors.newSingleThreadExecutor();
    private volatile long lastModifiedTime = 0;

    public void startPolling(String filePath, String message) {
        exS.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    checkFileChanges(filePath, message);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Polling interrupted.");
            }
        });
    }

    private void checkFileChanges(String filePath, String message) {
        Path path = Paths.get(filePath);
        try {
            long currentModifiedTime = Files.getLastModifiedTime(path).toMillis();
            if (currentModifiedTime != lastModifiedTime) {
                lastModifiedTime = currentModifiedTime;
                onFileChange(filePath, message);
            }
        } catch (IOException e) {
            System.err.println("Error checking file changes: " + e.getMessage());
        }
    }

    public void stopPolling() {
        exS.shutdownNow();
    }

    private void onFileChange(String filePath, String message) {
        String content2 = readFile(filePath);
        if (content2.equals(message)) {
            startProcess1();
            startProcess2();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lab6::new);
    }
}