package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai03PhuongTrinhBacNhat extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JLabel lblResult = new JLabel("Nghiem: ");

    public Bai03PhuongTrinhBacNhat() {
        setTitle("Bai 3 - Giai phuong trinh ax + b = 0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.add(new JLabel("He so a:"));
        inputPanel.add(txtA);
        inputPanel.add(new JLabel("He so b:"));
        inputPanel.add(txtB);

        JButton btnSolve = new JButton("Giai phuong trinh");
        btnSolve.addActionListener(e -> giaiPhuongTrinh());

        add(inputPanel, BorderLayout.CENTER);
        add(btnSolve, BorderLayout.SOUTH);
        add(lblResult, BorderLayout.NORTH);

        setSize(420, 190);
        setLocationRelativeTo(null);
    }

    private void giaiPhuongTrinh() {
        try {
            double a = Double.parseDouble(txtA.getText().trim());
            double b = Double.parseDouble(txtB.getText().trim());
            final double EPS = 1e-9;

            if (Math.abs(a) < EPS && Math.abs(b) < EPS) {
                lblResult.setText("Nghiem: phuong trinh co vo so nghiem");
            } else if (Math.abs(a) < EPS) {
                lblResult.setText("Nghiem: phuong trinh vo nghiem");
            } else {
                double x = -b / a;
                lblResult.setText(String.format("Nghiem: x = %.4f", x));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap a, b la so hop le!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai03PhuongTrinhBacNhat().setVisible(true));
    }
}
