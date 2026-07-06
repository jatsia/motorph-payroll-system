package com.motorph.payroll.ui;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.PayrollResult;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.service.PdfExportService;
import com.motorph.payroll.service.PayrollService;
import com.motorph.payroll.service.report.PayrollSummaryReportService;
import com.motorph.payroll.service.report.PdfReportWriter;
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
import java.nio.file.Path;
import java.util.List;

public class PayrollSummaryPanel extends JPanel {
    private final EmployeeRepository employeeRepository;
    private final PayrollService payrollService;
    private final PayrollSummaryReportService reportService = new PayrollSummaryReportService();
    private final PdfReportWriter pdfReportWriter = new PdfReportWriter();
    private final PdfExportService pdfExportService = new PdfExportService();
    private final DefaultTableModel model;
    private final PagePanel pagePanel;

    public PayrollSummaryPanel(EmployeeRepository employeeRepository, PayrollService payrollService) {
        this.employeeRepository = employeeRepository;
        this.payrollService = payrollService;
        this.model = DataTableFactory.readOnlyModel(
                "Employee No.", "Name", "Position", "Department", "Gross Income", "Deductions", "Net Pay"
        );

        setLayout(new BorderLayout());

        ActionToolbar toolbar = new ActionToolbar();
        toolbar.addPrimaryAction("Generate Report", this::loadSummary);
        toolbar.addSecondaryAction("Export PDF", this::exportPdf);

        JTable table = DataTableFactory.create(model, 100, 220, 220, 140, 150, 150, 150);
        this.pagePanel = new PagePanel("Payroll Summary", toolbar, new JScrollPane(table));
        add(pagePanel, BorderLayout.CENTER);
        loadSummary();
    }

    private void loadSummary() {
        model.setRowCount(0);

        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            pagePanel.setStatus("No employees available.");
            return;
        }

        for (Employee employee : employees) {
            PayrollResult result = payrollService.calculate(employee);
            model.addRow(new Object[]{
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getPosition(),
                    employee.getDepartment(),
                    MoneyFormatter.format(result.getGrossIncome()),
                    MoneyFormatter.format(result.getDeductions()),
                    MoneyFormatter.format(result.getNetPay())
            });
        }

        pagePanel.setStatus("Loaded " + employees.size() + " payroll records.");
    }

    private void exportPdf() {
        loadSummary();

        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            MessageDialogs.info(this, "No payroll records to export.");
            return;
        }

        Path path = pdfReportWriter.writePayrollSummary("payroll-summary.pdf", reportService.buildRows(employees, payrollService));
        pdfExportService.showExported(this, path);
    }
}
