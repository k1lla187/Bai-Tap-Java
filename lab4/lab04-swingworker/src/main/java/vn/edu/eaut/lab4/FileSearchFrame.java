package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileSearchFrame extends JFrame {
    private File selectedFile;
    private JButton btnChoose;
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JTextArea txtResult;
    private JLabel lblFile;
    private JProgressBar progressBar;
    private SwingWorker<List<String>, Integer> currentWorker;

    public FileSearchFrame() {
        setTitle("Bai 7 - Tim kiem tu khoa trong file");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblFile = new JLabel("Chua chon file");
        btnChoose = new JButton("Chon file");
        txtKeyword = new JTextField(20);
        btnSearch = new JButton("Tim kiem");
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(lblFile);
        topPanel.add(btnChoose);

        JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        midPanel.add(new JLabel("Tu khoa:"));
        midPanel.add(txtKeyword);
        midPanel.add(btnSearch);

        JScrollPane scrollPane = new JScrollPane(txtResult);

        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getName());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file truoc");
            return;
        }

        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap tu khoa");
            return;
        }

        btnSearch.setEnabled(false);
        txtResult.setText("");
        progressBar.setValue(0);

        currentWorker = new SwingWorker<>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                List<String> results = new ArrayList<>();
                List<String> matchingLines = new ArrayList<>();
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                int lineNum = 0;

                try (BufferedReader reader = Files.newBufferedReader(
                        selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNum++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;

                        if (line.toLowerCase().contains(keyword.toLowerCase())) {
                            matchingLines.add(String.format("%d: %s", lineNum, line));
                        }

                        int progress = totalBytes == 0 ? 100
                            : (int) Math.min(100, (readBytes * 100 / totalBytes));
                        setProgress(progress);
                    }
                }

                results.add("Tim thay " + matchingLines.size() + " dong chua tu khoa '" + keyword + "':\n");
                results.addAll(matchingLines);
                return results;
            }

            @Override
            protected void done() {
                try {
                    List<String> results = get();
                    txtResult.setText(String.join("\n", results));
                } catch (Exception ex) {
                    txtResult.setText("Loi khi doc file");
                }
                progressBar.setValue(100);
                btnSearch.setEnabled(true);
            }
        };

        currentWorker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        currentWorker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileSearchFrame().setVisible(true));
    }
}
