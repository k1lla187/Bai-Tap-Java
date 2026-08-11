package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener {
    private JButton[] buttons;

    public MenuFrame() {
        setTitle("Lab 4 - SwingWorker Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1, 10, 10));

        String[] names = {
            "Bai 1: Dem nguoc",
            "Bai 2: Tai du lieu",
            "Bai 3: Tong so nguyen to",
            "Bai 4: Fibonacci",
            "Bai 5: Dem dong file",
            "Bai 6: Huy tac vu",
            "Bai 7: Tim kiem tu khoa",
            "Bai 8: Doc CSV diem SV",
            "Bai 9: Tai san pham",
            "Bai 10: Quan ly san pham CSV"
        };

        buttons = new JButton[names.length];
        for (int i = 0; i < names.length; i++) {
            buttons[i] = new JButton(names[i]);
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setSize(350, 450);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String cmd = source.getActionCommand();

        JFrame frame = null;
        switch (cmd) {
            case "Bai 1: Dem nguoc":
                frame = new CountdownFrame();
                break;
            case "Bai 2: Tai du lieu":
                frame = new ProgressDemoFrame();
                break;
            case "Bai 3: Tong so nguyen to":
                frame = new PrimeSumFrame();
                break;
            case "Bai 4: Fibonacci":
                frame = new FibonacciFrame();
                break;
            case "Bai 5: Dem dong file":
                frame = new FileLineCounterFrame();
                break;
            case "Bai 6: Huy tac vu":
                frame = new CancelTaskFrame();
                break;
            case "Bai 7: Tim kiem tu khoa":
                frame = new FileSearchFrame();
                break;
            case "Bai 8: Doc CSV diem SV":
                frame = new CSVReaderFrame();
                break;
            case "Bai 9: Tai san pham":
                frame = new ProductLoaderFrame();
                break;
            case "Bai 10: Quan ly san pham CSV":
                frame = new ProductManagerFrame();
                break;
        }

        if (frame != null) {
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFrame().setVisible(true));
    }
}
