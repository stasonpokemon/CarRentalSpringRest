package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponseDTO userToUserResponseDTO(User user);

    User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);
}
