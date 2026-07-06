package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateEmployeeRepository implements EmployeeRepository {
    @Override
    public List<Employee> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select distinct e
                            from Employee e
                            join fetch e.position p
                            join fetch p.department
                            join fetch e.compensation
                            order by e.employeeId
                            """, Employee.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Employee> findById(int employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.createQuery("""
                            select distinct e
                            from Employee e
                            join fetch e.position p
                            join fetch p.department
                            join fetch e.compensation
                            where e.employeeId = :employeeId
                            """, Employee.class)
                    .setParameter("employeeId", employeeId)
                    .uniqueResult();

            return Optional.ofNullable(employee);
        }
    }

    @Override
    public Employee save(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee saved = session.merge(employee);
            transaction.commit();
            return saved;
        }
    }

    @Override
    public void deleteById(int employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("delete from AttendanceRecord a where a.employee.employeeId = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
            session.createMutationQuery("delete from LeaveRequest lr where lr.employee.employeeId = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
            session.createMutationQuery("delete from GovernmentIds g where g.employee.employeeId = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
            session.createMutationQuery("delete from Compensation c where c.employee.employeeId = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
            session.createMutationQuery("delete from Employee e where e.employeeId = :employeeId")
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();
            transaction.commit();
        }
    }
}
