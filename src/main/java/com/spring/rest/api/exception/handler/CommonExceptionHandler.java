package com.spring.rest.api.exception.handler;

import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.exception.dto.ErrorTypeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> entityNotFoundExceptionHandler(
            NotFoundException notFoundException) {

        log.warn(notFoundException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(notFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        log.warn(methodArgumentNotValidException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(methodArgumentNotValidException.getMessage())
                .status(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> propertyReferenceExceptionHandler(
            PropertyReferenceException propertyReferenceException) {

        log.warn(propertyReferenceException.getMessage());

        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(propertyReferenceException.getMessage())
                .status(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
    }

}
