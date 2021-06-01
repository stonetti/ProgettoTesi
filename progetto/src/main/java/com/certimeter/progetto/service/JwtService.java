package com.certimeter.progetto.service;

import com.certimeter.progetto.configuration.SecurityProperties;
import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.repository.UserMapperRepository;
import com.certimeter.progetto.security.JwtResourceIdSecurity;
import com.certimeter.progetto.security.TokenFactory;
import com.certimeter.progetto.utilities.Converter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
    UserMapperRepository repo;
    @Autowired
    SecurityProperties jwt;

    @Autowired
    TokenFactory tokenFactory;
    //private SecretKey key;
    private final String key = "chiave-segreta-per-signature-jwt-progetto-0109";
    private final SecretKey jwtSigningKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    //TODO: chiedere a Fabio perchè se scommento non funziona (key=null)

    //	@PostConstruct public void init() {
    //		key = Keys.hmacShaKeyFor(jwt.getJwtSigningKey().getBytes(StandardCharsets.UTF_8));
    //		System.out.println(jwt);
    //	}

    public User getUserFromToken(String token) {
        Jws<Claims> jws = getTokenClaims(token);
        String userEncodedId = jws.getBody().getIssuer();
        String userDecodedId = JwtResourceIdSecurity.decrypt(userEncodedId, jwt.getResourceIdKey());
        return Converter.convert(repo.getUser(userDecodedId), User.class);
    }

    public Map<String, Object> setTokenMap(User user, Role role) {
        String accessToken = tokenFactory.getAccessToken(user, role);
        String refreshToken = tokenFactory.getRefreshToken(user, role);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("Access Token", accessToken);
        tokenMap.put("Refresh token", refreshToken);
        return tokenMap;
    }

    public boolean passwordCheck(User user, AccountDetails acc) {
        String hashedPwd = user.getAccDetails().getPassword();
        return (BCrypt.checkpw(acc.getPassword(), hashedPwd));

    }

    public Jws<Claims> getTokenClaims(String token) {
        System.out.println(token);
        token = token.replace("\"", "");
        System.out.println(token);

        return Jwts.parserBuilder().setSigningKey(jwtSigningKey).build().parseClaimsJws(token);
    }

    public String createIssuerFromUser(User user) {
        String userId = user.getId();
        return JwtResourceIdSecurity.encrypt(userId, jwt.getResourceIdKey());
    }
}
