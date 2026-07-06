package com.motorph.payroll.ui.components;

import com.motorph.payroll.util.MoneyFormatter;

import javax.swing.table.DefaultTableCellRenderer;

public class MoneyTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    protected void setValue(Object value) {
        if (value instanceof Number number) {
            setText(MoneyFormatter.format(number.doubleValue()));
            return;
        }

        super.setValue(value);
    }
}

