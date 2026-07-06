package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.AttendanceRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateAttendanceRepository implements AttendanceRepository {
    @Override
    public List<AttendanceRecord> findByEmployeeId(int employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select a
                            from AttendanceRecord a
                            where a.employee.employeeId = :employeeId
                            order by a.workDate
                            """, AttendanceRecord.class)
                    .setParameter("employeeId", employeeId)
                    .getResultList();
        }
    }

    @Override
    public Optional<AttendanceRecord> findByEmployeeIdAndDate(int employeeId, String workDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            AttendanceRecord record = session.createQuery("""
                            select a
                            from AttendanceRecord a
                            join fetch a.employee e
                            join fetch e.position p
                            join fetch p.department
                            join fetch e.compensation
                            where e.employeeId = :employeeId
                            and a.workDate = :workDate
                            """, AttendanceRecord.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("workDate", workDate)
                    .uniqueResult();

            return Optional.ofNullable(record);
        }
    }

    @Override
    public AttendanceRecord save(AttendanceRecord record) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            AttendanceRecord saved = session.merge(record);
            transaction.commit();
            return saved;
        }
    }
}
