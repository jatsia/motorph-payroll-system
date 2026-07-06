package com.motorph.payroll;

import com.motorph.payroll.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {
    @Test
    void loginUsesRepositoryCredentials() {
        AuthService authService = new AuthService((username, password) ->
                "admin".equals(username) && "admin123".equals(password));

        assertTrue(authService.login("admin", "admin123"));
        assertFalse(authService.login("admin", "wrong"));
        assertFalse(authService.login("", "admin123"));
        assertFalse(authService.login("admin", ""));
        assertFalse(authService.login(null, "admin123"));
    }
}
