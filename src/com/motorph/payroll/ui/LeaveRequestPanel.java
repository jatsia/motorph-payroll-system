package com.motorph.payroll.ui;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.LeaveRequest;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.repository.LeaveRequestRepository;
import com.motorph.payroll.service.LeaveRequestService;
import com.motorph.payroll.ui.components.ActionToolbar;
import com.motorph.payroll.ui.components.DataTableFactory;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.components.PagePanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestPanel extends JPanel {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestService leaveRequestService;
    private final List<Employee> employees;
    private final JComboBox<String> employeeSelect = new JComboBox<>();
    private final JComboBox<String> leaveTypeSelect = new JComboBox<>(new String[]{"Vacation Leave", "Sick Leave", "Emergency Leave"});
    private final JTextField startDateField = new JTextField();
    private final JTextField endDateField = new JTextField();
    private final JTextField reasonField = new JTextField();
    private final DefaultTableModel model;
    private final JTable table;
    private final PagePanel pagePanel;

    public LeaveRequestPanel(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveRequestService = new LeaveRequestService(leaveRequestRepository);
        this.employees = new ArrayList<>(employeeRepository.findAll());
        this.model = DataTableFactory.readOnlyModel("ID", "Employee", "Start", "End", "Type", "Reason", "Status");
        this.table = DataTableFactory.create(model, 60, 220, 100, 100, 130, 260, 110);

        setLayout(new BorderLayout());

        for (Employee employee : employees) {
            employeeSelect.addItem(employee.getEmployeeId() + " - " + employee.getFullName());
        }

        ActionToolbar toolbar = new ActionToolbar();
        toolbar.addPrimaryAction("Submit Request", this::submitRequest);
        toolbar.addSecondaryAction("Approve Selected", this::approveSelected);
        toolbar.addSecondaryAction("Reject Selected", this::rejectSelected);
        toolbar.addSecondaryAction("Refresh", this::loadRequests);

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);
        content.add(buildForm(), BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);

        this.pagePanel = new PagePanel("Leave Requests", toolbar, content);
        add(pagePanel, BorderLayout.CENTER);
        clearForm();
        loadRequests();
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridLayout(2, 5, 8, 8));
        form.setOpaque(false);
        form.add(new JLabel("Employee"));
        form.add(new JLabel("Start Date"));
        form.add(new JLabel("End Date"));
        form.add(new JLabel("Leave Type"));
        form.add(new JLabel("Reason"));
        form.add(employeeSelect);
        form.add(startDateField);
        form.add(endDateField);
        form.add(leaveTypeSelect);
        form.add(reasonField);
        return form;
    }

    private void submitRequest() {
        Employee employee = getSelectedEmployee();
        try {
            leaveRequestService.submit(
                    employee,
                    startDateField.getText(),
                    endDateField.getText(),
                    String.valueOf(leaveTypeSelect.getSelectedItem()),
                    reasonField.getText()
            );
            clearForm();
            loadRequests();
            pagePanel.setStatus("Leave request submitted.");
        } catch (Exception exception) {
            MessageDialogs.error(this, exception.getMessage());
        }
    }

    private void approveSelected() {
        updateSelectedStatus(true);
    }

    private void rejectSelected() {
        updateSelectedStatus(false);
    }

    private void updateSelectedStatus(boolean approved) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageDialogs.info(this, "Select a leave request first.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int requestId = (Integer) model.getValueAt(modelRow, 0);
        if (approved) {
            leaveRequestService.approve(requestId);
        } else {
            leaveRequestService.reject(requestId);
        }

        loadRequests();
        pagePanel.setStatus(approved ? "Leave request approved." : "Leave request rejected.");
    }

    private void loadRequests() {
        model.setRowCount(0);
        List<LeaveRequest> requests = leaveRequestRepository.findAll();
        for (LeaveRequest request : requests) {
            model.addRow(new Object[]{
                    request.getLeaveRequestId(),
                    request.getEmployee().getFullName(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getLeaveType(),
                    request.getReason(),
                    request.getStatus()
            });
        }

        pagePanel.setStatus("Loaded " + requests.size() + " leave requests.");
    }

    private void clearForm() {
        startDateField.setText("2026-01-20");
        endDateField.setText("2026-01-20");
        leaveTypeSelect.setSelectedIndex(0);
        reasonField.setText("");
    }

    private Employee getSelectedEmployee() {
        int selectedIndex = employeeSelect.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= employees.size()) {
            return null;
        }

        return employees.get(selectedIndex);
    }
}
