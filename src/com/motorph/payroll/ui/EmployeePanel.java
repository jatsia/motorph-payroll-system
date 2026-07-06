package com.motorph.payroll.ui;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.ui.components.ActionToolbar;
import com.motorph.payroll.ui.components.DataTableFactory;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.components.PagePanel;
import com.motorph.payroll.util.MoneyFormatter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class EmployeePanel extends JPanel {
    private final EmployeeRepository employeeRepository;
    private final DefaultTableModel model;
    private final JTable table;

    public EmployeePanel(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.model = DataTableFactory.readOnlyModel(
                "Employee No.", "Name", "Position", "Department", "Basic Salary", "Semi-Monthly Rate"
        );
        this.table = DataTableFactory.create(model, 100, 220, 240, 140, 140, 170);

        setLayout(new BorderLayout());

        ActionToolbar toolbar = new ActionToolbar();
        toolbar.addSecondaryAction("Refresh", this::loadEmployees);
        toolbar.addPrimaryAction("View Details", this::showSelectedEmployee);

        add(new PagePanel("Employees", toolbar, new JScrollPane(table)), BorderLayout.CENTER);
        loadEmployees();
    }

    private void loadEmployees() {
        model.setRowCount(0);

        for (Employee employee : employeeRepository.findAll()) {
            model.addRow(new Object[]{
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition(),
                    employee.getDepartment(),
                    MoneyFormatter.format(employee.getBasicSalary()),
                    MoneyFormatter.format(employee.getGrossSemiMonthlyRate())
            });
        }
    }

    private void showSelectedEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageDialogs.info(this, "Select an employee first.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int employeeId = (Integer) model.getValueAt(modelRow, 0);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found."));

        MessageDialogs.info(this, """
                Employee No.: %d
                Name: %s
                Position: %s
                Department: %s
                Basic Salary: %s
                Semi-Monthly Rate: %s
                """.formatted(
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getDepartment(),
                MoneyFormatter.format(employee.getBasicSalary()),
                MoneyFormatter.format(employee.getGrossSemiMonthlyRate())
        ));
    }
}
