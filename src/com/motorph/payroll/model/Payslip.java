package com.motorph.payroll.model;

public class Payslip {
    private final Employee employee;
    private final String periodStart;
    private final String periodEnd;
    private final double grossIncome;
    private final double benefits;
    private final double sssDeduction;
    private final double philhealthDeduction;
    private final double pagibigDeduction;
    private final double withholdingTax;
    private final double deductions;

    public Payslip(Employee employee, String periodStart, String periodEnd, double grossIncome, double benefits, double sssDeduction, double philhealthDeduction, double pagibigDeduction, double withholdingTax) {
        this.employee = employee;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.grossIncome = grossIncome;
        this.benefits = benefits;
        this.sssDeduction = sssDeduction;
        this.philhealthDeduction = philhealthDeduction;
        this.pagibigDeduction = pagibigDeduction;
        this.withholdingTax = withholdingTax;
        this.deductions = sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax;
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
}
