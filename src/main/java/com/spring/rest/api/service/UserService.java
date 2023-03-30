package com.spring.rest.api.service;

import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> blockUser(UUID userId);

    ResponseEntity<?> unlockUser(UUID userId);

    ResponseEntity<?> findPassportByUserId(UUID userId);

    ResponseEntity<?> createPassportForUser(UUID userId,
                                            PassportDTO passportDTO);

    ResponseEntity<?> updateUsersPassport(UUID userId,
                                          PassportDTO passportDTO);

    ResponseEntity<?> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO);

    ResponseEntity<?> activateUser(String activateCode);

    ResponseEntity<?> findUser(UUID userId);

    User findUserByIdOrThrowException(UUID userId);


}
