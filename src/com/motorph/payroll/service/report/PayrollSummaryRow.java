package com.motorph.payroll.service.report;

public class PayrollSummaryRow {
    private final int employeeId;
    private final String fullName;
    private final String position;
    private final String department;
    private final double grossIncome;
    private final double sssDeduction;
    private final double philhealthDeduction;
    private final double pagibigDeduction;
    private final double withholdingTax;
    private final double deductions;
    private final double netPay;

    public PayrollSummaryRow(int employeeId, String fullName, String position, String department, double grossIncome, double sssDeduction, double philhealthDeduction, double pagibigDeduction, double withholdingTax, double deductions, double netPay) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.position = position;
        this.department = department;
        this.grossIncome = grossIncome;
        this.sssDeduction = sssDeduction;
        this.philhealthDeduction = philhealthDeduction;
        this.pagibigDeduction = pagibigDeduction;
        this.withholdingTax = withholdingTax;
        this.deductions = deductions;
        this.netPay = netPay;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public double getGrossIncome() {
        return grossIncome;
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
        return netPay;
    }
}
