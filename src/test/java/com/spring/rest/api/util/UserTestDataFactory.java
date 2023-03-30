package com.spring.rest.api.util;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.Role;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.entity.mapper.PassportMapper;
import com.spring.rest.api.entity.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestDataFactory {

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private static final PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);

    public static User buildUserWithPassport() {
        return User.builder()
                .username("testtest" + UUID.randomUUID())
                .password("testtest")
                .email("test@test.com")
                .roles(Set.of(Role.USER, Role.ADMIN))
                .active(true)
                .passport(PassportTestDataFactory.buildPassport()).build();
    }

    public static User buildUserWithOutPassport() {
        return User.builder()
                .username("testtest" + UUID.randomUUID())
                .password("testtest")
                .email("test@test.com")
                .roles(Set.of(Role.USER, Role.ADMIN))
                .active(true).build();
    }

    public static UserResponseDTO buildUserResponseDTO(User user) {
        return userMapper.userToUserResponseDTO(user);
    }

    public static PassportResponseDTO buildPassportResponseDTOFromUser(User user) {
        PassportResponseDTO passportResponseDTO = null;
        try {
            Passport passport = user.getPassport();
            passportResponseDTO = passportMapper.passportToPassportResponseDTO(passport);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return passportResponseDTO;
    }

    public static CreateUserRequestDTO buildCreateUserRequestDTO() {
        return CreateUserRequestDTO.builder()
                .username("testtest" + UUID.randomUUID())
                .password("testtest")
                .email("test@test.com")
                .build();
    }

    public static User buildNewUserFromCreateUserRequestDTO(CreateUserRequestDTO createUserRequestDTO) {
        User user = userMapper.createUserRequestDTOToUser(createUserRequestDTO);
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        return user;
    }

    public static User buildBlockedUser() {
        return User.builder()
                .username("testtest" + UUID.randomUUID())
                .password("testtest")
                .email("test@test.com")
                .roles(Set.of(Role.USER, Role.ADMIN))
                .active(false).build();
    }


    public static UserResponseDTO buildActicatedUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(user);
        userResponseDTO.setActive(true);
        return userResponseDTO;
    }

    public static UserResponseDTO buildBlockedUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(user);
        userResponseDTO.setActive(false);
        return userResponseDTO;
    }
}
