package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductLoaderFrame extends JFrame {
    private JButton btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;
    private JProgressBar progressBar;

    public ProductLoaderFrame() {
        setTitle("Bai 9 - Tai danh sach san pham");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLoad = new JButton("Tai san pham");
        lblStatus = new JLabel("Trang thai: San sang");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(btnLoad);
        topPanel.add(lblStatus);
        topPanel.add(progressBar);

        String[] columns = {"Ma SP", "Ten SP", "Don gia"};
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

        btnLoad.addActionListener(e -> loadProducts());
    }

    private List<String[]> getSampleProducts() {
        List<String[]> products = new ArrayList<>();
        products.add(new String[]{"SP01", "Ban phim", "250000"});
        products.add(new String[]{"SP02", "Chuot", "150000"});
        products.add(new String[]{"SP03", "Man hinh", "2500000"});
        products.add(new String[]{"SP04", "Tai nghe", "350000"});
        products.add(new String[]{"SP05", "Webcam", "450000"});
        products.add(new String[]{"SP06", "Loa", "800000"});
        products.add(new String[]{"SP07", "USB", "120000"});
        products.add(new String[]{"SP08", "Adapter", "200000"});
        products.add(new String[]{"SP09", "Router", "650000"});
        products.add(new String[]{"SP10", "Switch", "900000"});
        return products;
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        progressBar.setValue(0);
        tableModel.setRowCount(0);
        lblStatus.setText("Dang tai san pham...");

        SwingWorker<List<String[]>, Integer> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> products = getSampleProducts();
                List<String[]> results = new ArrayList<>();

                for (int i = 0; i < products.size(); i++) {
                    Thread.sleep(500);
                    results.add(products.get(i));
                    int progress = (int) (((i + 1) * 100.0) / products.size());
                    setProgress(progress);
                }
                return results;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                progressBar.setValue(value);
                lblStatus.setText("Dang tai... " + value + "%");
            }

            @Override
            protected void done() {
                try {
                    List<String[]> products = get();
                    for (String[] p : products) {
                        tableModel.addRow(p);
                    }
                    lblStatus.setText("Da tai " + products.size() + " san pham");
                } catch (Exception ex) {
                    lblStatus.setText("Loi khi tai du lieu");
                }
                btnLoad.setEnabled(true);
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
        SwingUtilities.invokeLater(() -> new ProductLoaderFrame().setVisible(true));
    }
}
