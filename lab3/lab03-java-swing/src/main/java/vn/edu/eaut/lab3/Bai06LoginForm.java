package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {
    private final JTextField txtUsername = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);
    private final JComboBox<String> cboRole = new JComboBox<>(new String[]{"Admin", "User"});
    private final JCheckBox chkShowPassword = new JCheckBox("Hien thi mat khau");

    public Bai06LoginForm() {
        setTitle("Bai 6 - Form dang nhap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel lblTitle = new JLabel("DANG NHAP HE THONG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Tai khoan:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Mat khau:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Vai tro:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cboRole, gbc);

        // Show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 3;
        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('*');
            }
        });
        formPanel.add(chkShowPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnLogin = new JButton("Dang nhap");
        JButton btnReset = new JButton("Lam moi");
        btnPanel.add(btnLogin);
        btnPanel.add(btnReset);
        add(btnPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> dangNhap());
        btnReset.addActionListener(e -> lamMoi());

        setSize(400, 280);
        setLocationRelativeTo(null);
    }

    private void dangNhap() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String selectedRole = (String) cboRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiem tra tai khoan
        boolean valid = false;
        if (selectedRole.equals("Admin") && username.equals("admin") && password.equals("123456")) {
            valid = true;
        } else if (selectedRole.equals("User") && username.equals("user") && password.equals("123456")) {
            valid = true;
        }

        if (valid) {
            JOptionPane.showMessageDialog(this, "Xin chao " + username + "!\nDang nhap thanh cong voi vai tro " + selectedRole + ".", "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Dang nhap that bai!\nVui long kiem tra lai thong tin.", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lamMoi() {
        txtUsername.setText("");
        txtPassword.setText("");
        cboRole.setSelectedIndex(0);
        chkShowPassword.setSelected(false);
        txtPassword.setEchoChar('*');
        txtUsername.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}
