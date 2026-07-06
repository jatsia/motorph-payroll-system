package com.motorph.payroll.model;

public class PayrollResult {
    private final Employee employee;
    private final double grossIncome;
    private final double benefits;
    private final double deductions;

    public PayrollResult(Employee employee, double grossIncome, double benefits, double deductions) {
        this.employee = employee;
        this.grossIncome = grossIncome;
        this.benefits = benefits;
        this.deductions = deductions;
    }

    public Employee getEmployee() {
        return employee;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public double getBenefits() {
        return benefits;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return grossIncome + benefits - deductions;
    }
}

