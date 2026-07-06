package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import com.motorph.payroll.model.Position;
import org.hibernate.Session;

import java.util.List;

public class HibernatePositionRepository implements PositionRepository {
    @Override
    public List<Position> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                            select p
                            from Position p
                            join fetch p.department
                            order by p.positionId
                            """, Position.class)
                    .getResultList();
        }
    }
}
