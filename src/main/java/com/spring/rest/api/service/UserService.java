package com.spring.rest.api.service;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * The UserService interface, which stores the business logic for working with an user.
 */
public interface UserService {

    ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable);

    ResponseEntity<UserResponseDTO> blockUser(UUID userId);

    ResponseEntity<UserResponseDTO> unlockUser(UUID userId);

    ResponseEntity<PassportResponseDTO> findPassportByUserId(UUID userId);

    ResponseEntity<PassportResponseDTO> createPassportForUser(UUID userId,
                                            PassportRequestDTO passportRequestDTO);

    ResponseEntity<PassportResponseDTO> updateUsersPassport(UUID userId,
                                          PassportRequestDTO passportRequestDTO);

    ResponseEntity<UserResponseDTO> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO);

    ResponseEntity<UserResponseDTO> activateUser(String activateCode);

    ResponseEntity<UserResponseDTO> findById(UUID userId);

    User findUserByIdOrThrowException(UUID userId);


}
