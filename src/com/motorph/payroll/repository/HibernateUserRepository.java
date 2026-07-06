package com.motorph.payroll.repository;

import com.motorph.payroll.database.HibernateUtil;
import org.hibernate.Session;

public class HibernateUserRepository implements UserRepository {
    @Override
    public boolean existsByCredentials(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("""
                            select count(u)
                            from UserAccount u
                            where u.username = :username
                            and u.password = :password
                            """, Long.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            return count == 1;
        }
    }
}

