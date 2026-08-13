package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SanPhamPanel extends JPanel {

    private final SanPhamBUS bus = new SanPhamBUS();
    private JTable tbl;
    private DefaultTableModel model;
    private JTextField txtMaSp, txtTenSp, txtDonGia, txtSoLuong, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public SanPhamPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        loadData();
    }

    private void initComponents() {
        // --- Form nhap lieu ---
        JPanel formPanel = new JPanel(new java.awt.GridLayout(2, 4, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));
        formPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 100));

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();

        formPanel.add(new JLabel("Ma SP:"));
        formPanel.add(txtMaSp);
        formPanel.add(new JLabel("Ten SP:"));
        formPanel.add(txtTenSp);
        formPanel.add(new JLabel("Don gia:"));
        formPanel.add(txtDonGia);
        formPanel.add(new JLabel("So luong:"));
        formPanel.add(txtSoLuong);

        // --- Bang du lieu ---
        String[] cols = {"Ma SP", "Ten san pham", "Don gia", "So luong"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tbl = new JTable(model);
        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromRow();
        });
        JScrollPane scroll = new JScrollPane(tbl);

        // --- Nut chuc nang ---
        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");
        btnTimKiem = new JButton("Tim kiem");
        txtTimKiem = new JTextField(20);

        JPanel btnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        btnPanel.add(new JLabel("Tim:"));
        btnPanel.add(txtTimKiem);
        btnPanel.add(btnTimKiem);

        add(formPanel);
        add(scroll);
        add(btnPanel);

        // --- Su kien ---
        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnLamMoi.addActionListener(e -> { clearForm(); loadData(); });
        btnTimKiem.addActionListener(e -> timKiem());
    }

    private void loadData() {
        SwingWorker<List<SanPham>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<SanPham> doInBackground() throws Exception {
                return bus.findAll();
            }
            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (SanPham sp : get()) {
                        model.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(),
                                sp.getDonGia(), sp.getSoLuong()});
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(SanPhamPanel.this, "Loi load: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void them() {
        try {
            SanPham sp = buildSanPham();
            sp.setMaSp(0);
            bus.save(sp);
            MessageUtil.showSuccess(this, "Them san pham thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showError(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi SQL: " + ex.getMessage());
        }
    }

    private void sua() {
        int row = tbl.getSelectedRow();
        if (row < 0) { MessageUtil.showError(this, "Chon dong can sua!"); return; }
        try {
            SanPham sp = buildSanPham();
            sp.setMaSp((int) model.getValueAt(row, 0));
            bus.save(sp);
            MessageUtil.showSuccess(this, "Cap nhat thanh cong!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showError(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi SQL: " + ex.getMessage());
        }
    }

    private void xoa() {
        int row = tbl.getSelectedRow();
        if (row < 0) { MessageUtil.showError(this, "Chon dong can xoa!"); return; }
        if (!MessageUtil.confirm(this, "Ban co muon xoa san pham nay?")) return;
        try {
            int maSp = (int) model.getValueAt(row, 0);
            bus.delete(maSp);
            MessageUtil.showSuccess(this, "Xoa thanh cong!");
            clearForm();
            loadData();
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi SQL: " + ex.getMessage());
        }
    }

    private void timKiem() {
        String kw = txtTimKiem.getText().trim();
        SwingWorker<List<SanPham>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<SanPham> doInBackground() throws Exception {
                if (kw.isEmpty()) return bus.findAll();
                return bus.searchByName(kw);
            }
            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (SanPham sp : get()) {
                        model.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(),
                                sp.getDonGia(), sp.getSoLuong()});
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(SanPhamPanel.this, "Loi tim kiem: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void fillFormFromRow() {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            txtMaSp.setText(String.valueOf(model.getValueAt(row, 0)));
            txtTenSp.setText(String.valueOf(model.getValueAt(row, 1)));
            txtDonGia.setText(String.valueOf(model.getValueAt(row, 2)));
            txtSoLuong.setText(String.valueOf(model.getValueAt(row, 3)));
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtTimKiem.setText("");
        tbl.clearSelection();
    }

    private SanPham buildSanPham() {
        return new SanPham(
                0,
                txtTenSp.getText().trim(),
                new BigDecimal(txtDonGia.getText().trim()),
                Integer.parseInt(txtSoLuong.getText().trim())
        );
    }
}
