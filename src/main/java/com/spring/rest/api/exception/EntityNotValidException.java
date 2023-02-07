package com.spring.rest.api.exception;

public class EntityNotValidException extends RuntimeException{
    public EntityNotValidException(String message) {
        super(message);
    }
}
