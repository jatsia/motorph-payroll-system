package com.motorph.payroll.service.report;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Employee;

import java.util.List;

public class TimecardReportService {
    public String buildText(Employee employee, List<AttendanceRecord> records) {
        StringBuilder builder = new StringBuilder();
        builder.append("""
                MotorPH
                EMPLOYEE TIMECARD

                Employee ID:       %d
                Employee Name:     %s
                Position/Dept:     %s / %s

                Date | Day | Time In | Break Out | Break In | Time Out | Hours | Remarks
                """.formatted(
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getDepartment()
        ));

        for (AttendanceRecord record : records) {
            builder.append("%s | %s | %s | %s | %s | %s | %.2f | %s%n".formatted(
                    record.getWorkDate(),
                    record.getDayName(),
                    record.getTimeIn(),
                    record.getBreakOut(),
                    record.getBreakIn(),
                    record.getTimeOut(),
                    record.getHoursWorked(),
                    record.getRemarks()
            ));
        }

        return builder.toString();
    }
}

