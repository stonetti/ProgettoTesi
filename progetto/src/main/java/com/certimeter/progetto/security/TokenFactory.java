package com.certimeter.progetto.security;

import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenFactory {
    @Autowired
    JwtService jwt;
    private String secretString = "chiave-segreta-per-signature-jwt-progetto-0109";
    private SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

    public String getAccessToken(User user, Role role) {

        String iss = jwt.createIssuerFromUser(user);
        String token = Jwts.builder().setIssuer(iss).setAudience("web").claim("Scope", Role.PM).claim("TokenType", "Access").setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 900000))// 15 min
                .signWith(key).compact();

        return token;
    }

    public String getRefreshToken(User user, Role role) {
        String iss = jwt.createIssuerFromUser(user);
        String token = Jwts.builder().setIssuer(iss).setAudience("web").claim("Scope", Role.PM).claim("TokenType", "Refresh").setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))// 24 ore
                .signWith(key).compact();

        return token;
    }

}
