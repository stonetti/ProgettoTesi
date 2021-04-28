package com.example.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import com.example.UserDemo.UserLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenFactory {
	
	private String secretString = "chiave-segreta-per-demo-user-0109";
	private SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
	
	
	
	public String getAccessToken(UserLogin login) {
		
		String token = Jwts.builder()
		    .setSubject(login.getFullName())
		    .setAudience("web")
            .claim("username", login.getUsername())
            .claim("TokenType", "Access")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60000))//1 min
            .signWith(key).compact();
		
		return token;
	}
	
	
	public String getRefreshToken(UserLogin login) {
		
		String token = Jwts.builder()
		    .setSubject(login.getFullName())
		    .setAudience("web")
            .claim("username", login.getUsername())
            .claim("TokenType", "Refresh")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 600000))//10 min
            .signWith(key).compact();
		
		return token;
		}

}
