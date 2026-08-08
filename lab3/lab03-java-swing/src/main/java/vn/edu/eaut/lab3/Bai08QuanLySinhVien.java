package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bai08QuanLySinhVien extends JFrame {
    private final JTextField txtMaSV = new JTextField(10);
    private final JTextField txtHoTen = new JTextField(20);
    private final JTextField txtDiemTB = new JTextField(10);
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final List<Student> danhSachSV = new ArrayList<>();

    public Bai08QuanLySinhVien() {
        setTitle("Bai 8 - Quan ly sinh vien");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel lblTitle = new JLabel("QUAN LY SINH VIEN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Ma sinh vien:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaSV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Ho va ten:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtHoTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Diem trung binh:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDiemTB, gbc);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        JButton btnRefresh = new JButton("Lam moi");
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(btnPanel, gbc);

        add(inputPanel, BorderLayout.WEST);

        // Table
        String[] columnNames = {"Ma SV", "Ho ten", "Diem TB", "Xep loai"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        btnAdd.addActionListener(e -> themSinhVien());
        btnUpdate.addActionListener(e -> suaSinhVien());
        btnDelete.addActionListener(e -> xoaSinhVien());
        btnRefresh.addActionListener(e -> lamMoi());

        // Table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaSV.setText((String) tableModel.getValueAt(selectedRow, 0));
                    txtHoTen.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtDiemTB.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
                }
            }
        });

        // Add sample data
        themMauDuLieu();

        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void themMauDuLieu() {
        danhSachSV.add(new Student("SV001", "Nguyen Van A", 8.5));
        danhSachSV.add(new Student("SV002", "Tran Thi B", 7.2));
        danhSachSV.add(new Student("SV003", "Le Van C", 5.5));
        capNhatBang();
    }

    private void themSinhVien() {
        String maSV = txtMaSV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String diemStr = txtDiemTB.getText().trim();

        if (maSV.isEmpty() || hoTen.isEmpty() || diemStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiem tra ma SV da ton tai
        for (Student sv : danhSachSV) {
            if (sv.getMaSV().equals(maSV)) {
                JOptionPane.showMessageDialog(this, "Ma sinh vien da ton tai!", "Loi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            double diemTB = Double.parseDouble(diemStr);
            if (diemTB < 0 || diemTB > 10) {
                JOptionPane.showMessageDialog(this, "Diem phai tu 0 den 10!", "Loi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student sv = new Student(maSV, hoTen, diemTB);
            danhSachSV.add(sv);
            capNhatBang();
            lamMoi();
            JOptionPane.showMessageDialog(this, "Them sinh vien thanh cong!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Diem phai la so!", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaSinhVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon sinh vien can sua!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maSV = txtMaSV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String diemStr = txtDiemTB.getText().trim();

        try {
            double diemTB = Double.parseDouble(diemStr);
            if (diemTB < 0 || diemTB > 10) {
                JOptionPane.showMessageDialog(this, "Diem phai tu 0 den 10!", "Loi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student sv = danhSachSV.get(selectedRow);
            sv.setHoTen(hoTen);
            sv.setDiemTB(diemTB);
            capNhatBang();
            lamMoi();
            JOptionPane.showMessageDialog(this, "Sua sinh vien thanh cong!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Diem phai la so!", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaSinhVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon sinh vien can xoa!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac chan muon xoa sinh vien nay?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            danhSachSV.remove(selectedRow);
            capNhatBang();
            lamMoi();
            JOptionPane.showMessageDialog(this, "Xoa sinh vien thanh cong!");
        }
    }

    private void lamMoi() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiemTB.setText("");
        txtMaSV.requestFocus();
        table.clearSelection();
    }

    private void capNhatBang() {
        tableModel.setRowCount(0);
        for (Student sv : danhSachSV) {
            tableModel.addRow(new Object[]{
                sv.getMaSV(),
                sv.getHoTen(),
                String.format("%.2f", sv.getDiemTB()),
                sv.xepLoai()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}
