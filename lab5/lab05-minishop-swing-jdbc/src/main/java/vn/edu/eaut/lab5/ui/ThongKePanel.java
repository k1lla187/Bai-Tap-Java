package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ThongKePanel extends JPanel {

    private final ThongKeBUS bus = new ThongKeBUS();
    private JLabel lblDoanhThu, lblHoaDonCaoNhat, lblSanPhamBanChay;
    private JTextField txtTuNgay, txtDenNgay;
    private JButton btnDoanhThu, btnHoaDonCaoNhat, btnSanPhamBanChay, btnTop5;
    private DefaultTableModel topModel;

    public ThongKePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        // --- Ngay ---
        JPanel datePanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        datePanel.setBorder(BorderFactory.createTitledBorder("Khoang ngay"));
        datePanel.add(new JLabel("Tu ngay (yyyy-MM-dd):"));
        txtTuNgay = new JTextField(12);
        datePanel.add(txtTuNgay);
        datePanel.add(new JLabel("Den ngay (yyyy-MM-dd):"));
        txtDenNgay = new JTextField(12);
        datePanel.add(txtDenNgay);

        // --- Nut thong ke ---
        JPanel btnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        btnDoanhThu = new JButton("Tinh doanh thu");
        btnHoaDonCaoNhat = new JButton("Hoa don cao nhat");
        btnSanPhamBanChay = new JButton("San pham ban chay");
        btnTop5 = new JButton("Top 5 hoa don");
        btnPanel.add(btnDoanhThu);
        btnPanel.add(btnHoaDonCaoNhat);
        btnPanel.add(btnSanPhamBanChay);
        btnPanel.add(btnTop5);

        // --- Ket qua ---
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Ket qua thong ke"));
        resultPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 150));

        lblDoanhThu = new JLabel("Doanh thu: ---");
        lblHoaDonCaoNhat = new JLabel("Hoa don cao nhat: ---");
        lblSanPhamBanChay = new JLabel("San pham ban chay: ---");

        lblDoanhThu.setFont(lblDoanhThu.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        lblHoaDonCaoNhat.setFont(lblHoaDonCaoNhat.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        lblSanPhamBanChay.setFont(lblSanPhamBanChay.getFont().deriveFont(java.awt.Font.BOLD, 14f));

        resultPanel.add(lblDoanhThu);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(lblHoaDonCaoNhat);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(lblSanPhamBanChay);

        // --- Top 5 hoa don ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createTitledBorder("Top 5 hoa don gia tri cao nhat"));

        String[] cols = {"Ma HD", "Ngay lap", "Ma KH", "Ten KH", "Tong tien"};
        topModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblTop = new JTable(topModel);
        JScrollPane scroll = new JScrollPane(tblTop);
        scroll.setPreferredSize(new java.awt.Dimension(750, 200));
        topPanel.add(scroll);

        // --- Progress bar ---
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        add(datePanel);
        add(btnPanel);
        add(resultPanel);
        add(Box.createVerticalStrut(10));
        add(topPanel);
        add(Box.createVerticalStrut(5));
        add(progressBar);

        // --- Su kien ---
        btnDoanhThu.addActionListener(e -> tinhDoanhThu(progressBar));
        btnHoaDonCaoNhat.addActionListener(e -> timHoaDonCaoNhat());
        btnSanPhamBanChay.addActionListener(e -> timSanPhamBanChay());
        btnTop5.addActionListener(e -> timTop5HoaDon(topModel, progressBar));
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(s.trim());
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    private void tinhDoanhThu(JProgressBar pb) {
        pb.setVisible(true);
        pb.setIndeterminate(true);
        btnDoanhThu.setEnabled(false);

        LocalDate tuNgay = parseDate(txtTuNgay.getText());
        LocalDate denNgay = parseDate(txtDenNgay.getText());

        SwingWorker<BigDecimal, Void> worker = new SwingWorker<>() {
            @Override
            protected BigDecimal doInBackground() throws Exception {
                return bus.tinhDoanhThu(tuNgay, denNgay);
            }
            @Override
            protected void done() {
                pb.setIndeterminate(false);
                pb.setVisible(false);
                btnDoanhThu.setEnabled(true);
                try {
                    BigDecimal dt = get();
                    lblDoanhThu.setText("Doanh thu: " + dt + " VND");
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Loi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void timHoaDonCaoNhat() {
        btnHoaDonCaoNhat.setEnabled(false);
        SwingWorker<HoaDon, Void> worker = new SwingWorker<>() {
            @Override
            protected HoaDon doInBackground() throws Exception {
                return bus.findHoaDonCaoNhat();
            }
            @Override
            protected void done() {
                btnHoaDonCaoNhat.setEnabled(true);
                try {
                    HoaDon hd = get();
                    if (hd != null) {
                        lblHoaDonCaoNhat.setText(
                            "Hoa don cao nhat: HD#" + hd.getMaHd() + " | "
                            + hd.getNgayLap() + " | " + hd.getTenKh() + " | "
                            + hd.getTongTien() + " VND");
                    } else {
                        lblHoaDonCaoNhat.setText("Hoa don cao nhat: Khong co du lieu");
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Loi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void timSanPhamBanChay() {
        btnSanPhamBanChay.setEnabled(false);
        SwingWorker<SanPham, Void> worker = new SwingWorker<>() {
            @Override
            protected SanPham doInBackground() throws Exception {
                return bus.findSanPhamBanChayNhat();
            }
            @Override
            protected void done() {
                btnSanPhamBanChay.setEnabled(true);
                try {
                    SanPham sp = get();
                    if (sp != null) {
                        lblSanPhamBanChay.setText(
                            "San pham ban chay: " + sp.getTenSp() + " (Ma: " + sp.getMaSp() + ")");
                    } else {
                        lblSanPhamBanChay.setText("San pham ban chay: Khong co du lieu");
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Loi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void timTop5HoaDon(DefaultTableModel m, JProgressBar pb) {
        pb.setVisible(true);
        pb.setIndeterminate(true);
        btnTop5.setEnabled(false);

        SwingWorker<List<HoaDon>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<HoaDon> doInBackground() throws Exception {
                return bus.findTopHoaDon(5);
            }
            @Override
            protected void done() {
                pb.setIndeterminate(false);
                pb.setVisible(false);
                btnTop5.setEnabled(true);
                try {
                    m.setRowCount(0);
                    for (HoaDon hd : get()) {
                        m.addRow(new Object[]{
                                hd.getMaHd(), hd.getNgayLap(), hd.getMaKh(),
                                hd.getTenKh(), hd.getTongTien()
                        });
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Loi: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }
}
