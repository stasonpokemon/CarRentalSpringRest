package com.spring.rest.api.util;

import com.spring.rest.api.exception.EntityNotValidException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public <T> List<Sort.Order> getOrdersFromRequest(String[] sort, Class<T> tClass) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            // will sort more than 2 columns
            for (String sortOrder : sort) {
                // sortOrder="column, direction"
                String[] _sort = sortOrder.split(",");
                isFieldsCorrect(_sort[0], tClass);
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[column, direction]
            isFieldsCorrect(sort[0], tClass);
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    private <T> boolean isFieldsCorrect(String field, Class<T> tClass) {
        Set<String> classFields = Arrays.stream(tClass.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toSet());
        classFields.addAll(Arrays.stream(tClass.getSuperclass().getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toSet()));
        if (!classFields.contains(field)) {
            throw new SortParametersNotValidException(new StringBuilder("Wrong sort request - ")
                    .append(field).toString());
        }
        return true;
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
