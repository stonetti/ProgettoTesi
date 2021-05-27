package com.certimeter.progetto.security;

import com.certimeter.progetto.errorHandling.CustomError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

    ObjectMapper mapper = new ObjectMapper();
    private final String key = "chiave-segreta-per-signature-jwt-progetto-0109";
    private final SecretKey jwtSigningKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Invalid or missing authorization in request header.");
        }

        final String token = authHeader.substring(7);

        try {

            Jwts.parserBuilder().setSigningKey(jwtSigningKey).build().parseClaimsJws(token);
            chain.doFilter(req, res);

        } catch (ExpiredJwtException e) {
            String msg = null;
            try {
                msg = handleExpiredJwtEx(req, e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            res.setContentType("application/json");
            HttpServletResponse res2 = (HttpServletResponse) res;
            res2.setStatus(401);
            IOUtils.write(msg, res2.getOutputStream(), Charset.defaultCharset());
        }

    }

    public String handleExpiredJwtEx(ServletRequest req, Exception ex) throws Exception {
        CustomError errormsg = new CustomError();
        errormsg.setTitle("Token expired.");
        List<String> detailsList = new ArrayList<>();
        detailsList.add("Your token has expired. Request a new access token with your refresh token or login again!");
        errormsg.setDetails(detailsList);
        errormsg.setInstance(((HttpServletRequest) req).getRequestURI());
        errormsg.setStatus(HttpStatus.UNAUTHORIZED);
        errormsg.setObjDetails(ex.getMessage());
        return mapper.writeValueAsString(errormsg);
    }

}