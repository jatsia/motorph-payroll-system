package com.motorph.payroll.ui;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.repository.EmploymentStatusRepository;
import com.motorph.payroll.repository.PositionRepository;
import com.motorph.payroll.ui.components.ActionToolbar;
import com.motorph.payroll.ui.components.DataTableFactory;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.components.PagePanel;
import com.motorph.payroll.ui.theme.AppTheme;
import com.motorph.payroll.util.MoneyFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class EmployeePanel extends JPanel {
    private final EmployeeRepository employeeRepository;
    private final List<Position> positions;
    private final List<EmploymentStatus> statuses;
    private final DefaultTableModel model;
    private final JTable table;
    private final PagePanel pagePanel;

    private final JTextField employeeIdField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField firstNameField = new JTextField();
    private final JTextField birthdayField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JComboBox<String> statusSelect = new JComboBox<>();
    private final JComboBox<String> positionSelect = new JComboBox<>();
    private final JTextField basicSalaryField = new JTextField();
    private final JTextField riceField = new JTextField();
    private final JTextField phoneAllowanceField = new JTextField();
    private final JTextField clothingField = new JTextField();
    private final JTextField grossField = new JTextField();
    private final JTextField hourlyField = new JTextField();

    public EmployeePanel(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.positions = loadPositions();
        this.statuses = loadStatuses();
        this.model = DataTableFactory.readOnlyModel(
                "Employee No.", "Name", "Position", "Department", "Status", "Basic Salary", "Gross Rate"
        );
        this.table = DataTableFactory.create(model, 95, 200, 180, 120, 120, 120, 120);
        this.pagePanel = buildPage();

        setLayout(new BorderLayout());
        add(pagePanel, BorderLayout.CENTER);

        for (EmploymentStatus status : statuses) {
            statusSelect.addItem(status.getName());
        }
        for (Position position : positions) {
            positionSelect.addItem(position.getTitle() + " / " + position.getDepartment().getName());
        }

        styleFields();
        resetForm();
        loadEmployees();
    }

    private PagePanel buildPage() {
        ActionToolbar toolbar = new ActionToolbar();
        toolbar.addPrimaryAction("Save", this::saveEmployee);
        toolbar.addSecondaryAction("Delete", this::deleteEmployee);
        toolbar.addSecondaryAction("New", this::resetForm);
        toolbar.addSecondaryAction("Refresh", this::loadEmployees);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);
        content.add(buildFormCard(), BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        return new PagePanel("Employees", toolbar, content);
    }

    private JPanel buildFormCard() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(AppTheme.PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JPanel form = new JPanel(new GridLayout(0, 4, 12, 8));
        form.setOpaque(false);

        addField(form, "Employee No.", employeeIdField);
        addField(form, "Last name", lastNameField);
        addField(form, "First name", firstNameField);
        addField(form, "Birthday", birthdayField);
        addField(form, "Address", addressField);
        addField(form, "Phone", phoneField);
        addField(form, "Status", statusSelect);
        addField(form, "Position", positionSelect);
        addField(form, "Basic salary", basicSalaryField);
        addField(form, "Rice subsidy", riceField);
        addField(form, "Phone allowance", phoneAllowanceField);
        addField(form, "Clothing allowance", clothingField);
        addField(form, "Gross semi-monthly rate", grossField);
        addField(form, "Hourly rate", hourlyField);

        JPanel hintRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        hintRow.setOpaque(false);
        hintRow.add(new JLabel("Select a row to edit."));

        card.add(form, BorderLayout.CENTER);
        card.add(hintRow, BorderLayout.SOUTH);
        return card;
    }

    private void addField(JPanel form, String label, JTextField field) {
        form.add(new JLabel(label));
        form.add(field);
    }

    private void addField(JPanel form, String label, JComboBox<String> field) {
        form.add(new JLabel(label));
        form.add(field);
    }

    private void styleFields() {
        List<JTextField> fields = List.of(
                employeeIdField, lastNameField, firstNameField, birthdayField, addressField,
                phoneField, basicSalaryField, riceField, phoneAllowanceField, clothingField,
                grossField, hourlyField
        );

        for (JTextField field : fields) {
            field.setPreferredSize(new Dimension(220, 34));
        }

        statusSelect.setPreferredSize(new Dimension(220, 34));
        positionSelect.setPreferredSize(new Dimension(220, 34));
    }

    private void loadEmployees() {
        model.setRowCount(0);

        for (Employee employee : employeeRepository.findAll()) {
            model.addRow(new Object[]{
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition(),
                    employee.getDepartment(),
                    employee.getStatus().getName(),
                    MoneyFormatter.format(employee.getBasicSalary()),
                    MoneyFormatter.format(employee.getGrossSemiMonthlyRate())
            });
        }

        pagePanel.setStatus("Loaded " + model.getRowCount() + " employees.");
    }

    private void saveEmployee() {
        try {
            int employeeId = Integer.parseInt(employeeIdField.getText().trim());
            EmploymentStatus status = statuses.get(statusSelect.getSelectedIndex());
            Position position = positions.get(positionSelect.getSelectedIndex());

            Employee employee = new Employee(
                    employeeId,
                    lastNameField.getText().trim(),
                    firstNameField.getText().trim(),
                    birthdayField.getText().trim(),
                    addressField.getText().trim(),
                    phoneField.getText().trim(),
                    status,
                    position,
                    null
            );

            new Compensation(
                    employee,
                    parseDouble(basicSalaryField.getText(), "basic salary"),
                    parseDouble(riceField.getText(), "rice subsidy"),
                    parseDouble(phoneAllowanceField.getText(), "phone allowance"),
                    parseDouble(clothingField.getText(), "clothing allowance"),
                    parseDouble(grossField.getText(), "gross semi-monthly rate"),
                    parseDouble(hourlyField.getText(), "hourly rate")
            );

            employeeRepository.save(employee);
            loadEmployees();
            pagePanel.setStatus("Saved employee " + employeeId + ".");
        } catch (Exception exception) {
            MessageDialogs.error(this, exception.getMessage());
        }
    }

    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageDialogs.info(this, "Select an employee first.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int employeeId = (Integer) model.getValueAt(modelRow, 0);
        employeeRepository.deleteById(employeeId);
        resetForm();
        loadEmployees();
        pagePanel.setStatus("Deleted employee " + employeeId + ".");
    }

    private void resetForm() {
        employeeIdField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        birthdayField.setText("1990-01-01");
        addressField.setText("");
        phoneField.setText("");
        basicSalaryField.setText("0");
        riceField.setText("0");
        phoneAllowanceField.setText("0");
        clothingField.setText("0");
        grossField.setText("0");
        hourlyField.setText("0");
        if (!statuses.isEmpty()) {
            statusSelect.setSelectedIndex(0);
        }
        if (!positions.isEmpty()) {
            positionSelect.setSelectedIndex(0);
        }
        pagePanel.setStatus("Ready for a new employee record.");
    }

    private List<Position> loadPositions() {
        return new ArrayList<>(new com.motorph.payroll.repository.RepositoryFactory().createPositionRepository().findAll());
    }

    private List<EmploymentStatus> loadStatuses() {
        return new ArrayList<>(new com.motorph.payroll.repository.RepositoryFactory().createEmploymentStatusRepository().findAll());
    }

    private double parseDouble(String value, String label) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(label + " must be a number.");
        }
    }
}
