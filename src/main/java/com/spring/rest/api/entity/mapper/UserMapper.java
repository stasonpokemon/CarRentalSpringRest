package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userDTOtoUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);
}
