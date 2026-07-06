package com.motorph.payroll;

import com.motorph.payroll.database.DataSeeder;
import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.repository.RepositoryFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeRepositoryTest {
    @Test
    void savesAndDeletesEmployeeInMysql() {
        new DataSeeder().seed();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        EmployeeRepository employeeRepository = repositoryFactory.createEmployeeRepository();
        EmploymentStatus status = repositoryFactory.createEmploymentStatusRepository().findAll().get(0);
        Position position = repositoryFactory.createPositionRepository().findAll().get(0);
        int employeeId = 19991;

        Employee employee = new Employee(employeeId, "Test", "Employee", "1990-01-01", "Sample Address", "09170000000", status, position, null);
        new Compensation(employee, 50000, 1500, 1000, 1000, 25000, 300);

        employeeRepository.save(employee);
        assertTrue(employeeRepository.findById(employeeId).isPresent());

        employeeRepository.deleteById(employeeId);
        assertTrue(employeeRepository.findById(employeeId).isEmpty());
    }
}
