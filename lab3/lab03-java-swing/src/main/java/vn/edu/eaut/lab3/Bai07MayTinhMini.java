package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai07MayTinhMini extends JFrame implements ActionListener {
    private final JTextField txtSo1 = new JTextField(15);
    private final JTextField txtSo2 = new JTextField(15);
    private final JTextField txtResult = new JTextField(15);
    private final JTextArea txtHistory = new JTextArea(6, 30);
    private char lastOperator = '+';

    public Bai07MayTinhMini() {
        setTitle("Bai 7 - May tinh mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("So thu nhat:"));
        inputPanel.add(txtSo1);
        inputPanel.add(new JLabel("So thu hai:"));
        inputPanel.add(txtSo2);
        inputPanel.add(new JLabel("Ket qua:"));
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(240, 240, 240));
        inputPanel.add(txtResult);

        add(inputPanel, BorderLayout.NORTH);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnCong = new JButton("+");
        JButton btnTru = new JButton("-");
        JButton btnNhan = new JButton("*");
        JButton btnChia = new JButton("/");
        JButton btnClear = new JButton("Clear");

        btnCong.setPreferredSize(new Dimension(50, 35));
        btnTru.setPreferredSize(new Dimension(50, 35));
        btnNhan.setPreferredSize(new Dimension(50, 35));
        btnChia.setPreferredSize(new Dimension(50, 35));
        btnClear.setPreferredSize(new Dimension(80, 35));

        btnPanel.add(btnCong);
        btnPanel.add(btnTru);
        btnPanel.add(btnNhan);
        btnPanel.add(btnChia);
        btnPanel.add(btnClear);

        add(btnPanel, BorderLayout.CENTER);

        // History panel
        JLabel lblHistory = new JLabel("Lich su phep tinh:");
        txtHistory.setEditable(false);
        txtHistory.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.add(lblHistory, BorderLayout.NORTH);
        historyPanel.add(new JScrollPane(txtHistory), BorderLayout.CENTER);

        add(historyPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnCong.addActionListener(this);
        btnTru.addActionListener(this);
        btnNhan.addActionListener(this);
        btnChia.addActionListener(this);
        btnClear.addActionListener(e -> lamMoi());

        setSize(350, 350);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "+":
                lastOperator = '+';
                break;
            case "-":
                lastOperator = '-';
                break;
            case "*":
                lastOperator = '*';
                break;
            case "/":
                lastOperator = '/';
                break;
        }
        tinhToan(lastOperator);
    }

    private void tinhToan(char operator) {
        try {
            double a = Double.parseDouble(txtSo1.getText().trim());
            double b = Double.parseDouble(txtSo2.getText().trim());
            double result = 0;
            String expression = "";

            switch (operator) {
                case '+':
                    result = a + b;
                    expression = String.format("%.2f + %.2f = %.2f", a, b, result);
                    break;
                case '-':
                    result = a - b;
                    expression = String.format("%.2f - %.2f = %.2f", a, b, result);
                    break;
                case '*':
                    result = a * b;
                    expression = String.format("%.2f * %.2f = %.2f", a, b, result);
                    break;
                case '/':
                    if (Math.abs(b) < 1e-9) {
                        JOptionPane.showMessageDialog(this, "Khong the chia cho 0!", "Loi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    result = a / b;
                    expression = String.format("%.2f / %.2f = %.2f", a, b, result);
                    break;
            }

            txtResult.setText(String.valueOf(result));
            txtHistory.append(expression + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so hop le!", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoi() {
        txtSo1.setText("");
        txtSo2.setText("");
        txtResult.setText("");
        txtSo1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}
