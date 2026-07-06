package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.LeaveRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateLeaveRequestRepository implements LeaveRequestRepository {
    @Override
    public List<LeaveRequest> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select lr
                            from LeaveRequest lr
                            join fetch lr.employee e
                            join fetch e.position p
                            join fetch p.department
                            order by lr.startDate desc, lr.leaveRequestId desc
                            """, LeaveRequest.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<LeaveRequest> findById(int leaveRequestId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LeaveRequest request = session.createQuery("""
                            select lr
                            from LeaveRequest lr
                            join fetch lr.employee e
                            join fetch e.position p
                            join fetch p.department
                            where lr.leaveRequestId = :leaveRequestId
                            """, LeaveRequest.class)
                    .setParameter("leaveRequestId", leaveRequestId)
                    .uniqueResult();

            return Optional.ofNullable(request);
        }
    }

    @Override
    public LeaveRequest save(LeaveRequest request) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            LeaveRequest saved = session.merge(request);
            transaction.commit();
            return saved;
        }
    }
}
