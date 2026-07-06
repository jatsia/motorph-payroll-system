package com.motorph.payroll;

import com.motorph.payroll.database.DataSeeder;
import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.repository.AttendanceRepository;
import com.motorph.payroll.repository.EmployeeRepository;
import com.motorph.payroll.repository.RepositoryFactory;
import com.motorph.payroll.service.AttendanceService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersistenceIntegrationTest {
    @Test
    void persistedEmployeesAndAttendanceLoadFromMySql() {
        new DataSeeder().seed();
        RepositoryFactory repositoryFactory = new RepositoryFactory();

        assertTrue(repositoryFactory.createEmployeeRepository().findAll().size() >= 4);
        assertTrue(repositoryFactory.createAttendanceRepository().findByEmployeeId(10001).size() >= 3);
        assertTrue(repositoryFactory.createAttendanceRepository().findByEmployeeId(10015).size() >= 20);
    }

    @Test
    void seederCanRunRepeatedlyWithoutDuplicatingSampleAttendance() {
        new DataSeeder().seed();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        AttendanceRepository attendanceRepository = repositoryFactory.createAttendanceRepository();
        int firstCount = attendanceRepository.findByEmployeeId(10001).size();

        new DataSeeder().seed();

        assertEquals(firstCount, attendanceRepository.findByEmployeeId(10001).size());
    }

    @Test
    void missingEmployeeAndAttendanceReturnEmptyResults() {
        new DataSeeder().seed();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        EmployeeRepository employeeRepository = repositoryFactory.createEmployeeRepository();
        AttendanceRepository attendanceRepository = repositoryFactory.createAttendanceRepository();

        assertTrue(employeeRepository.findById(99999).isEmpty());
        assertTrue(attendanceRepository.findByEmployeeId(99999).isEmpty());
    }

    @Test
    void savesAndUpdatesAttendanceInMySql() {
        new DataSeeder().seed();
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        EmployeeRepository employeeRepository = repositoryFactory.createEmployeeRepository();
        AttendanceRepository attendanceRepository = repositoryFactory.createAttendanceRepository();
        AttendanceService attendanceService = new AttendanceService(attendanceRepository);
        Employee employee = employeeRepository.findById(10015).orElseThrow();
        String workDate = "2026-07-06-test";

        attendanceService.save(employee, workDate, "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        attendanceService.save(employee, workDate, "Mon", "09:00 AM", "12:00 PM", "01:00 PM", "06:00 PM", 8, 1, "Adjusted");

        Optional<AttendanceRecord> saved = attendanceRepository.findByEmployeeIdAndDate(10015, workDate);
        assertTrue(saved.isPresent());
        assertEquals("09:00 AM", saved.get().getTimeIn());
        assertEquals(1, saved.get().getOvertimeHours(), 0.01);
        assertEquals("Adjusted", saved.get().getRemarks());
    }
}
