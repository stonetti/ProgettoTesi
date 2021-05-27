package com.certimeter.progetto.security;

import com.certimeter.progetto.configuration.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityProperties prop;

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(prop.getLoginUrl(), prop.getRefreshTokenUrl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().antMatcher("/**").addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
