package com.certimeter.progetto.service;

import com.certimeter.progetto.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {


    @Autowired
    JwtService jwt;

    public List<Role> getRolesByJwt(String jwt) {
        return null;
    }

    public boolean isPm(String token) {
        return jwt.getTokenClaims(token).getBody().get("role").equals(Role.PM.getRole());
    }

    public boolean isAdmin(String token) {
        return jwt.getTokenClaims(token).getBody().get("role").equals(Role.ADMIN.getRole());
    }

    public boolean isUser(String token) {
        return jwt.getTokenClaims(token).getBody().get("role").equals(Role.USER.getRole());
    }
}
