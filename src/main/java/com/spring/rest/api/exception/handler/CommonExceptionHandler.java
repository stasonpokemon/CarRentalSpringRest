package com.spring.rest.api.exception.handler;

import com.spring.rest.api.exception.EntityNotFoundException;
import com.spring.rest.api.exception.EntityNotValidException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.exception.dto.ErrorTypeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> sortParametersNotValidExceptionHandler(SortParametersNotValidException sortParametersNotValidException) {
        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(sortParametersNotValidException.getMessage())
                .status(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException) {
        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(entityNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> entityNotValidExceptionHandler(EntityNotValidException entityNotValidException) {
        return new ResponseEntity<>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(entityNotValidException.getMessage())
                .status(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
    }
}
