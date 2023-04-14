package com.spring.rest.api.util;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.mapper.PassportMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * PassportTestDataFactory factory test class for create passport entities and DTOs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportTestDataFactory {

    private static final PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);

    public static Passport buildPassport() {
        return Passport.builder()
                .name("test" + UUID.randomUUID())
                .surname("test")
                .address("test")
                .patronymic("test")
                .birthday(new Date(2000, Calendar.FEBRUARY, 1, 1, 1, 1))
                .address("test").build();
    }

    public static Passport buildPassportOfUser(User user) {
        return Passport.builder()
                .name("test" + UUID.randomUUID())
                .surname("test")
                .address("test")
                .patronymic("test")
                .birthday(new Date(2000, Calendar.FEBRUARY, 1, 1, 1, 1))
                .address("test")
                .user(user).build();
    }

    public static PassportRequestDTO buildPassportDTOFromPassport(Passport passport) {
        return passportMapper.passportToPassportDTO(passport);
    }

    public static PassportResponseDTO buildPassportResponseDTOFromPassport(Passport passport) {
        return passportMapper.passportToPassportResponseDTO(passport);
    }
}
