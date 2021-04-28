package com.example.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
	
	    String loginUrl;

		public String getLoginUrl() {
			return loginUrl;
		}

		public void setLoginUrl(String logi) {
			this.loginUrl = logi;
		} 

	 
}
	