package com.example.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Security.TokenFactory;
import com.example.UserDemo.MapperDemo;
import com.example.UserDemo.UserLogin;


@RestController


public class UserController {
	
	@Autowired
	MapperDemo mapperdemo;
	private TokenFactory tokenFactory = new TokenFactory();


	@PostMapping ("/login")
	public Map<String, Object> login(@RequestBody UserLogin login) throws ServletException{		
		
		if(login.getUsername() == null|| !mapperdemo.usernameExist(login.getUsername()))
			throw new ServletException("Invalid login");
		String accessToken = tokenFactory.getAccessToken(login);	
		String refreshToken = tokenFactory.getRefreshToken(login);
		Map<String, Object> tokenMap = new HashMap<>();
		tokenMap.put("Access Token", accessToken);
		tokenMap.put("Refresh token", refreshToken);
		return tokenMap;
	}	
	
	@PostMapping ("/auth/refreshToken")
	public String refresh(@RequestBody UserLogin login) throws ServletException{	
		
		String accessToken = tokenFactory.
				getAccessToken(login);	
		return accessToken;
	}	
	
	

}
