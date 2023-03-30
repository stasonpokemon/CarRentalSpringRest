package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PassportMapper {

    Passport passportDTOtoPassport(PassportRequestDTO passportRequestDTO);


    PassportRequestDTO passportToPassportDTO(Passport passport);

    @Mapping(target = "userId", source = "user.id")
    PassportResponseDTO passportToPassportResponseDTO(Passport passport);
}
