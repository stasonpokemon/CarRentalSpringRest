package com.spring.rest.api.util;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;

/**
 * PassportUtil class.
 */
public class PassportUtil {

    private static PassportUtil instance;

    private PassportUtil() {
    }

    public static synchronized PassportUtil getInstance() {
        if (instance == null) {
            instance = new PassportUtil();
        }
        return instance;
    }


    public void copyNotNullFieldsFromPassportDTOToPassport(PassportRequestDTO from, Passport to) {
        if (from.getAddress() != null && !from.getAddress().isEmpty()) {
            to.setAddress(from.getAddress());
        }
        if (from.getBirthday() != null) {
            to.setBirthday(from.getBirthday());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            to.setName(from.getName());
        }
        if (from.getPatronymic() != null && !from.getPatronymic().isEmpty()) {
            to.setPatronymic(from.getPatronymic());
        }
        if (from.getSurname() != null && !from.getSurname().isEmpty()) {
            to.setSurname(from.getSurname());
        }
    }
}
