package com.certimeter.progetto.errorHandling;

public class AuthorizationFailureException extends Exception {

    public AuthorizationFailureException() {
        super("Access to the requested resource is not authorized for current user.");
    }
}

