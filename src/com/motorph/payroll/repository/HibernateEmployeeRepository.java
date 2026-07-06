package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.Employee;
import org.hibernate.Session;

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
}
