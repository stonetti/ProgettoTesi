package com.certimeter.progetto.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import com.certimeter.progetto.model.AccountDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenFactory {

	private String secretString = "chiave-segreta-per-demo-user-0109";
	private SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

	public String getAccessToken(AccountDetails login) {

		String token = Jwts.builder().setSubject(login.getUsername()).setAudience("web").claim("TokenType", "Access").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 600000))// 1 min
				.signWith(key).compact();

		return token;
	}

	public String getRefreshToken(AccountDetails login) {

		String token = Jwts.builder().setSubject(login.getUsername()).setAudience("web").claim("username", login.getUsername()).claim("TokenType", "Refresh").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 600000))// 10 min
				.signWith(key).compact();

		return token;
	}

}
