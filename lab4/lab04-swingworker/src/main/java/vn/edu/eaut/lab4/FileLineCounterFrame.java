package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {
    private File selectedFile;
    private JButton btnChoose;
    private JButton btnCount;
    private JLabel lblFile;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public FileLineCounterFrame() {
        setTitle("Bai 5 - Dem so dong file");
        setSize(500, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChoose = new JButton("Chon file");
        btnCount = new JButton("Dem dong");
        lblFile = new JLabel("Chua chon file");
        lblResult = new JLabel("So dong: ");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnChoose);
        btnPanel.add(btnCount);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(lblFile);
        panel.add(btnPanel);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        btnChoose.addActionListener(e -> chooseFile());
        btnCount.addActionListener(e -> countLines());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getName());
        }
    }

    private void countLines() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file truoc");
            return;
        }

        btnCount.setEnabled(false);
        btnChoose.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Dang doc file...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                long lines = 0;

                try (BufferedReader reader = Files.newBufferedReader(
                        selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        int progress = totalBytes == 0 ? 100
                            : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                    }
                }
                return lines;
            }

            @Override
            protected void done() {
                try {
                    long lineCount = get();
                    lblResult.setText("So dong: " + lineCount);
                } catch (Exception ex) {
                    lblResult.setText("Loi khi doc file");
                }
                progressBar.setValue(100);
                btnCount.setEnabled(true);
                btnChoose.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileLineCounterFrame().setVisible(true));
    }
}
