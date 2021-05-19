package com.certimeter.progetto.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.queries.UserQueries;
import com.certimeter.progetto.security.TokenFactory;

@RestController

public class LoginController {

	@Autowired
	UserQueries db;
	private TokenFactory tokenFactory = new TokenFactory();

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody AccountDetails login) throws ServletException {

		System.out.println(login.getUsername() + " " + login.getPassword());
		if (login.getUsername() == null || passwordOrUsernameWrong(login))
			throw new ServletException("Invalid username or password");

		String accessToken = tokenFactory.getAccessToken(login);
		String refreshToken = tokenFactory.getRefreshToken(login);
		Map<String, Object> tokenMap = new HashMap<>();
		tokenMap.put("Access Token", accessToken);
		tokenMap.put("Refresh token", refreshToken);
		return tokenMap;
	}

	private boolean passwordOrUsernameWrong(AccountDetails acc) {
		User user = db.findByAccountDetails(acc.getUsername(), acc.getPassword());
		if (user == null)
			return true;
		String pwd = user.getAccDetails().getPassword();
		return pwd != user.getAccDetails().getPassword();
	}

	@PostMapping("/auth/refreshToken")
	public String refresh(@RequestBody AccountDetails login) throws ServletException {
		// TODO: fare il check sul refresh token
		String accessToken = tokenFactory.getAccessToken(login);
		return accessToken;
	}

}
