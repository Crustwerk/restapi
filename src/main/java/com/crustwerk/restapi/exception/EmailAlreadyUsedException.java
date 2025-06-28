package com.crustwerk.restapi.exception;

/**
 * Viene lanciata quando si tenta di registrare un'email già presente nel sistema.
 */
public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("This email is already registered");
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
