package com.clientDemo;


import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;


public class CustomError {
	
	
	private String title;
	private List<String> details;
	private URI type;
	private HttpStatus status;
	private String code;
	private String instance;
	private Object objDetails;
	
	
	public CustomError() {
	}

	public URI getType() {
		return type;
	}

	public void setType(URI type) {
		this.type = type;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getObjDetails() {
		return objDetails;
	}

	public void setObjDetails(Object objDetails) {
		this.objDetails = objDetails;
	}

	
	
}

