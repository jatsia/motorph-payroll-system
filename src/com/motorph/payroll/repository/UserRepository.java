package com.motorph.payroll.repository;

public interface UserRepository {
    boolean existsByCredentials(String username, String password);
}

