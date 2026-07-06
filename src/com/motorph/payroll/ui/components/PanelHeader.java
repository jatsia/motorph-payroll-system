package com.motorph.payroll.ui.components;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

public class PanelHeader extends JPanel {
    public PanelHeader(String title, Component actions) {
        super(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(16f));
        add(label, BorderLayout.WEST);

        if (actions != null) {
            add(actions, BorderLayout.EAST);
        }
    }
}

