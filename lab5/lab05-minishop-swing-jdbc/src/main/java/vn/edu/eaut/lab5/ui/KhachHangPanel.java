package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.sql.SQLException;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private final KhachHangBUS bus = new KhachHangBUS();
    private JTable tbl;
    private DefaultTableModel model;
    private JTextField txtMaKh, txtTenKh, txtSdt, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public KhachHangPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        loadData();
    }

    private void initComponents() {
        // --- Form nhap lieu ---
        JPanel formPanel = new JPanel(new java.awt.GridLayout(2, 4, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));
        formPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 100));

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtDiaChi = new JTextField();

        // SDT chi cho phep nhap toi da 10 chu so
        txtSdt = new JTextField();
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());

        formPanel.add(new JLabel("Ma KH:"));
        formPanel.add(txtMaKh);
        formPanel.add(new JLabel("Ten KH:"));
        formPanel.add(txtTenKh);
        formPanel.add(new JLabel("SDT:"));
        formPanel.add(txtSdt);
        formPanel.add(new JLabel("Dia chi:"));
        formPanel.add(txtDiaChi);

        // --- Bang du lieu ---
        String[] cols = {"Ma KH", "Ten khach hang", "SDT", "Dia chi"};
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
        SwingWorker<List<KhachHang>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<KhachHang> doInBackground() throws Exception {
                return bus.findAll();
            }
            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (KhachHang kh : get()) {
                        model.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(),
                                kh.getSdt(), kh.getDiaChi()});
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(KhachHangPanel.this, "Loi load: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void them() {
        try {
            KhachHang kh = buildKhachHang();
            kh.setMaKh(0);
            bus.save(kh);
            MessageUtil.showSuccess(this, "Them khach hang thanh cong!");
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
            KhachHang kh = buildKhachHang();
            kh.setMaKh((int) model.getValueAt(row, 0));
            bus.save(kh);
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
        if (!MessageUtil.confirm(this, "Ban co muon xoa khach hang nay?")) return;
        try {
            int maKh = (int) model.getValueAt(row, 0);
            bus.delete(maKh);
            MessageUtil.showSuccess(this, "Xoa thanh cong!");
            clearForm();
            loadData();
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi SQL: " + ex.getMessage());
        }
    }

    private void timKiem() {
        String kw = txtTimKiem.getText().trim();
        SwingWorker<List<KhachHang>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<KhachHang> doInBackground() throws Exception {
                if (kw.isEmpty()) return bus.findAll();
                return bus.searchByName(kw);
            }
            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (KhachHang kh : get()) {
                        model.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(),
                                kh.getSdt(), kh.getDiaChi()});
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(KhachHangPanel.this, "Loi tim kiem: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void fillFormFromRow() {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            txtMaKh.setText(String.valueOf(model.getValueAt(row, 0)));
            txtTenKh.setText(String.valueOf(model.getValueAt(row, 1)));
            txtSdt.setText(String.valueOf(model.getValueAt(row, 2)));
            txtDiaChi.setText(String.valueOf(model.getValueAt(row, 3)));
        }
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        tbl.clearSelection();
    }

    private KhachHang buildKhachHang() {
        return new KhachHang(
                0,
                txtTenKh.getText().trim(),
                txtSdt.getText().trim(),
                txtDiaChi.getText().trim()
        );
    }

    /** Chi cho phep nhap chu so, toi da 10 ky tu */
    static class PhoneDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null) return;
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = current.substring(0, offset) + string + current.substring(offset);
            if (newText.matches("\\d{0,10}")) {
                super.insertString(fb, offset, string, attr);
            }
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) return;
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = current.substring(0, offset) + text + current.substring(offset + length);
            if (newText.matches("\\d{0,10}")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
