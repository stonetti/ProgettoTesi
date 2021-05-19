package com.certimeter.progetto.errorHandling;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> inputErrors = new HashMap<String, String>();
		List<String> userReadableErrors = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		String jsonErrors = "";
		String sendingErrorClassName = ex.getStackTrace()[0].getClassName().toUpperCase();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			System.out.println(error);
			inputErrors.put(error.getField(), error.getCode());
			userReadableErrors.add(error.getField() + " " + error.getDefaultMessage());
		}

		try {
			jsonErrors = mapper.writeValueAsString(inputErrors);
			System.out.println(jsonErrors);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		CustomError errormsg = new CustomError();
		// errormsg.setType();
		errormsg.setTitle("Data Constraint Violation");
		errormsg.setInstance(((ServletWebRequest) request).getRequest().getRequestURL().toString());
		errormsg.setStatus(HttpStatus.BAD_REQUEST);
		errormsg.setCode("req_01");
		errormsg.setDetails(userReadableErrors);
		errormsg.setObjDetails(jsonErrors);

		return new ResponseEntity<>(errormsg, headers, status);
	}

	@ExceptionHandler(value = { SQLIntegrityConstraintViolationException.class })
	protected ResponseEntity<Object> handleConstraintViolation(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
		CustomError error = new CustomError();
		error.setType(URI.create("https://www.w3schools.com/sql/sql_constraints.asp"));
		error.setTitle(ex.getClass().getSimpleName());
		error.setInstance("");
		error.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	/*
	 * @ExceptionHandler(value = { Exception.class }) protected ResponseEntity<Object> handleExceptions (Exception ex, HttpServletRequest request){ CustomError error = new CustomError(); error.setType(URI.create( "https://docs.oracle.com/javaee/7/api/javax/validation/ConstraintViolation.html" ));
	 * error.setTitle(ex.getClass().getSimpleName()); error.setInstance(((ServletWebRequest) request).getRequest().getRequestURL().toString()); error.setStatus(HttpStatus.BAD_REQUEST);
	 * 
	 * return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); }
	 */

	@ExceptionHandler(value = { ExpiredJwtException.class })
	protected ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest request) {
		CustomError error = new CustomError();
		error.setTitle(ex.getClass().getSimpleName());
		error.setInstance(((ServletWebRequest) request).getRequest().getRequestURL().toString());
		error.setStatus(HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

}
