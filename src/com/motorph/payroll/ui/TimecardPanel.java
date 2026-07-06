package com.motorph.payroll.ui;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.repository.AttendanceRepository;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.service.AttendanceService;
import com.motorph.payroll.service.PdfExportService;
import com.motorph.payroll.service.report.PdfReportWriter;
import com.motorph.payroll.ui.components.DataTableFactory;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.theme.AppTheme;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TimecardPanel extends JPanel {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceService attendanceService;
    private final List<Employee> employees;
    private final JComboBox<String> employeeSelect;
    private final DefaultTableModel model;
    private final JTable table;
    private final JLabel statusLabel = new JLabel(" ");
    private final PdfReportWriter pdfReportWriter = new PdfReportWriter();
    private final PdfExportService pdfExportService = new PdfExportService();
    private final JTextField workDateField = new JTextField();
    private final JTextField dayField = new JTextField();
    private final JTextField timeInField = new JTextField();
    private final JTextField breakOutField = new JTextField();
    private final JTextField breakInField = new JTextField();
    private final JTextField timeOutField = new JTextField();
    private final JTextField hoursField = new JTextField();
    private final JTextField overtimeField = new JTextField();
    private final JTextField remarksField = new JTextField();

    public TimecardPanel(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceService = new AttendanceService(attendanceRepository);
        this.employees = new ArrayList<>(employeeRepository.findAll());
        this.employeeSelect = new JComboBox<>();
        this.model = DataTableFactory.readOnlyModel(
                "Date", "Day", "Time In", "Break Out", "Break In", "Time Out", "Hours", "OT", "Remarks"
        );
        this.table = DataTableFactory.create(model, 110, 60, 90, 100, 90, 90, 70, 60, 260);

        setLayout(new BorderLayout());

        for (Employee employee : employees) {
            employeeSelect.addItem(employee.getEmployeeId() + " - " + employee.getFullName());
        }
        employeeSelect.setPreferredSize(new Dimension(320, 36));
        selectEmployeeWithRecords();
        employeeSelect.addActionListener(event -> {
            clearForm();
            loadTimecard();
        });

        add(buildPage(), BorderLayout.CENTER);
        clearForm();
        loadTimecard();
    }

    private JPanel buildPage() {
        JPanel page = new JPanel(new BorderLayout(0, 14));
        page.setBackground(AppTheme.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        page.add(buildHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);
        content.add(buildFormCard(), BorderLayout.NORTH);
        content.add(buildTableCard(), BorderLayout.CENTER);
        page.add(content, BorderLayout.CENTER);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 0));
        page.add(statusLabel, BorderLayout.SOUTH);

        return page;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 10));
        header.setOpaque(false);

        JLabel title = new JLabel("Employee Timecard");
        title.setFont(title.getFont().deriveFont(18f));
        header.add(title, BorderLayout.NORTH);

        JPanel filterRow = new JPanel(new BorderLayout(12, 0));
        filterRow.setBackground(AppTheme.PANEL);
        filterRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel employeeGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        employeeGroup.setOpaque(false);
        employeeGroup.add(new JLabel("Employee"));
        employeeGroup.add(employeeSelect);
        employeeGroup.add(primaryButton("Load", this::loadTimecard));
        filterRow.add(employeeGroup, BorderLayout.WEST);

        JPanel reportGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        reportGroup.setOpaque(false);
        reportGroup.add(secondaryButton("Refresh", this::loadTimecard));
        reportGroup.add(secondaryButton("Export PDF", this::exportPdf));
        filterRow.add(reportGroup, BorderLayout.EAST);

        header.add(filterRow, BorderLayout.CENTER);
        return header;
    }

    private JPanel buildFormCard() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(AppTheme.PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(229, 231, 235)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        addField(form, 0, 0, "Date", workDateField);
        addField(form, 2, 0, "Day", dayField);
        addField(form, 4, 0, "Time In", timeInField);
        addField(form, 0, 1, "Break Out", breakOutField);
        addField(form, 2, 1, "Break In", breakInField);
        addField(form, 4, 1, "Time Out", timeOutField);
        addField(form, 0, 2, "Hours", hoursField);
        addField(form, 2, 2, "Overtime", overtimeField);
        addField(form, 4, 2, "Remarks", remarksField);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        actions.add(secondaryButton("Add Record", this::clearForm));
        actions.add(secondaryButton("Edit Selected", this::fillFormFromSelection));
        actions.add(primaryButton("Save Record", this::saveRecord));

        card.add(form, BorderLayout.CENTER);
        card.add(actions, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.PANEL);
        card.setBorder(BorderFactory.createLineBorder(new java.awt.Color(209, 213, 219)));
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        return card;
    }

    private void addField(JPanel form, int gridX, int gridY, String label, JTextField field) {
        field.setPreferredSize(new Dimension(180, 34));

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = gridX;
        labelConstraints.gridy = gridY;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, 0, 10, 8);
        form.add(new JLabel(label), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = gridX + 1;
        fieldConstraints.gridy = gridY;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(0, 0, 10, 18);
        form.add(field, fieldConstraints);
    }

    private JButton primaryButton(String label, Runnable action) {
        JButton button = new JButton(label);
        AppTheme.stylePrimaryButton(button);
        button.addActionListener(event -> action.run());
        return button;
    }

    private JButton secondaryButton(String label, Runnable action) {
        JButton button = new JButton(label);
        AppTheme.styleSecondaryButton(button);
        button.addActionListener(event -> action.run());
        return button;
    }

    private void loadTimecard() {
        model.setRowCount(0);
        Employee employee = getSelectedEmployee();
        if (employee == null) {
            setStatus("No employee selected.");
            return;
        }

        List<AttendanceRecord> records = attendanceRepository.findByEmployeeId(employee.getEmployeeId());
        if (records.isEmpty()) {
            setStatus("No attendance records for " + employee.getFullName() + ".");
            return;
        }

        for (AttendanceRecord record : records) {
            model.addRow(new Object[]{
                    record.getWorkDate(),
                    record.getDayName(),
                    record.getTimeIn(),
                    record.getBreakOut(),
                    record.getBreakIn(),
                    record.getTimeOut(),
                    record.getHoursWorked(),
                    record.getOvertimeHours(),
                    record.getRemarks()
            });
        }

        setStatus("Loaded " + records.size() + " attendance rows for " + employee.getFullName() + ".");
    }

    private void clearForm() {
        workDateField.setText("");
        dayField.setText("");
        timeInField.setText("");
        breakOutField.setText("");
        breakInField.setText("");
        timeOutField.setText("");
        hoursField.setText("0");
        overtimeField.setText("0");
        remarksField.setText("Present");
        setStatus("Ready for a new attendance record.");
    }

    private void fillFormFromSelection() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageDialogs.info(this, "Select a timecard row first.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        workDateField.setText(String.valueOf(model.getValueAt(modelRow, 0)));
        dayField.setText(String.valueOf(model.getValueAt(modelRow, 1)));
        timeInField.setText(valueWithoutDash(model.getValueAt(modelRow, 2)));
        breakOutField.setText(valueWithoutDash(model.getValueAt(modelRow, 3)));
        breakInField.setText(valueWithoutDash(model.getValueAt(modelRow, 4)));
        timeOutField.setText(valueWithoutDash(model.getValueAt(modelRow, 5)));
        hoursField.setText(String.valueOf(model.getValueAt(modelRow, 6)));
        overtimeField.setText(String.valueOf(model.getValueAt(modelRow, 7)));
        remarksField.setText(String.valueOf(model.getValueAt(modelRow, 8)));
        setStatus("Editing " + workDateField.getText() + ".");
    }

    private void saveRecord() {
        Employee employee = getSelectedEmployee();
        if (employee == null) {
            MessageDialogs.info(this, "Select an employee first.");
            return;
        }

        try {
            attendanceService.save(
                    employee,
                    workDateField.getText(),
                    dayField.getText(),
                    timeInField.getText(),
                    breakOutField.getText(),
                    breakInField.getText(),
                    timeOutField.getText(),
                    parseDouble(hoursField.getText(), "Hours"),
                    parseDouble(overtimeField.getText(), "Overtime"),
                    remarksField.getText()
            );
            loadTimecard();
            setStatus("Saved attendance record for " + employee.getFullName() + ".");
        } catch (Exception exception) {
            MessageDialogs.error(this, exception.getMessage());
        }
    }

    private void exportPdf() {
        Employee employee = getSelectedEmployee();
        if (employee == null) {
            MessageDialogs.info(this, "Select an employee first.");
            return;
        }

        List<AttendanceRecord> records = attendanceRepository.findByEmployeeId(employee.getEmployeeId());
        if (records.isEmpty()) {
            MessageDialogs.info(this, "No attendance records to export.");
            return;
        }

        Path path = pdfReportWriter.writeTimecard("timecard-" + employee.getEmployeeId() + ".pdf", employee, records);
        pdfExportService.showExported(this, path);
    }

    private double parseDouble(String value, String label) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(label + " must be a number.");
        }
    }

    private String valueWithoutDash(Object value) {
        String text = String.valueOf(value);
        return "-".equals(text) ? "" : text;
    }

    private Employee getSelectedEmployee() {
        int selectedIndex = employeeSelect.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= employees.size()) {
            return null;
        }

        return employees.get(selectedIndex);
    }

    private void selectEmployeeWithRecords() {
        for (int index = 0; index < employees.size(); index++) {
            Employee employee = employees.get(index);
            if (!attendanceRepository.findByEmployeeId(employee.getEmployeeId()).isEmpty()) {
                employeeSelect.setSelectedIndex(index);
                return;
            }
        }
    }

    private void setStatus(String message) {
        statusLabel.setText(message == null || message.isBlank() ? " " : message);
    }
}
