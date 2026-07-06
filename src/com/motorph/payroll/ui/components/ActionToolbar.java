package com.motorph.payroll.ui.components;

import com.motorph.payroll.ui.theme.AppTheme;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class ActionToolbar extends JPanel {
    public ActionToolbar() {
        super(new FlowLayout(FlowLayout.LEFT, 8, 0));
        setOpaque(false);
    }

    public JButton addPrimaryAction(String label, Runnable action) {
        JButton button = new JButton(label);
        AppTheme.stylePrimaryButton(button);
        button.addActionListener(event -> action.run());
        add(button);
        return button;
    }

    public JButton addSecondaryAction(String label, Runnable action) {
        JButton button = new JButton(label);
        AppTheme.styleSecondaryButton(button);
        button.addActionListener(event -> action.run());
        add(button);
        return button;
    }
}

