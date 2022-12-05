package com.spring.rest.api.util;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.exception.EntityNotValidException;
import org.springframework.validation.BindingResult;

public class CarUtil {

    private static CarUtil instance;

    private CarUtil() {
    }

    public synchronized static CarUtil getInstance() {
        if (instance == null) {
            instance = new CarUtil();
        }
        return instance;
    }


    public void copyNotNullFieldsFromCarDTOToCar(CarDTO from, Car to) {
        if (from.getModel() != null && !from.getModel().isEmpty()) {
            to.setModel(from.getModel());
        }
        if (from.getProducer() != null && !from.getProducer().isEmpty()) {
            to.setProducer(from.getProducer());
        }
        if (from.getPricePerDay() != null) {
            to.setPricePerDay(from.getPricePerDay());
        }
        if (from.getDamageStatus() != null && !from.getDamageStatus().isEmpty()) {
            to.setDamageStatus(from.getDamageStatus());
        }
        if (from.getReleaseDate() != null) {
            to.setReleaseDate(from.getReleaseDate());
        }
        if (from.getImageLink() != null && !from.getImageLink().isEmpty()) {
            to.setImageLink(from.getImageLink());
        }
    }

}
