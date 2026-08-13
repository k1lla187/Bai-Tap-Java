package vn.edu.eaut.lab5.util;

import javax.swing.*;
import java.awt.Component;

public class MessageUtil {

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Loi", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Xac nhan",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
