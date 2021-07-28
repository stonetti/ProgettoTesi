package com.certimeter.progetto.service;

import com.certimeter.progetto.configuration.SecurityProperties;
import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.repository.UserMapperRepository;
import com.certimeter.progetto.security.JwtInstance;
import com.certimeter.progetto.security.TokenFactory;
import com.certimeter.progetto.utilities.Converter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
    UserMapperRepository userMapperRepository;
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    TokenFactory tokenFactory;
    @Autowired
    JwtInstance jwtInstance;


    private final String key = "chiave-segreta-per-signature-jwt-progetto-0109";
    private final SecretKey jwtSigningKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    public User getUserFromToken() {
        return Converter.convert(userMapperRepository.getUser(getClaim("id", String.class)), User.class);
    }

    public void checkTokenAndInitSession(String token) {
        jwtInstance.setToken(Jwts.parserBuilder().setSigningKey(jwtSigningKey).build().parseClaimsJws(token));
    }

    public Map<String, Object> setTokenMap(User user, Role role) {
        String accessToken = tokenFactory.getAccessToken(user, role);
        String refreshToken = tokenFactory.getRefreshToken(user, role);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        return tokenMap;
    }

    public boolean passwordCheck(User user, AccountDetails acc) {
        return (BCrypt.checkpw(acc.getPassword(), user.getAccDetails().getPassword()));
    }

    public <T> T getClaim(String key, Class<T> cls) {
        return jwtInstance.getToken().getBody().get(key, cls);
    }

    public Role getRole() {
        return Role.valueOf(getClaim("role", String.class));
    }
}
