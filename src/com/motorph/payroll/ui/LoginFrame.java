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
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {
    private final AuthService authService = new AuthService();
    private final JTextField usernameField = new JTextField("admin", 18);
    private final JPasswordField passwordField = new JPasswordField("admin123", 18);

    public LoginFrame() {
        setTitle("MotorPH Payroll Login");
        setSize(500, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel titleLabel = new JLabel("MotorPH Payroll System");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(26, 28, 10, 28));
        titleLabel.setFont(titleLabel.getFont().deriveFont(22f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 24, 24, 24),
                BorderFactory.createLineBorder(new Color(229, 231, 235))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username"), gbc);

        gbc.gridy++;
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Password"), gbc);

        gbc.gridy++;
        formPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Sign in");
        AppTheme.stylePrimaryButton(loginButton);
        loginButton.addActionListener(event -> login());
        getRootPane().setDefaultButton(loginButton);

        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username == null || username.isBlank() || password.isBlank()) {
            MessageDialogs.error(this, "Enter username and password.");
            return;
        }

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
