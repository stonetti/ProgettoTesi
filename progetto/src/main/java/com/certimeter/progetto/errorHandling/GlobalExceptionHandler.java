package com.certimeter.progetto.errorHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> inputErrors = new HashMap<>();
        List<String> userReadableErrors = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonErrors = null;

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            inputErrors.put(error.getField(), error.getCode());
            userReadableErrors.add(error.getField() + " " + error.getDefaultMessage());
        }

        try {
            jsonErrors = mapper.writeValueAsString(inputErrors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        CustomError errormsg = new CustomError();
        errormsg.setDetails(userReadableErrors);
        errormsg.setObjDetails(jsonErrors);
        errormsg.setTitle(ex.getClass().getSimpleName());
        errormsg.setStatus(HttpStatus.BAD_REQUEST);
        errormsg.setInstance(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        return new ResponseEntity<>(errormsg, headers, status);
    }


    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
        CustomError error = new CustomError();
        error.setType(URI.create("blog"));
        error.setTitle(ex.getClass().getSimpleName());
        error.setInstance(((ServletWebRequest) request).getRequest().getRequestURL().toString());
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
