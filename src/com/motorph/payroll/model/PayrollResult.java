package com.motorph.payroll.model;

public class PayrollResult {
    private final Employee employee;
    private final double grossIncome;
    private final double benefits;
    private final double sssDeduction;
    private final double philhealthDeduction;
    private final double pagibigDeduction;
    private final double withholdingTax;
    private final double deductions;

    public PayrollResult(Employee employee, double grossIncome, double benefits, double sssDeduction, double philhealthDeduction, double pagibigDeduction, double withholdingTax) {
        this.employee = employee;
        this.grossIncome = grossIncome;
        this.benefits = benefits;
        this.sssDeduction = sssDeduction;
        this.philhealthDeduction = philhealthDeduction;
        this.pagibigDeduction = pagibigDeduction;
        this.withholdingTax = withholdingTax;
        this.deductions = sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax;
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

    public double getSssDeduction() {
        return sssDeduction;
    }

    public double getPhilhealthDeduction() {
        return philhealthDeduction;
    }

    public double getPagibigDeduction() {
        return pagibigDeduction;
    }

    public double getWithholdingTax() {
        return withholdingTax;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return grossIncome + benefits - deductions;
    }
}
