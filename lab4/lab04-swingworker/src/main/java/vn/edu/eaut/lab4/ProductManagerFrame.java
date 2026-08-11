package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProductManagerFrame extends JFrame {
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnSave;
    private JButton btnOpen;
    private JProgressBar progressBar;
    private List<String[]> products = new ArrayList<>();
    private String csvFilePath = "products.csv";

    public ProductManagerFrame() {
        setTitle("Bai 10 - Quan ly san pham CSV");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Ma SP:"), gbc);
        gbc.gridx = 1;
        txtMaSP = new JTextField(15);
        inputPanel.add(txtMaSP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Ten SP:"), gbc);
        gbc.gridx = 1;
        txtTenSP = new JTextField(15);
        inputPanel.add(txtTenSP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Don gia:"), gbc);
        gbc.gridx = 1;
        txtDonGia = new JTextField(15);
        inputPanel.add(txtDonGia, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Them");
        btnUpdate = new JButton("Sua");
        btnDelete = new JButton("Xoa");
        btnRefresh = new JButton("Lam moi");
        btnSave = new JButton("Luu CSV");
        btnOpen = new JButton("Mo CSV");
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnSave);
        btnPanel.add(btnOpen);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(btnPanel, gbc);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        String[] columns = {"Ma SP", "Ten SP", "Don gia"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnRefresh.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> saveToCSV());
        btnOpen.addActionListener(e -> openCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaSP.setText((String) tableModel.getValueAt(selectedRow, 0));
                    txtTenSP.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtDonGia.setText((String) tableModel.getValueAt(selectedRow, 2));
                    txtMaSP.setEnabled(false);
                }
            }
        });

        loadSampleData();
    }

    private void loadSampleData() {
        products.add(new String[]{"SP01", "Ban phim", "250000"});
        products.add(new String[]{"SP02", "Chuot", "150000"});
        products.add(new String[]{"SP03", "Man hinh", "2500000"});
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (String[] p : products) {
            tableModel.addRow(p);
        }
    }

    private void addProduct() {
        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        String donGia = txtDonGia.getText().trim();

        if (maSP.isEmpty() || tenSP.isEmpty() || donGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
            return;
        }

        for (String[] p : products) {
            if (p[0].equals(maSP)) {
                JOptionPane.showMessageDialog(this, "Ma san pham da ton tai!");
                return;
            }
        }

        try {
            Double.parseDouble(donGia);
            products.add(new String[]{maSP, tenSP, donGia});
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Them san pham thanh cong!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Don gia phai la so!");
        }
    }

    private void updateProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham can sua!");
            return;
        }

        String tenSP = txtTenSP.getText().trim();
        String donGia = txtDonGia.getText().trim();

        if (tenSP.isEmpty() || donGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
            return;
        }

        try {
            Double.parseDouble(donGia);
            products.set(selectedRow, new String[]{
                (String) tableModel.getValueAt(selectedRow, 0), tenSP, donGia
            });
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Sua san pham thanh cong!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Don gia phai la so!");
        }
    }

    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham can xoa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac chan muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            products.remove(selectedRow);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Xoa san pham thanh cong!");
        }
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtMaSP.setEnabled(true);
        table.clearSelection();
        txtMaSP.requestFocus();
    }

    private void saveToCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("products.csv"));
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        btnSave.setEnabled(false);
        progressBar.setValue(0);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("Ma SP,Ten SP,Don gia\n");
                    int total = products.size();
                    for (int i = 0; i < products.size(); i++) {
                        String[] p = products.get(i);
                        writer.write(p[0] + "," + p[1] + "," + p[2] + "\n");
                        Thread.sleep(100);
                        setProgress((int) (((i + 1) * 100.0) / total));
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                btnSave.setEnabled(true);
                JOptionPane.showMessageDialog(ProductManagerFrame.this, "Luu file thanh cong!");
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void openCSV() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        btnOpen.setEnabled(false);
        progressBar.setValue(0);
        products.clear();

        SwingWorker<List<String[]>, Integer> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> data = new ArrayList<>();
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                int total = lines.size();

                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            data.add(parts);
                        }
                    }
                    setProgress((int) ((i * 100.0) / total));
                    Thread.sleep(50);
                }
                return data;
            }

            @Override
            protected void done() {
                try {
                    products.addAll(get());
                    refreshTable();
                    JOptionPane.showMessageDialog(ProductManagerFrame.this, "Doc " + products.size() + " san pham!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ProductManagerFrame.this, "Loi khi doc file!");
                }
                progressBar.setValue(100);
                btnOpen.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new ProductManagerFrame().setVisible(true));
    }
}
