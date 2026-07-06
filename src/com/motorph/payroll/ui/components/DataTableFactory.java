package com.motorph.payroll.ui.components;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public final class DataTableFactory {
    private DataTableFactory() {
    }

    public static JTable create(DefaultTableModel model, int... widths) {
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        TableColumnModel columnModel = table.getColumnModel();
        for (int index = 0; index < widths.length && index < columnModel.getColumnCount(); index++) {
            columnModel.getColumn(index).setPreferredWidth(widths[index]);
        }

        return table;
    }

    public static DefaultTableModel readOnlyModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}

