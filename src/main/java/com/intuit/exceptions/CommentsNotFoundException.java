package com.intuit.exceptions;

public class CommentsNotFoundException extends RuntimeException {

    public CommentsNotFoundException(String message) {
        super(message);
    }

}
