package com.spring.rest.api.exception;

public class CarNotValidException extends RuntimeException {
    public CarNotValidException(String message) {
        super(message);
    }
}
