package com.clientDemo;

import java.io.IOException;
import java.lang.System;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {


	public static void makePostRequest() throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ClientUser user = new ClientUser();
		String json = "";
		String jsonBody = "";

		try {
			jsonBody = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}


		RestTemplate restTemplate = new RestTemplate();

		
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON); 
		  HttpEntity<String> request = new HttpEntity<String>(jsonBody.toString(),headers);
		 
		 
		  try {
			  json = restTemplate.postForObject("http://localhost:8080/user", request, String.class);
			  System.out.println(json);
		  
		  }catch (HttpClientErrorException e) {
			    CustomError customError = mapper.readValue(e.getResponseBodyAsString(), CustomError.class);
			    System.out.println(customError.getDetails());
			    			    
			}
		  
	}
	



}
