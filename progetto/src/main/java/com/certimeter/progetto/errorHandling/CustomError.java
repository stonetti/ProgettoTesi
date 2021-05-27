package com.certimeter.progetto.errorHandling;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@Data

public class CustomError {

    /*
     *
     * Problem details standard format
     *
     * -The Content-Type of the problem details should be application/problem+json. This will tell client how to properly parse the error response object The -type is a URI that identifies the problem type. User can go to the URI to learn more about the problem -The title is used to give a short,
     * human-readable summary of the problem type -The status is for the HTTP status code returned by the server - The instance is a URI that identifies the specific occurrence of the problem
     *
     */

    //TODO: mettere @data!
    private String title;
    private List<String> details;
    private URI type;
    private HttpStatus status;
    private String instance;
    private Object objDetails;

}
