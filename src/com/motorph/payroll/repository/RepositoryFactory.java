package com.motorph.payroll.repository;

import com.motorph.payroll.database.DataSeeder;

public class RepositoryFactory {
    public RepositoryFactory() {
        new DataSeeder().seed();
    }

    public EmployeeRepository createEmployeeRepository() {
        return new HibernateEmployeeRepository();
    }

    public AttendanceRepository createAttendanceRepository() {
        return new HibernateAttendanceRepository();
    }

    public UserRepository createUserRepository() {
        return new HibernateUserRepository();
    }

    public LeaveRequestRepository createLeaveRequestRepository() {
        return new HibernateLeaveRequestRepository();
    }
}
