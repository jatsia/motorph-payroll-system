package com.motorph.payroll.service;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.PayrollResult;

public class PayrollCalculator {
    private static final double SSS_RATE = 0.045;
    private static final double PHILHEALTH_RATE = 0.025;
    private static final double PAGIBIG_FIXED = 100;
    private static final double WITHHOLDING_TAX_RATE = 0.10;

    public PayrollResult calculate(Employee employee) {
        Compensation compensation = employee.getCompensation();
        double grossIncome = compensation.getGrossSemiMonthlyRate();
        double benefits = compensation.getRiceSubsidy() + compensation.getPhoneAllowance() + compensation.getClothingAllowance();
        double sssDeduction = grossIncome * SSS_RATE;
        double philhealthDeduction = grossIncome * PHILHEALTH_RATE;
        double pagibigDeduction = PAGIBIG_FIXED;
        double withholdingTax = grossIncome * WITHHOLDING_TAX_RATE;

        return new PayrollResult(employee, grossIncome, benefits, sssDeduction, philhealthDeduction, pagibigDeduction, withholdingTax);
    }
}
