package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.EmploymentStatus;
import org.hibernate.Session;

import java.util.List;

public class HibernateEmploymentStatusRepository implements EmploymentStatusRepository {
    @Override
    public List<EmploymentStatus> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select s
                            from EmploymentStatus s
                            order by s.statusId
                            """, EmploymentStatus.class)
                    .getResultList();
        }
    }
}
