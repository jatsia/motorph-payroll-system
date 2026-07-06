package com.motorph.payroll;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.service.PayrollService;
import com.motorph.payroll.service.report.PayrollSummaryReportService;
import com.motorph.payroll.service.report.PayslipReportService;
import com.motorph.payroll.service.report.TimecardReportService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {
    @Test
    void payslipReportContainsEmployeeAndTakeHomePay() {
        Employee employee = sampleEmployee();
        Payslip payslip = new Payslip(employee, "2026-01-02", "2026-01-15", 26750, 3500, 4815);

        String report = new PayslipReportService().buildText(payslip);

        assertTrue(report.contains("Romualdez, Fredrick"));
        assertTrue(report.contains("PHP 25,435.00"));
    }

    @Test
    void payrollSummaryHandlesEmptyEmployeeList() {
        String report = new PayrollSummaryReportService().buildText(List.of(), new PayrollService());

        assertTrue(report.contains("MONTHLY PAYROLL SUMMARY REPORT"));
        assertTrue(report.contains("PHP 0.00"));
    }

    @Test
    void timecardReportHandlesNoAttendanceRows() {
        Employee employee = sampleEmployee();

        String report = new TimecardReportService().buildText(employee, List.<AttendanceRecord>of());

        assertTrue(report.contains("EMPLOYEE TIMECARD"));
        assertTrue(report.contains("Romualdez, Fredrick"));
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
