package com.motorph.payroll.ui.theme;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public final class AppTheme {
    public static final Color BACKGROUND = new Color(245, 247, 250);
    public static final Color PANEL = Color.WHITE;
    public static final Color TEXT = new Color(31, 41, 55);
    public static final Color ACCENT = new Color(37, 99, 235);

    private AppTheme() {
    }

    public static void apply() {
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("Table.rowHeight", 28);
        UIManager.put("Panel.background", BACKGROUND);
    }

    public static void stylePrimaryButton(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setBackground(PANEL);
        button.setForeground(TEXT);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219)),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
