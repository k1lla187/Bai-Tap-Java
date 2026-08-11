package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CancelTaskFrame extends JFrame {
    private JButton btnStart;
    private JButton btnCancel;
    private JProgressBar progressBar;
    private JLabel lblStatus;
    private SwingWorker<Void, Integer> currentWorker;

    public CancelTaskFrame() {
        setTitle("Bai 6 - Huy tac vu");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnStart = new JButton("Bat dau");
        btnCancel = new JButton("Huy");
        btnCancel.setEnabled(false);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        lblStatus = new JLabel("Trang thai: San sang");

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnStart);
        btnPanel.add(btnCancel);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(lblStatus);
        panel.add(progressBar);
        panel.add(btnPanel);
        panel.add(new JLabel("Mo phong tai du lieu 10 giay, co the huy"));

        add(panel);

        btnStart.addActionListener(e -> startTask());
        btnCancel.addActionListener(e -> cancelTask());
    }

    private void startTask() {
        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Dang xu ly...");

        currentWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 10) {
                    if (isCancelled()) {
                        return null;
                    }
                    publish(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                progressBar.setValue(value);
                lblStatus.setText("Dang xu ly... " + value + "%");
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    lblStatus.setText("Da huy tac vu!");
                } else {
                    progressBar.setValue(100);
                    lblStatus.setText("Hoan thanh!");
                    JOptionPane.showMessageDialog(CancelTaskFrame.this, "Hoan thanh!");
                }
                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        };

        currentWorker.execute();
    }

    private void cancelTask() {
        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
            progressBar.setValue(0);
            lblStatus.setText("Da huy tac vu!");
            btnStart.setEnabled(true);
            btnCancel.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelTaskFrame().setVisible(true));
    }
}
