package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface presents the basic contract for converting User to UserDTO and vice versa.
 */
@Mapper
public interface UserMapper {

    @Mapping(target = "passportId", source = "passport.id")
    UserResponseDTO userToUserResponseDTO(User user);

    User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);
}
