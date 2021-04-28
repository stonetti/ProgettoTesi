package com.clientDemo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootApplication
public class ClientDemoApplication {

	public static void main(String[] args) throws IOException, IOException {
		SpringApplication.run(ClientDemoApplication.class, args);
		Request.makePostRequest();
	}

}
