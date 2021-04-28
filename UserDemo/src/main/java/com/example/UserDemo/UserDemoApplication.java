package com.example.UserDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.example.UserDemo")
@SpringBootApplication
@ComponentScan(basePackages = { "com.*"})

public class UserDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserDemoApplication.class, args);
	}

}
