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

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id")
    private int leaveRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "leave_type", nullable = false)
    private String leaveType;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status;

    protected LeaveRequest() {
    }

    public LeaveRequest(Employee employee, String startDate, String endDate, String leaveType, String reason, String status) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
        this.status = status;
    }

    public int getLeaveRequestId() {
        return leaveRequestId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        status = "Approved";
    }

    public void reject() {
        status = "Rejected";
    }
}
