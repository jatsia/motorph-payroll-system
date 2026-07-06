package com.motorph.payroll.model;

public class Payslip {
    private final Employee employee;
    private final String periodStart;
    private final String periodEnd;
    private final double grossIncome;
    private final double benefits;
    private final double deductions;

    public Payslip(Employee employee, String periodStart, String periodEnd, double grossIncome, double benefits, double deductions) {
        this.employee = employee;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.grossIncome = grossIncome;
        this.benefits = benefits;
        this.deductions = deductions;
    }

    public String getPayslipNumber() {
        return employee.getEmployeeId() + "-" + periodEnd;
    }

    public double getNetPay() {
        return grossIncome + benefits - deductions;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
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
}

