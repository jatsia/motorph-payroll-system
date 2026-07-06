package com.motorph.payroll.service;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.repository.AttendanceRepository;

public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceRecord save(Employee employee, String workDate, String dayName, String timeIn, String breakOut, String breakIn, String timeOut, double hoursWorked, double overtimeHours, String remarks) {
        validate(employee, workDate, dayName, hoursWorked, overtimeHours, remarks);

        AttendanceRecord record = attendanceRepository.findByEmployeeIdAndDate(employee.getEmployeeId(), workDate)
                .orElseGet(() -> new AttendanceRecord(employee, workDate, dayName, null, null, null, null, 0, 0, remarks));

        record.update(dayName, blankToNull(timeIn), blankToNull(breakOut), blankToNull(breakIn), blankToNull(timeOut), hoursWorked, overtimeHours, remarks.trim());
        return attendanceRepository.save(record);
    }

    private void validate(Employee employee, String workDate, String dayName, double hoursWorked, double overtimeHours, String remarks) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required.");
        }

        if (workDate == null || workDate.isBlank()) {
            throw new IllegalArgumentException("Work date is required.");
        }

        if (dayName == null || dayName.isBlank()) {
            throw new IllegalArgumentException("Day is required.");
        }

        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative.");
        }

        if (overtimeHours < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative.");
        }

        if (remarks == null || remarks.isBlank()) {
            throw new IllegalArgumentException("Remarks is required.");
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}

