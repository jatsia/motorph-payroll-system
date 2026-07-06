package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "compensation")
public class Compensation {
    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "basic_salary", nullable = false)
    private double basicSalary;

    @Column(name = "rice_subsidy", nullable = false)
    private double riceSubsidy;

    @Column(name = "phone_allowance", nullable = false)
    private double phoneAllowance;

    @Column(name = "clothing_allowance", nullable = false)
    private double clothingAllowance;

    @Column(name = "gross_semi_monthly_rate", nullable = false)
    private double grossSemiMonthlyRate;

    @Column(name = "hourly_rate", nullable = false)
    private double hourlyRate;

    protected Compensation() {
    }

    public Compensation(Employee employee, double basicSalary, double riceSubsidy, double phoneAllowance, double clothingAllowance, double grossSemiMonthlyRate, double hourlyRate) {
        this.employee = employee;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        employee.setCompensation(this);
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public double getGrossSemiMonthlyRate() {
        return grossSemiMonthlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
