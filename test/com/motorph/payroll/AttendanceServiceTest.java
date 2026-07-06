package com.motorph.payroll;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.repository.AttendanceRepository;
import com.motorph.payroll.service.AttendanceService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AttendanceServiceTest {
    @Test
    void rejectsMissingDateDayRemarksAndNegativeHours() {
        AttendanceService service = new AttendanceService(new FakeAttendanceRepository());
        Employee employee = sampleEmployee();

        assertThrows(IllegalArgumentException.class, () -> service.save(null, "2026-01-01", "Mon", "", "", "", "", 8, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(employee, "", "Mon", "", "", "", "", 8, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(employee, "2026-01-01", "", "", "", "", "", 8, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(employee, "2026-01-01", "Mon", "", "", "", "", -1, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(employee, "2026-01-01", "Mon", "", "", "", "", 8, -1, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(employee, "2026-01-01", "Mon", "", "", "", "", 8, 0, ""));
    }

    @Test
    void savesAndUpdatesSameEmployeeDate() {
        FakeAttendanceRepository repository = new FakeAttendanceRepository();
        AttendanceService service = new AttendanceService(repository);
        Employee employee = sampleEmployee();

        service.save(employee, "2026-01-01", "Thu", "08:00 AM", "", "", "05:00 PM", 8, 0, "Present");
        service.save(employee, "2026-01-01", "Thu", "09:00 AM", "", "", "06:00 PM", 8, 1, "Adjusted");

        assertEquals(1, repository.records.size());
        assertEquals("09:00 AM", repository.records.get(0).getTimeIn());
        assertEquals(1, repository.records.get(0).getOvertimeHours(), 0.01);
        assertEquals("Adjusted", repository.records.get(0).getRemarks());
    }

    @Test
    void storesBlankTimesAsDashesAndTrimsRemarks() {
        FakeAttendanceRepository repository = new FakeAttendanceRepository();
        AttendanceService service = new AttendanceService(repository);
        Employee employee = sampleEmployee();

        service.save(employee, "2026-01-02", "Fri", " ", "", "", "", 0, 0, " Leave ");

        AttendanceRecord record = repository.records.get(0);
        assertEquals("-", record.getTimeIn());
        assertEquals("-", record.getBreakOut());
        assertEquals("-", record.getBreakIn());
        assertEquals("-", record.getTimeOut());
        assertEquals("Leave", record.getRemarks());
    }

    private Employee sampleEmployee() {
        Department department = new Department(4, "Accounting");
        Position position = new Position(4, department, "Account Manager");
        EmploymentStatus status = new EmploymentStatus(1, "Regular");
        Employee employee = new Employee(10015, "Romualdez", "Fredrick", "1985-03-10", "Sample Address", "023-079-009", status, position, null);
        new Compensation(employee, 53500, 1500, 1000, 1000, 26750, 318.45);
        return employee;
    }

    private static class FakeAttendanceRepository implements AttendanceRepository {
        private final List<AttendanceRecord> records = new ArrayList<>();

        @Override
        public List<AttendanceRecord> findByEmployeeId(int employeeId) {
            return records;
        }

        @Override
        public Optional<AttendanceRecord> findByEmployeeIdAndDate(int employeeId, String workDate) {
            return records.stream()
                    .filter(record -> record.getWorkDate().equals(workDate))
                    .findFirst();
        }

        @Override
        public AttendanceRecord save(AttendanceRecord record) {
            if (!records.contains(record)) {
                records.add(record);
            }
            return record;
        }
    }
}
