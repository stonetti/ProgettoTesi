package com.certimeter.progetto.errorHandling;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@Data

public class CustomError {
    
    private String title;
    private List<String> details;
    private URI type;
    private HttpStatus status;
    private String instance;
    private Object objDetails;

}
