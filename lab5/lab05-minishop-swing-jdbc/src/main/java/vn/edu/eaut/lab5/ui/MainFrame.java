package vn.edu.eaut.lab5.ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("MiniShop - Quan ly ban hang");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("San pham", new SanPhamPanel());
        tabs.addTab("Khach hang", new KhachHangPanel());
        tabs.addTab("Hoa don", new HoaDonPanel());
        tabs.addTab("Thong ke", new ThongKePanel());

        add(tabs);
    }
}
