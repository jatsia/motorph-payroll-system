package com.motorph.payroll.service;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.PayrollResult;

public class PayrollCalculator {
    private static final double SAMPLE_DEDUCTION_RATE = 0.18;

    public PayrollResult calculate(Employee employee) {
        Compensation compensation = employee.getCompensation();
        double grossIncome = compensation.getGrossSemiMonthlyRate();
        double benefits = compensation.getRiceSubsidy() + compensation.getPhoneAllowance() + compensation.getClothingAllowance();
        double deductions = grossIncome * SAMPLE_DEDUCTION_RATE;

        return new PayrollResult(employee, grossIncome, benefits, deductions);
    }
}

