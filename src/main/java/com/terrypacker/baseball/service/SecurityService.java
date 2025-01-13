package com.terrypacker.baseball.service;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private final AuthenticationContext authenticationContext;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(AuthenticationContext authenticationContext, PasswordEncoder passwordEncoder) {
        this.authenticationContext = authenticationContext;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
    }

    public void logout() {
        authenticationContext.logout();
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
