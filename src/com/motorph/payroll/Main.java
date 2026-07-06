package com.motorph.payroll;

import com.motorph.payroll.database.DataSeeder;
import com.motorph.payroll.ui.LoginFrame;
import com.motorph.payroll.ui.theme.AppTheme;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppTheme.apply();
            try {
                new DataSeeder().seed();
                new LoginFrame().setVisible(true);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "MySQL database is not available.", "Startup Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
