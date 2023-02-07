package com.spring.rest.api.util;

import com.spring.rest.api.exception.EntityNotValidException;
import org.springframework.validation.BindingResult;

public class CommonUtil {

    private static CommonUtil instance;

    private CommonUtil() {
    }

    public synchronized static CommonUtil getInstance() {
        if (instance == null) {
            instance = new CommonUtil();
        }
        return instance;
    }

    public void checkBindingResultOrThrowException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fieldError ->
                    errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append("; "));
            throw new EntityNotValidException(errorMessage.toString());
        }
    }
}
