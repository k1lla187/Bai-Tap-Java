package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FibonacciFrame extends JFrame {
    private JTextField txtN;
    private JButton btnFind;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public FibonacciFrame() {
        setTitle("Bai 4 - Tim so Fibonacci");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtN = new JTextField(15);
        btnFind = new JButton("Tim");
        lblResult = new JLabel("Ket qua: ");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(txtN);
        panel.add(btnFind);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);
        btnFind.addActionListener(e -> findFibonacci());
    }

    private BigInteger fibonacci(int n, Map<Integer, BigInteger> memo) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        BigInteger value = fibonacci(n - 1, memo).add(fibonacci(n - 2, memo));
        memo.put(n, value);
        return value;
    }

    private void findFibonacci() {
        int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n < 0) {
                JOptionPane.showMessageDialog(this, "N phai >= 0");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le");
            return;
        }

        btnFind.setEnabled(false);
        txtN.setEnabled(false);
        progressBar.setIndeterminate(true);
        progressBar.setString("Dang tinh...");
        lblResult.setText("Dang tinh Fibonacci...");

        SwingWorker<BigInteger, Void> worker = new SwingWorker<>() {
            @Override
            protected BigInteger doInBackground() {
                Map<Integer, BigInteger> memo = new HashMap<>();
                return fibonacci(n, memo);
            }

            @Override
            protected void done() {
                try {
                    BigInteger result = get();
                    lblResult.setText("Fibonacci(" + n + ") = " + result);
                } catch (Exception ex) {
                    lblResult.setText("Co loi khi tinh Fibonacci");
                }
                progressBar.setIndeterminate(false);
                progressBar.setValue(100);
                progressBar.setString("Hoan tat");
                btnFind.setEnabled(true);
                txtN.setEnabled(true);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FibonacciFrame().setVisible(true));
    }
}
