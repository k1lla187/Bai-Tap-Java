package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CountdownFrame extends JFrame {
    private JTextField txtSeconds;
    private JButton btnStart;
    private JLabel lblTime;

    public CountdownFrame() {
        setTitle("Bai 1 - Dong ho dem nguoc");
        setSize(400, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtSeconds = new JTextField(10);
        btnStart = new JButton("Bat dau");
        lblTime = new JLabel("Thoi gian con lai: ");
        lblTime.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(txtSeconds);
        panel.add(btnStart);
        panel.add(lblTime);

        add(panel);
        btnStart.addActionListener(e -> startCountdown());
    }

    private void startCountdown() {
        int seconds;
        try {
            seconds = Integer.parseInt(txtSeconds.getText().trim());
            if (seconds <= 0) {
                JOptionPane.showMessageDialog(this, "So giay phai lon hon 0");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le");
            return;
        }

        btnStart.setEnabled(false);
        txtSeconds.setEnabled(false);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = seconds; i >= 0; i--) {
                    publish(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                lblTime.setText("Thoi gian con lai: " + value + " giay");
            }

            @Override
            protected void done() {
                btnStart.setEnabled(true);
                txtSeconds.setEnabled(true);
                JOptionPane.showMessageDialog(CountdownFrame.this, "Hoan thanh!");
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CountdownFrame().setVisible(true));
    }
}
