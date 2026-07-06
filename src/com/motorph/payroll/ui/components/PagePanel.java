package com.motorph.payroll.ui.components;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

public class PagePanel extends JPanel {
    private final JLabel statusLabel = new JLabel(" ");

    public PagePanel(String title, Component toolbar, Component content) {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        add(new PanelHeader(title, toolbar), BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void setStatus(String message) {
        statusLabel.setText(message == null || message.isBlank() ? " " : message);
    }
}
