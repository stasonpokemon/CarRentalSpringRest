package com.spring.rest.api.exception.handler;

import com.spring.rest.api.exception.CarNotFoundException;
import com.spring.rest.api.exception.CarNotValidException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.exception.dto.ErrorTypeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CarExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> carNotFoundExceptionHandler(CarNotFoundException carNotFoundException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(carNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> carNotValidExceptionHandler(CarNotValidException carNotValidException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(carNotValidException.getMessage())
                .status(HttpStatus.NOT_FOUND).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorTypeResponseDTO> sortParametersNotValidExceptionHandler(SortParametersNotValidException sortParametersNotValidException) {
        return new ResponseEntity<ErrorTypeResponseDTO>(ErrorTypeResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(sortParametersNotValidException.getMessage())
                .status(HttpStatus.NOT_FOUND).build(), HttpStatus.BAD_REQUEST);
    }
}
