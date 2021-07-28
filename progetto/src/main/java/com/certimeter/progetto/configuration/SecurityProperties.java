package com.certimeter.progetto.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    String[] whitelistPaths;
    String jwtSigningKey;
    String resourceIdKey;

}