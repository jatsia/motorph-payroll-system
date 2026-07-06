package com.motorph.payroll.repository;

import com.motorph.payroll.model.AttendanceRecord;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {
    List<AttendanceRecord> findByEmployeeId(int employeeId);

    Optional<AttendanceRecord> findByEmployeeIdAndDate(int employeeId, String workDate);

    AttendanceRecord save(AttendanceRecord record);
}
