package com.motorph.payroll.service;

import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.LeaveRequest;
import com.motorph.payroll.repository.LeaveRequestRepository;

public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public LeaveRequest submit(Employee employee, String startDate, String endDate, String leaveType, String reason) {
        validate(employee, startDate, endDate, leaveType, reason);
        LeaveRequest request = new LeaveRequest(employee, startDate.trim(), endDate.trim(), leaveType.trim(), reason.trim(), "Pending");
        return leaveRequestRepository.save(request);
    }

    public LeaveRequest approve(int leaveRequestId) {
        LeaveRequest request = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));
        request.approve();
        return leaveRequestRepository.save(request);
    }

    public LeaveRequest reject(int leaveRequestId) {
        LeaveRequest request = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));
        request.reject();
        return leaveRequestRepository.save(request);
    }

    private void validate(Employee employee, String startDate, String endDate, String leaveType, String reason) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required.");
        }

        if (startDate == null || startDate.isBlank()) {
            throw new IllegalArgumentException("Start date is required.");
        }

        if (endDate == null || endDate.isBlank()) {
            throw new IllegalArgumentException("End date is required.");
        }

        if (leaveType == null || leaveType.isBlank()) {
            throw new IllegalArgumentException("Leave type is required.");
        }

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason is required.");
        }
    }
}
