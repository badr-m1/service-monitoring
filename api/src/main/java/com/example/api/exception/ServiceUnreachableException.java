package com.example.api.exception;

public class ServiceUnreachableException extends RuntimeException {

    public ServiceUnreachableException(String message) {
        super(message);
    }
}