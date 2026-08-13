package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {

    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private DefaultComboBoxModel<KhachHang> khModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<SanPham> spModel = new DefaultComboBoxModel<>();
    private DefaultTableModel cartModel;
    private List<ChiTietHoaDon> cart = new ArrayList<>();

    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong, txtTongTien;
    private JTable tblChiTiet, tblHoaDon;
    private JButton btnThemVaoGio, btnXoaKhoiGio, btnLuuHoaDon, btnLamMoi;

    public HoaDonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    private void initComponents() {
        // === TOP: Lap hoa don ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createTitledBorder("Lap hoa don"));
        topPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 280));

        JPanel row1 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        row1.add(new JLabel("Khach hang:"));
        cboKhachHang = new JComboBox<>(khModel);
        cboKhachHang.setPreferredSize(new java.awt.Dimension(300, 25));
        row1.add(cboKhachHang);
        row1.add(new JLabel("San pham:"));
        cboSanPham = new JComboBox<>(spModel);
        cboSanPham.setPreferredSize(new java.awt.Dimension(300, 25));
        row1.add(cboSanPham);

        JPanel row2 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        row2.add(new JLabel("So luong:"));
        txtSoLuong = new JTextField("1", 8);
        row2.add(txtSoLuong);
        btnThemVaoGio = new JButton("Them vao gio");
        btnXoaKhoiGio = new JButton("Xoa khoi gio");
        row2.add(btnThemVaoGio);
        row2.add(btnXoaKhoiGio);

        // Bang chi tiet hoa don tam (gio hang)
        String[] cartCols = {"Ma SP", "Ten san pham", "So luong", "Don gia", "Thanh tien"};
        cartModel = new DefaultTableModel(cartCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblChiTiet = new JTable(cartModel);
        JScrollPane cartScroll = new JScrollPane(tblChiTiet);
        cartScroll.setPreferredSize(new java.awt.Dimension(750, 120));

        JPanel row3 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
        row3.add(new JLabel("Tong tien:"));
        txtTongTien = new JTextField("0", 15);
        txtTongTien.setEditable(false);
        txtTongTien.setFont(txtTongTien.getFont().deriveFont(java.awt.Font.BOLD));
        row3.add(txtTongTien);
        btnLuuHoaDon = new JButton("Luu hoa don");
        btnLamMoi = new JButton("Lam moi");
        row3.add(btnLuuHoaDon);
        row3.add(btnLamMoi);

        topPanel.add(row1);
        topPanel.add(row2);
        topPanel.add(cartScroll);
        topPanel.add(row3);

        // === BOTTOM: Danh sach hoa don ===
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Danh sach hoa don"));
        bottomPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 280));

        String[] hdCols = {"Ma HD", "Ngay lap", "Ma KH", "Ten KH", "Tong tien"};
        DefaultTableModel hdModel = new DefaultTableModel(hdCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblHoaDon = new JTable(hdModel);
        JScrollPane hdScroll = new JScrollPane(tblHoaDon);

        bottomPanel.add(hdScroll);

        add(topPanel);
        add(bottomPanel);

        // --- Su kien ---
        btnThemVaoGio.addActionListener(e -> themVaoGio());
        btnXoaKhoiGio.addActionListener(e -> xoaKhoiGio());
        btnLuuHoaDon.addActionListener(e -> luuHoaDon(hdModel));
        btnLamMoi.addActionListener(e -> { cart.clear(); updateCartTable(); updateHoaDonTable(hdModel); loadCombobox(); });

        // Load data
        loadCombobox();
        updateHoaDonTable(hdModel);

        // Store model reference for refresh
        this.hdModelRef = hdModel;
    }

    private DefaultTableModel hdModelRef;

    private void loadCombobox() {
        try {
            List<KhachHang> dsKh = khachHangBUS.findAll();
            khModel.removeAllElements();
            for (KhachHang kh : dsKh) khModel.addElement(kh);

            List<SanPham> dsSp = sanPhamBUS.findAll();
            spModel.removeAllElements();
            for (SanPham sp : dsSp) spModel.addElement(sp);
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi load combobox: " + ex.getMessage());
        }
    }

    private void themVaoGio() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) { MessageUtil.showError(this, "Chon san pham!"); return; }

        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        } catch (NumberFormatException e) {
            MessageUtil.showError(this, "So luong khong hop le!"); return;
        }

        if (soLuong <= 0) { MessageUtil.showError(this, "So luong phai lon hon 0!"); return; }
        if (soLuong > sp.getSoLuong()) {
            MessageUtil.showError(this, "So luong vuot qua ton kho (" + sp.getSoLuong() + ")!"); return;
        }

        // Kiem tra da co trong gio chua
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getMaSp() == sp.getMaSp()) {
                int newQty = cart.get(i).getSoLuong() + soLuong;
                if (newQty > sp.getSoLuong()) {
                    MessageUtil.showError(this, "Tong so luong vuot qua ton kho!"); return;
                }
                ChiTietHoaDon ct = cart.get(i);
                ct.setSoLuong(newQty);
                cart.set(i, ct);
                updateCartTable();
                return;
            }
        }

        ChiTietHoaDon ct = new ChiTietHoaDon(sp.getMaSp(), sp.getTenSp(), soLuong, sp.getDonGia());
        cart.add(ct);
        updateCartTable();
    }

    private void xoaKhoiGio() {
        int row = tblChiTiet.getSelectedRow();
        if (row < 0) { MessageUtil.showError(this, "Chon dong can xoa!"); return; }
        cart.remove(row);
        updateCartTable();
    }

    private void updateCartTable() {
        cartModel.setRowCount(0);
        BigDecimal tong = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : cart) {
            cartModel.addRow(new Object[]{
                    ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(),
                    ct.getDonGia(), ct.getThanhTien()
            });
            if (ct.getThanhTien() != null) {
                tong = tong.add(ct.getThanhTien());
            }
        }
        txtTongTien.setText(tong.toString());
    }

    private void updateHoaDonTable(DefaultTableModel m) {
        SwingWorker<List<HoaDon>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<HoaDon> doInBackground() throws Exception {
                return hoaDonBUS.findAll();
            }
            @Override
            protected void done() {
                try {
                    m.setRowCount(0);
                    for (HoaDon hd : get()) {
                        m.addRow(new Object[]{
                                hd.getMaHd(), hd.getNgayLap(), hd.getMaKh(),
                                hd.getTenKh(), hd.getTongTien()
                        });
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(HoaDonPanel.this, "Loi load hoa don: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void luuHoaDon(DefaultTableModel hdModel) {
        if (cart.isEmpty()) { MessageUtil.showError(this, "Gio hang trong!"); return; }
        KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
        if (kh == null) { MessageUtil.showError(this, "Chon khach hang!"); return; }

        btnLuuHoaDon.setEnabled(false);
        SwingWorker<Integer, Void> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return hoaDonBUS.saveHoaDon(kh.getMaKh(), cart);
            }
            @Override
            protected void done() {
                btnLuuHoaDon.setEnabled(true);
                try {
                    int maHd = get();
                    MessageUtil.showSuccess(HoaDonPanel.this, "Luu hoa don thanh cong! Ma HD: " + maHd);
                    cart.clear();
                    updateCartTable();
                    updateHoaDonTable(hdModel);
                } catch (Exception ex) {
                    MessageUtil.showError(HoaDonPanel.this, "Loi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }
}
