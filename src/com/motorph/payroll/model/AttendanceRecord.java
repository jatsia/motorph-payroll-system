package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "attendance", uniqueConstraints = {
        @UniqueConstraint(name = "uq_attendance_employee_date", columnNames = {"employee_id", "work_date"})
})
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "work_date", nullable = false)
    private String workDate;

    @Column(name = "day_name", nullable = false)
    private String dayName;

    @Column(name = "time_in")
    private String timeIn;

    @Column(name = "break_out")
    private String breakOut;

    @Column(name = "break_in")
    private String breakIn;

    @Column(name = "time_out")
    private String timeOut;

    @Column(name = "hours_worked", nullable = false)
    private double hoursWorked;

    @Column(name = "overtime_hours", nullable = false)
    private double overtimeHours;

    @Column(nullable = false)
    private String remarks;

    protected AttendanceRecord() {
    }

    public AttendanceRecord(Employee employee, String workDate, String dayName, String timeIn, String breakOut, String breakIn, String timeOut, double hoursWorked, double overtimeHours, String remarks) {
        this.employee = employee;
        this.workDate = workDate;
        this.dayName = dayName;
        this.timeIn = timeIn;
        this.breakOut = breakOut;
        this.breakIn = breakIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = overtimeHours;
        this.remarks = remarks;
    }

    public String getWorkDate() {
        return workDate;
    }

    public String getDayName() {
        return dayName;
    }

    public String getTimeIn() {
        return valueOrDash(timeIn);
    }

    public String getBreakOut() {
        return valueOrDash(breakOut);
    }

    public String getBreakIn() {
        return valueOrDash(breakIn);
    }

    public String getTimeOut() {
        return valueOrDash(timeOut);
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public String getRemarks() {
        return remarks;
    }

    public void update(String dayName, String timeIn, String breakOut, String breakIn, String timeOut, double hoursWorked, double overtimeHours, String remarks) {
        this.dayName = dayName;
        this.timeIn = timeIn;
        this.breakOut = breakOut;
        this.breakIn = breakIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = overtimeHours;
        this.remarks = remarks;
    }

    private String valueOrDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
