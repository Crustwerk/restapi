package com.crustwerk.restapi.exception;

/**
 * Viene lanciata quando si tenta di registrare un utente minorenne.
 */
public class UnderageUserException extends RuntimeException {

    public UnderageUserException() {
        super("You have to be at least 18 years old!");
    }

    public UnderageUserException(String message) {
        super(message);
    }
}
