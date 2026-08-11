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
        topPanel.add(new JLabel("File:"));
        topPanel.add(lblFile);
        topPanel.add(btnChoose);

        JPanel keywordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keywordPanel.add(new JLabel("Tu khoa:"));
        keywordPanel.add(txtKeyword);
        keywordPanel.add(btnSearch);

        JScrollPane scrollPane = new JScrollPane(txtResult);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(keywordPanel, BorderLayout.NORTH);
        centerContainer.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText(selectedFile.getName());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file!");
            return;
        }
        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap tu khoa!");
            return;
        }

        btnSearch.setEnabled(false);
        btnChoose.setEnabled(false);
        txtResult.setText("Dang tim kiem...\n");
        progressBar.setValue(0);

        SwingWorker<List<String>, Integer> worker = new SwingWorker<>() {
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
                            matchingLines.add(lineNum + ": " + line);
                        }

                        int progress = (int) Math.min(100, (readBytes * 100 / totalBytes));
                        publish(progress);
                    }
                }

                results.add("=== Ket qua tim kiem '" + keyword + "' ===");
                results.add("Tim thay " + matchingLines.size() + " dong:");
                results.addAll(matchingLines);
                return results;
            }

            @Override
            protected void process(List<Integer> chunks) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                try {
                    List<String> results = get();
                    txtResult.setText(String.join("\n", results));
                } catch (Exception ex) {
                    txtResult.setText("Loi: " + ex.getMessage());
                }
                progressBar.setValue(100);
                btnSearch.setEnabled(true);
                btnChoose.setEnabled(true);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileSearchFrame().setVisible(true));
    }
}