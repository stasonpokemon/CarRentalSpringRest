package com.spring.rest.api.exception.handler;

import com.spring.rest.api.exception.BadRequestException;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.exception.dto.ErrorTypeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The CommonExceptionHandler class for handling exceptions.
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> notFoundExceptionHandler(
            NotFoundException notFoundException) {

        log.warn(notFoundException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(notFoundException.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> badRequestExceptionHandler(
            BadRequestException badRequestException) {

        log.warn(badRequestException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(badRequestException.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        log.info("Handle MethodArgumentNotValidException: {}", errors);

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(errors).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> propertyReferenceExceptionHandler(
            PropertyReferenceException propertyReferenceException) {

        log.warn(propertyReferenceException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(propertyReferenceException.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

}
