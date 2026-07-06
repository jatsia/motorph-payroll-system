package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employment_statuses")
public class EmploymentStatus {
    @Id
    @Column(name = "status_id")
    private int statusId;

    @Column(nullable = false, unique = true)
    private String name;

    protected EmploymentStatus() {
    }

    public EmploymentStatus(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getName() {
        return name;
    }
}

