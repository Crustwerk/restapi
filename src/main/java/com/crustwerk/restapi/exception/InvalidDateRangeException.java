package com.crustwerk.restapi.exception;

public class InvalidDateRangeException extends Throwable {

    public InvalidDateRangeException() {
        super("Invalid date range");
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }
}
