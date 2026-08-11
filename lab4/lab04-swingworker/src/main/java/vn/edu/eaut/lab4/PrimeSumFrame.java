package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class PrimeSumFrame extends JFrame {
    private JTextField txtN;
    private JButton btnCalculate;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public PrimeSumFrame() {
        setTitle("Bai 3 - Tong cac so nguyen to");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtN = new JTextField(15);
        btnCalculate = new JButton("Tinh");
        lblResult = new JLabel("Ket qua: ");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(txtN);
        panel.add(btnCalculate);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);
        btnCalculate.addActionListener(e -> calculatePrimeSum());
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private void calculatePrimeSum() {
        int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n <= 2) {
                JOptionPane.showMessageDialog(this, "N phai lon hon 2");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le");
            return;
        }

        btnCalculate.setEnabled(false);
        txtN.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Dang tinh...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                long sum = 0;
                for (int i = 2; i < n; i++) {
                    if (isPrime(i)) sum += i;
                    int progress = (int) ((i * 100.0) / n);
                    setProgress(progress);
                }
                return sum;
            }

            @Override
            protected void done() {
                try {
                    long result = get();
                    lblResult.setText("Tong cac so nguyen to nho hon " + n + " = " + result);
                } catch (Exception ex) {
                    lblResult.setText("Co loi khi tinh toan");
                }
                btnCalculate.setEnabled(true);
                txtN.setEnabled(true);
                progressBar.setValue(100);
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
        SwingUtilities.invokeLater(() -> new PrimeSumFrame().setVisible(true));
    }
}
