package com.motorph.payroll;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.service.report.PayrollSummaryRow;
import com.motorph.payroll.service.report.PdfReportWriter;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfReportWriterTest {
    @Test
    void writesNonEmptyPdf() throws Exception {
        Path path = new PdfReportWriter().write("test-report-" + System.nanoTime() + ".pdf", "MotorPH\nSample Report");

        assertNonEmpty(path);
    }

    @Test
    void writesStructuredPayrollSummaryPdf() throws Exception {
        Path path = new PdfReportWriter().writePayrollSummary(
                "test-payroll-summary-" + System.nanoTime() + ".pdf",
                List.of(new PayrollSummaryRow(10015, "Romualdez, Fredrick", "Account Manager", "Accounting", 26750, 4815, 25435))
        );

        assertNonEmpty(path);
    }

    @Test
    void writesStructuredPayslipPdf() throws Exception {
        Employee employee = sampleEmployee();
        Payslip payslip = new Payslip(employee, "2026-01-02", "2026-01-15", 26750, 3500, 4815);

        Path path = new PdfReportWriter().writePayslip("test-payslip-" + System.nanoTime() + ".pdf", payslip);

        assertNonEmpty(path);
    }

    @Test
    void writesStructuredTimecardPdf() throws Exception {
        Employee employee = sampleEmployee();
        AttendanceRecord record = new AttendanceRecord(employee, "2023-12-01", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");

        Path path = new PdfReportWriter().writeTimecard("test-timecard-" + System.nanoTime() + ".pdf", employee, List.of(record));

        assertNonEmpty(path);
    }

    @Test
    void writesLongTimecardPdfAcrossPages() throws Exception {
        Employee employee = sampleEmployee();
        List<AttendanceRecord> records = new ArrayList<>();
        for (int day = 1; day <= 60; day++) {
            records.add(new AttendanceRecord(employee, "2026-01-" + day, "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present"));
        }

        Path path = new PdfReportWriter().writeTimecard("test-long-timecard-" + System.nanoTime() + ".pdf", employee, records);

        assertNonEmpty(path);
    }

    private void assertNonEmpty(Path path) throws Exception {
        assertTrue(Files.exists(path));
        assertTrue(Files.size(path) > 0);
    }

    private Employee sampleEmployee() {
        Department department = new Department(4, "Accounting");
        Position position = new Position(4, department, "Account Manager");
        EmploymentStatus status = new EmploymentStatus(1, "Regular");
        Employee employee = new Employee(10015, "Romualdez", "Fredrick", "1985-03-10", "Sample Address", "023-079-009", status, position, null);
        new Compensation(employee, 53500, 1500, 1000, 1000, 26750, 318.45);
        return employee;
    }
}
