package com.motorph.payroll;

import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.LeaveRequest;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.repository.LeaveRequestRepository;
import com.motorph.payroll.service.LeaveRequestService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeaveRequestServiceTest {
    @Test
    void submitsPendingLeaveRequest() {
        FakeLeaveRequestRepository repository = new FakeLeaveRequestRepository();
        LeaveRequestService service = new LeaveRequestService(repository);

        service.submit(sampleEmployee(), "2026-01-20", "2026-01-21", "Vacation Leave", "Family commitment");

        assertEquals(1, repository.requests.size());
        assertEquals("Pending", repository.requests.get(0).getStatus());
    }

    @Test
    void rejectsMissingLeaveRequestFields() {
        LeaveRequestService service = new LeaveRequestService(new FakeLeaveRequestRepository());

        assertThrows(IllegalArgumentException.class, () -> service.submit(null, "2026-01-20", "2026-01-21", "Vacation Leave", "Reason"));
        assertThrows(IllegalArgumentException.class, () -> service.submit(sampleEmployee(), "", "2026-01-21", "Vacation Leave", "Reason"));
        assertThrows(IllegalArgumentException.class, () -> service.submit(sampleEmployee(), "2026-01-20", "", "Vacation Leave", "Reason"));
        assertThrows(IllegalArgumentException.class, () -> service.submit(sampleEmployee(), "2026-01-20", "2026-01-21", "", "Reason"));
        assertThrows(IllegalArgumentException.class, () -> service.submit(sampleEmployee(), "2026-01-20", "2026-01-21", "Vacation Leave", ""));
    }

    @Test
    void approvesAndRejectsExistingRequest() {
        FakeLeaveRequestRepository repository = new FakeLeaveRequestRepository();
        LeaveRequest request = new LeaveRequest(sampleEmployee(), "2026-01-20", "2026-01-21", "Vacation Leave", "Reason", "Pending");
        repository.save(request);
        LeaveRequestService service = new LeaveRequestService(repository);

        service.approve(1);
        assertEquals("Approved", repository.requests.get(0).getStatus());

        service.reject(1);
        assertEquals("Rejected", repository.requests.get(0).getStatus());
    }

    private Employee sampleEmployee() {
        Department department = new Department(4, "Accounting");
        Position position = new Position(4, department, "Account Manager");
        EmploymentStatus status = new EmploymentStatus(1, "Regular");
        Employee employee = new Employee(10015, "Romualdez", "Fredrick", "1985-03-10", "Sample Address", "023-079-009", status, position, null);
        new Compensation(employee, 53500, 1500, 1000, 1000, 26750, 318.45);
        return employee;
    }

    private static class FakeLeaveRequestRepository implements LeaveRequestRepository {
        private final List<LeaveRequest> requests = new ArrayList<>();

        @Override
        public List<LeaveRequest> findAll() {
            return requests;
        }

        @Override
        public Optional<LeaveRequest> findById(int leaveRequestId) {
            if (leaveRequestId == 1 && !requests.isEmpty()) {
                return Optional.of(requests.get(0));
            }
            return Optional.empty();
        }

        @Override
        public LeaveRequest save(LeaveRequest request) {
            if (!requests.contains(request)) {
                requests.add(request);
            }
            return request;
        }
    }
}
