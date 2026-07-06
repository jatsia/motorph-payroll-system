package com.motorph.payroll.ui;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.service.PdfExportService;
import com.motorph.payroll.service.PayrollService;
import com.motorph.payroll.service.report.PayslipReportService;
import com.motorph.payroll.service.report.PdfReportWriter;
import com.motorph.payroll.ui.components.ActionToolbar;
import com.motorph.payroll.ui.components.MessageDialogs;
import com.motorph.payroll.ui.components.PagePanel;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PayslipPanel extends JPanel {
    private final PayrollService payrollService;
    private final PayslipReportService reportService = new PayslipReportService();
    private final PdfReportWriter pdfReportWriter = new PdfReportWriter();
    private final PdfExportService pdfExportService = new PdfExportService();
    private final List<Employee> employees;
    private final JComboBox<String> employeeSelect;
    private final JTextArea textArea;
    private final PagePanel pagePanel;
    private Payslip currentPayslip;

    public PayslipPanel(EmployeeRepository employeeRepository, PayrollService payrollService) {
        this.payrollService = payrollService;
        this.employees = new ArrayList<>(employeeRepository.findAll());
        this.employeeSelect = new JComboBox<>();
        this.textArea = new JTextArea();

        setLayout(new BorderLayout());

        for (Employee employee : employees) {
            employeeSelect.addItem(employee.getEmployeeId() + " - " + employee.getFullName());
        }
        employeeSelect.addActionListener(event -> generatePayslip());

        ActionToolbar toolbar = new ActionToolbar();
        toolbar.add(employeeSelect);
        toolbar.addPrimaryAction("Generate Payslip", this::generatePayslip);
        toolbar.addSecondaryAction("Export PDF", this::exportPdf);
        toolbar.addSecondaryAction("Print", this::printPayslip);

        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        this.pagePanel = new PagePanel("Payslip", toolbar, new JScrollPane(textArea));
        add(pagePanel, BorderLayout.CENTER);
        generatePayslip();
    }

    private void generatePayslip() {
        Employee employee = getSelectedEmployee();
        if (employee == null) {
            textArea.setText("No employee data available.");
            pagePanel.setStatus("No employee selected.");
            return;
        }

        currentPayslip = payrollService.createSamplePayslip(employee);
        textArea.setText(reportService.buildText(currentPayslip));
        textArea.setCaretPosition(0);
        pagePanel.setStatus("Generated payslip for " + employee.getFullName() + ".");
    }

    private void exportPdf() {
        if (currentPayslip == null) {
            generatePayslip();
        }

        if (currentPayslip == null) {
            MessageDialogs.info(this, "No payslip to export.");
            return;
        }

        Path path = pdfReportWriter.writePayslip("payslip-" + currentPayslip.getEmployee().getEmployeeId() + ".pdf", currentPayslip);
        pdfExportService.showExported(this, path);
    }

    private void printPayslip() {
        try {
            textArea.print();
        } catch (Exception exception) {
            MessageDialogs.error(this, "Could not print payslip.");
        }
    }

    private Employee getSelectedEmployee() {
        int selectedIndex = employeeSelect.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= employees.size()) {
            return null;
        }

        return employees.get(selectedIndex);
    }
}
