package com.crustwerk.restapi.exception;

public class LegalAgeUserException extends Throwable {

    public LegalAgeUserException() {
        super("User must be of legal age.");
    }

    public LegalAgeUserException(String message) {
        super(message);
    }
}
