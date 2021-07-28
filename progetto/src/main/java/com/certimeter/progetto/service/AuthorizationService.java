package com.certimeter.progetto.service;

import com.certimeter.progetto.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    JwtService jwtService;

    public boolean isPm() {
        return jwtService.getRole() == (Role.PM);
    }

    public boolean isAdmin() {
        return jwtService.getRole() == (Role.ADMIN);
    }

    public boolean isUser() {
        return jwtService.getRole() == (Role.USER);
    }
}

