package com.motorph.payroll;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.service.report.PdfReportWriter;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfReportWriterTest {
    @Test
    void writesPayslipPdf() throws Exception {
        Employee employee = sampleEmployee();
        Payslip payslip = new Payslip(employee, "2026-01-02", "2026-01-15", 26750, 3500, 1203.75, 668.75, 100, 2675);

        Path path = new PdfReportWriter().writePayslip("test-payslip-" + System.nanoTime() + ".pdf", payslip);

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
