package com.spring.rest.api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public <T, S> NotFoundException(Class<T> classA, S id) {
        super(String.format("Not found %s with id: %s", classA.getSimpleName(), id));
    }
}
