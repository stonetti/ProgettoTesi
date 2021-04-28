package com.example.Security;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import com.example.UserDemo.CustomError;
import com.example.UserDemo.MapperDemo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class JwtFilter extends GenericFilterBean {

	@Autowired
	MapperDemo mapperDemo;
	private String secretString = "chiave-segreta-per-demo-user-0109";
	private SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
	ObjectMapper mapper = new ObjectMapper();	  

	
    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Header con autorizzazione mancante o invalida.");
            //TODO: gestione errore token mancante
        }

        final String token = authHeader.substring(7);
 
	   try {
		Jws<Claims> jws = Jwts.parserBuilder() 
			    .setSigningKey(key)         
			    .build()                    
			    .parseClaimsJws(token);
		   
		    chain.doFilter(req, res);
		    
		} catch (ExpiredJwtException e) {
			String msg = null;
			try {
				msg = setMapper(req,e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			res.setContentType("application/json");
		    HttpServletResponse res2 =  (HttpServletResponse)res;
		    res2.setStatus(401);
		    IOUtils.write(msg, res2.getOutputStream(), Charset.defaultCharset());
	    }
	   
    }
    
    public String setMapper(ServletRequest req, Exception ex) throws Exception {
    	CustomError errormsg = new CustomError();
		errormsg.setTitle("Token expired.");
		errormsg.setInstance(((HttpServletRequest)req).getRequestURI().toString());
        errormsg.setStatus(HttpStatus.UNAUTHORIZED);
        errormsg.setObjDetails(ex.getMessage());	
        return  mapper.writeValueAsString(errormsg);
    }
    

}

