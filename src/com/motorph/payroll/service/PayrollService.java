package com.motorph.payroll.service;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.PayrollResult;
import com.motorph.payroll.model.Payslip;

public class PayrollService {
    private final PayrollCalculator payrollCalculator;

    public PayrollService() {
        this(new PayrollCalculator());
    }

    public PayrollService(PayrollCalculator payrollCalculator) {
        this.payrollCalculator = payrollCalculator;
    }

    public Payslip createSamplePayslip(Employee employee) {
        PayrollResult result = payrollCalculator.calculate(employee);
        return new Payslip(employee, "2026-01-02", "2026-01-15", result.getGrossIncome(), result.getBenefits(), result.getDeductions());
    }

    public PayrollResult calculate(Employee employee) {
        return payrollCalculator.calculate(employee);
    }
}
