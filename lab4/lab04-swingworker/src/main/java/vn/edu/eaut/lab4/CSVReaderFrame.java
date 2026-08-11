package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderFrame extends JFrame {
    private File selectedFile;
    private JButton btnChoose;
    private JButton btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblFile;
    private JLabel lblStats;
    private JProgressBar progressBar;

    public CSVReaderFrame() {
        setTitle("Bai 8 - Doc CSV diem sinh vien");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblFile = new JLabel("Chua chon file");
        lblStats = new JLabel("Chua co du lieu");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        btnChoose = new JButton("Chon file CSV");
        btnLoad = new JButton("Doc du lieu");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(lblFile);
        topPanel.add(btnChoose);
        topPanel.add(btnLoad);
        topPanel.add(progressBar);
        topPanel.add(lblStats);

        String[] columns = {"Ma SV", "Ho ten", "Diem TB", "Xep loai"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnLoad.addActionListener(e -> loadData());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getName());
        }
    }

    private void loadData() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file truoc");
            return;
        }

        btnLoad.setEnabled(false);
        btnChoose.setEnabled(false);
        progressBar.setValue(0);
        tableModel.setRowCount(0);

        SwingWorker<List<String[]>, Integer> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> data = new ArrayList<>();
                List<String> lines = Files.readAllLines(selectedFile.toPath(), StandardCharsets.UTF_8);
                int total = lines.size();

                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            data.add(parts);
                        }
                    }
                    int progress = (int) ((i * 100.0) / total);
                    setProgress(progress);
                }
                return data;
            }

            @Override
            protected void done() {
                try {
                    List<String[]> data = get();
                    for (String[] row : data) {
                        String diemStr = row.length > 2 ? row[2].trim() : "0";
                        try {
                            double diem = Double.parseDouble(diemStr);
                            String xepLoai = xepLoai(diem);
                            tableModel.addRow(new Object[]{row[0].trim(), row[1].trim(), diemStr, xepLoai});
                        } catch (NumberFormatException ex) {
                            tableModel.addRow(new Object[]{row[0].trim(), row[1].trim(), diemStr, "?"});
                        }
                    }

                    if (!data.isEmpty()) {
                        double maxDiem = 0;
                        String topStudent = "";
                        double sum = 0;
                        for (String[] row : data) {
                            try {
                                double diem = Double.parseDouble(row[2].trim());
                                sum += diem;
                                if (diem > maxDiem) {
                                    maxDiem = diem;
                                    topStudent = row[1].trim();
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                        double avg = sum / data.size();
                        lblStats.setText(String.format("SV: %d | TB: %.2f | Cao nhat: %s (%.2f)",
                            data.size(), avg, topStudent, maxDiem));
                    }
                } catch (Exception ex) {
                    lblStats.setText("Loi khi doc file");
                }
                progressBar.setValue(100);
                btnLoad.setEnabled(true);
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

    private String xepLoai(double diem) {
        if (diem >= 8.5) return "Gioi";
        if (diem >= 7.0) return "Kha";
        if (diem >= 5.0) return "Trung binh";
        return "Yeu";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CSVReaderFrame().setVisible(true));
    }
}
