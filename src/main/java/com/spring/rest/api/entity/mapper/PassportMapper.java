package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.dto.PassportDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PassportMapper {

    Passport passportDTOtoPassport(PassportDTO passportDTO);

    PassportDTO passportToPassportDTO(Passport passport);
}
