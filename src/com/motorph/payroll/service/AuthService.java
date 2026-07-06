package com.motorph.payroll.service;

import com.motorph.payroll.repository.RepositoryFactory;
import com.motorph.payroll.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService() {
        this(new RepositoryFactory().createUserRepository());
    }

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        return userRepository.existsByCredentials(username, password);
    }
}
