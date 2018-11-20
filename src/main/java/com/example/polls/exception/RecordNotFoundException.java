package com.example.polls.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String exception) {
        super(exception);
    }
}
