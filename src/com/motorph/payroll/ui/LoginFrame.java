package com.motorph.payroll.ui;

import com.motorph.payroll.service.AuthService;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.theme.AppTheme;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {
    private final AuthService authService = new AuthService();
    private final JTextField usernameField = new JTextField("admin", 18);
    private final JPasswordField passwordField = new JPasswordField("admin123", 18);

    public LoginFrame() {
        setTitle("MotorPH Payroll Login");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("MotorPH Payroll System");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(24, 28, 12, 28));
        titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(8, 28, 24, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username"), gbc);

        gbc.gridy++;
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Password"), gbc);

        gbc.gridy++;
        formPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setText("Submit");
        AppTheme.stylePrimaryButton(loginButton);
        loginButton.addActionListener(event -> login());
        getRootPane().setDefaultButton(loginButton);

        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (authService.login(username, password)) {
                new DashboardFrame().setVisible(true);
                dispose();
                return;
            }
        } catch (Exception exception) {
            MessageDialogs.error(this, "Database is not available.");
            return;
        }

        MessageDialogs.error(this, "Invalid username or password.");
    }
}
