package com.spring.rest.api.exception;

/**
 * Exception for BadRequest actions.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
