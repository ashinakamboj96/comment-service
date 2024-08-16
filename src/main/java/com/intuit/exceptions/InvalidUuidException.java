package com.intuit.exceptions;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(String message) {
        super(message);
    }
}
