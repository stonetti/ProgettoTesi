package com.certimeter.progetto.security;

import com.certimeter.progetto.configuration.SecurityProperties;
import com.certimeter.progetto.errorHandling.CustomError;
import com.certimeter.progetto.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    SecurityProperties prop;

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Invalid or missing authorization in request header.");
        }

        final String token = authHeader.substring(7);

        try {
            jwtService.checkTokenAndInitSession(token);
            chain.doFilter(request, res);
        } catch (ExpiredJwtException e) {
            String msg = null;
            try {
                msg = handleExpiredJwtEx(request, e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            res.setContentType("application/json");
            HttpServletResponse res2 = res;
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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String path : prop.getWhitelistPaths()) {
            if (pathMatcher.match(path, request.getRequestURI()))
                return true;
        }
        return false;
    }
}