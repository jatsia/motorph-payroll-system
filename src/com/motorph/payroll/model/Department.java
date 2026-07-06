package com.motorph.payroll.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "department_id")
    private int departmentId;

    @Column(nullable = false, unique = true)
    private String name;

    protected Department() {
    }

    public Department(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }
}

