package com.motorph.payroll.repository;

import com.motorph.payroll.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();

    Optional<Employee> findById(int employeeId);

    Employee save(Employee employee);

    void deleteById(int employeeId);
}
