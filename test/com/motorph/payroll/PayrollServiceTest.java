package com.motorph.payroll;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.PayrollResult;
import com.motorph.payroll.model.Payslip;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.service.PayrollCalculator;
import com.motorph.payroll.service.PayrollService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayrollServiceTest {
    @Test
    void calculatesNamedDeductionsAndNetPay() {
        Employee employee = sampleEmployee();

        PayrollResult result = new PayrollCalculator().calculate(employee);

        assertEquals(1203.75, result.getSssDeduction(), 0.01);
        assertEquals(668.75, result.getPhilhealthDeduction(), 0.01);
        assertEquals(100.00, result.getPagibigDeduction(), 0.01);
        assertEquals(2675.00, result.getWithholdingTax(), 0.01);
        assertEquals(25602.50, result.getNetPay(), 0.01);
    }

    @Test
    void payslipUsesPayrollCalculatorValues() {
        Employee employee = sampleEmployee();

        PayrollResult result = new PayrollCalculator().calculate(employee);
        Payslip payslip = new PayrollService().createSamplePayslip(employee);

        assertEquals(result.getDeductions(), payslip.getDeductions(), 0.01);
        assertEquals(result.getNetPay(), payslip.getNetPay(), 0.01);
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
