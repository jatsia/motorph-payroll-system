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
    void samplePayslipCalculatesExpectedTakeHomePay() {
        Employee employee = sampleEmployee();

        Payslip payslip = new PayrollService().createSamplePayslip(employee);

        assertEquals(25435.00, payslip.getNetPay(), 0.01);
    }

    @Test
    void payrollCalculatorAndPayslipUseSameValues() {
        Employee employee = sampleEmployee();

        PayrollResult result = new PayrollCalculator().calculate(employee);
        Payslip payslip = new PayrollService().createSamplePayslip(employee);

        assertEquals(result.getGrossIncome(), payslip.getGrossIncome(), 0.01);
        assertEquals(result.getBenefits(), payslip.getBenefits(), 0.01);
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
