package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(name = "position_id")
    private int positionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String title;

    protected Position() {
    }

    public Position(int positionId, Department department, String title) {
        this.positionId = positionId;
        this.department = department;
        this.title = title;
    }

    public int getPositionId() {
        return positionId;
    }

    public Department getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }
}

