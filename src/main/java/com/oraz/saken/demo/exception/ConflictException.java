package com.oraz.saken.demo.exception;

public class ConflictException extends RuntimeException{
    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}
