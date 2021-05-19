package com.certimeter.progetto.errorHandling;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CustomError {

	/*
	 * 
	 * Problem details standard format
	 * 
	 * -The Content-Type of the problem details should be application/problem+json. This will tell client how to properly parse the error response object The -type is a URI that identifies the problem type. User can go to the URI to learn more about the problem -The title is used to give a short,
	 * human-readable summary of the problem type -The status is for the HTTP status code returned by the server - The instance is a URI that identifies the specific occurrence of the problem
	 * 
	 */

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
