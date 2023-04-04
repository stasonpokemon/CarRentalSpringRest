package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "passportId", source = "passport.id")
    UserResponseDTO userToUserResponseDTO(User user);

    User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);
}
