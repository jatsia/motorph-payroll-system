package com.motorph.payroll.repository;

import com.motorph.payroll.model.EmploymentStatus;

import java.util.List;

public interface EmploymentStatusRepository {
    List<EmploymentStatus> findAll();
}
