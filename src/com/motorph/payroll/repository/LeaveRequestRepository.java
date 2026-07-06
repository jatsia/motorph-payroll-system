package com.motorph.payroll.repository;

import com.motorph.payroll.model.LeaveRequest;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository {
    List<LeaveRequest> findAll();

    Optional<LeaveRequest> findById(int leaveRequestId);

    LeaveRequest save(LeaveRequest request);
}
