package com.certimeter.progetto.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class JwtInstance {
    private Jws<Claims> token;
}
