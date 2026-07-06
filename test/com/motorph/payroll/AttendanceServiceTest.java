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
    void savesAndUpdatesAttendanceRecord() {
        FakeAttendanceRepository repository = new FakeAttendanceRepository();
        AttendanceService service = new AttendanceService(repository);

        service.save(sampleEmployee(), "2026-01-15", "Thursday", "08:00", "12:00", "13:00", "17:00", 8, 1, "Present");
        service.save(sampleEmployee(), "2026-01-15", "Thursday", "", "", "", "18:00", 8.5, 0.5, "Overtime");

        assertEquals(1, repository.records.size());
        assertEquals("18:00", repository.records.get(0).getTimeOut());
        assertEquals(8.5, repository.records.get(0).getHoursWorked(), 0.01);
        assertEquals("Overtime", repository.records.get(0).getRemarks());
    }

    @Test
    void rejectsMissingAttendanceFields() {
        AttendanceService service = new AttendanceService(new FakeAttendanceRepository());

        assertThrows(IllegalArgumentException.class, () -> service.save(null, "2026-01-15", "Thursday", "", "", "", "", 0, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(sampleEmployee(), "", "Thursday", "", "", "", "", 0, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(sampleEmployee(), "2026-01-15", "", "", "", "", "", 0, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(sampleEmployee(), "2026-01-15", "Thursday", "", "", "", "", -1, 0, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(sampleEmployee(), "2026-01-15", "Thursday", "", "", "", "", 0, -1, "Present"));
        assertThrows(IllegalArgumentException.class, () -> service.save(sampleEmployee(), "2026-01-15", "Thursday", "", "", "", "", 0, 0, ""));
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
            return records.stream().filter(record -> record.getWorkDate().equals(workDate)).findFirst();
        }

        @Override
        public AttendanceRecord save(AttendanceRecord record) {
            if (records.isEmpty()) {
                records.add(record);
            }
            return record;
        }
    }
}
