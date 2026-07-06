package com.motorph.payroll.database;

import com.motorph.payroll.model.AttendanceRecord;
import com.motorph.payroll.model.Compensation;
import com.motorph.payroll.model.Department;
import com.motorph.payroll.model.Employee;
import com.motorph.payroll.model.EmploymentStatus;
import com.motorph.payroll.model.GovernmentIds;
import com.motorph.payroll.model.LeaveRequest;
import com.motorph.payroll.model.Position;
import com.motorph.payroll.model.UserAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataSeeder {
    public void seed() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long employeeCount = session.createQuery("select count(e) from Employee e", Long.class).getSingleResult();
            Transaction transaction = session.beginTransaction();

            if (employeeCount == 0) {
                Department leadership = new Department(1, "Leadership");
                Department it = new Department(2, "IT");
                Department hr = new Department(3, "HR");
                Department accounting = new Department(4, "Accounting");
                Department marketing = new Department(5, "Marketing");
                session.persist(leadership);
                session.persist(it);
                session.persist(hr);
                session.persist(accounting);
                session.persist(marketing);

                EmploymentStatus regular = new EmploymentStatus(1, "Regular");
                EmploymentStatus probationary = new EmploymentStatus(2, "Probationary");
                session.persist(regular);
                session.persist(probationary);

                Position ceo = new Position(1, leadership, "Chief Executive Officer");
                Position itOperations = new Position(2, it, "IT Operations and Systems");
                Position hrManager = new Position(3, hr, "HR Manager");
                Position accountManager = new Position(4, accounting, "Account Manager");
                Position customerRelations = new Position(5, marketing, "Customer Service and Relations");
                session.persist(ceo);
                session.persist(itOperations);
                session.persist(hrManager);
                session.persist(accountManager);
                session.persist(customerRelations);

                Employee garcia = new Employee(10001, "Garcia", "Manuel III", "1983-10-11", "Valero Carpark Building Valero Street, Makati City", "966-860-270", regular, ceo, null);
                Employee hernandez = new Employee(10005, "Hernandez", "Eduard", "1989-09-23", "National Highway, Gingoog, Misamis Occidental", "088-861-012", regular, itOperations, garcia);
                Employee romualdez = new Employee(10015, "Romualdez", "Fredrick", "1985-03-10", "22A/52 Lubowitz Meadows, Pililla, Zambales", "023-079-009", regular, accountManager, garcia);
                Employee santos = new Employee(10034, "Santos", "Beatriz", "1990-08-07", "Agapita Building, Metro Manila", "526-639-511", regular, customerRelations, garcia);
                session.persist(garcia);
                session.persist(hernandez);
                session.persist(romualdez);
                session.persist(santos);

                session.persist(new GovernmentIds(garcia, "44-4506057-3", "820126853951", "442-605-657-000", "691295330870"));
                session.persist(new GovernmentIds(hernandez, "50-5577638-1", "957436191812", "031-702-374-000", "952347222457"));
                session.persist(new GovernmentIds(romualdez, "26-8768374-1", "308366860059", "598-065-761-000", "223057707853"));
                session.persist(new GovernmentIds(santos, "20-2987501-5", "918460050077", "911-529-713-000", "874042259378"));

                session.persist(new Compensation(garcia, 90000, 1500, 2000, 1000, 45000, 535.71));
                session.persist(new Compensation(hernandez, 52670, 1500, 1000, 1000, 26335, 313.51));
                session.persist(new Compensation(romualdez, 53500, 1500, 1000, 1000, 26750, 318.45));
                session.persist(new Compensation(santos, 52670, 1500, 1000, 1000, 26335, 313.51));

                session.persist(new UserAccount(1, null, "admin", "admin123", "Admin"));
            }

            seedSampleTimecards(session);
            seedLeaveRequests(session);

            transaction.commit();
        }
    }

    private void seedSampleTimecards(Session session) {
        Employee garcia = session.get(Employee.class, 10001);
        Employee hernandez = session.get(Employee.class, 10005);
        Employee romualdez = session.get(Employee.class, 10015);
        Employee santos = session.get(Employee.class, 10034);

        if (garcia != null) {
            addAttendance(session, garcia, "2023-12-01", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
            addAttendance(session, garcia, "2023-12-04", "Mon", "08:10 AM", "12:00 PM", "01:00 PM", "05:10 PM", 8, 0.25, "Late In");
            addAttendance(session, garcia, "2023-12-05", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "06:00 PM", 8, 1, "Overtime");
        }

        if (hernandez != null) {
            addAttendance(session, hernandez, "2023-12-01", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
            addAttendance(session, hernandez, "2023-12-04", "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:30 PM", 8, 0.5, "Overtime");
            addAttendance(session, hernandez, "2023-12-05", "Tue", null, null, null, null, 0, 0, "Leave");
        }

        if (romualdez != null) {
            seedFullMonthTimecard(session, romualdez);
        }

        if (santos != null) {
            addAttendance(session, santos, "2023-12-01", "Fri", "08:15 AM", "12:00 PM", "01:00 PM", "05:00 PM", 7.75, 0, "Late In");
            addAttendance(session, santos, "2023-12-04", "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
            addAttendance(session, santos, "2023-12-05", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        }
    }

    private void seedFullMonthTimecard(Session session, Employee employee) {
        addAttendance(session, employee, "2023-12-01", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-02", "Sat", null, null, null, null, 0, 0, "Weekend");
        addAttendance(session, employee, "2023-12-03", "Sun", null, null, null, null, 0, 0, "Weekend");
        addAttendance(session, employee, "2023-12-04", "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-05", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-06", "Wed", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-07", "Thu", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-08", "Fri", null, null, null, null, 0, 0, "Holiday (Feast of Immaculate Conception)");
        addAttendance(session, employee, "2023-12-11", "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-12", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-13", "Wed", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-14", "Thu", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-15", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-18", "Mon", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-19", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-20", "Wed", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-21", "Thu", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-22", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-25", "Mon", null, null, null, null, 0, 0, "Holiday (Christmas)");
        addAttendance(session, employee, "2023-12-26", "Tue", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-27", "Wed", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-28", "Thu", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-29", "Fri", "08:00 AM", "12:00 PM", "01:00 PM", "05:00 PM", 8, 0, "Present");
        addAttendance(session, employee, "2023-12-30", "Sat", null, null, null, null, 0, 0, "Holiday (Rizal Day)");
        addAttendance(session, employee, "2023-12-31", "Sun", null, null, null, null, 0, 0, "Weekend");
    }

    private void addAttendance(Session session, Employee employee, String workDate, String dayName, String timeIn, String breakOut, String breakIn, String timeOut, double hoursWorked, double overtimeHours, String remarks) {
        Long count = session.createQuery("""
                        select count(a)
                        from AttendanceRecord a
                        where a.employee.employeeId = :employeeId
                        and a.workDate = :workDate
                        """, Long.class)
                .setParameter("employeeId", employee.getEmployeeId())
                .setParameter("workDate", workDate)
                .getSingleResult();

        if (count == 0) {
            session.persist(new AttendanceRecord(employee, workDate, dayName, timeIn, breakOut, breakIn, timeOut, hoursWorked, overtimeHours, remarks));
        }
    }

    private void seedLeaveRequests(Session session) {
        Employee hernandez = session.get(Employee.class, 10005);
        Employee santos = session.get(Employee.class, 10034);

        if (hernandez != null) {
            addLeaveRequest(session, hernandez, "2026-01-20", "2026-01-21", "Vacation Leave", "Family commitment", "Pending");
        }

        if (santos != null) {
            addLeaveRequest(session, santos, "2026-01-24", "2026-01-24", "Sick Leave", "Medical appointment", "Approved");
        }
    }

    private void addLeaveRequest(Session session, Employee employee, String startDate, String endDate, String leaveType, String reason, String status) {
        Long count = session.createQuery("""
                        select count(lr)
                        from LeaveRequest lr
                        where lr.employee.employeeId = :employeeId
                        and lr.startDate = :startDate
                        and lr.endDate = :endDate
                        """, Long.class)
                .setParameter("employeeId", employee.getEmployeeId())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();

        if (count == 0) {
            session.persist(new LeaveRequest(employee, startDate, endDate, leaveType, reason, status));
        }
    }
}
