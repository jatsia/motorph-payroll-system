package com.motorph.payroll.ui.components;

import java.awt.Component;

import javax.swing.JOptionPane;

public final class MessageDialogs {
    private MessageDialogs() {
    }

    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MotorPH Payroll", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MotorPH Payroll", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "MotorPH Payroll", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
