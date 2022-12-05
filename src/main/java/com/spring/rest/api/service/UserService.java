package com.spring.rest.api.service;

import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> findAll(String[] sort);

    ResponseEntity<?> blockUser(Long userId);

    ResponseEntity<?> unlockUser(Long userId);

    ResponseEntity<?> findPassportByUserId(Long userId);

    ResponseEntity<?> createPassportForUser(Long userId, PassportDTO passportDTO);

    ResponseEntity<?> updateUsersPassport(Long userId, PassportDTO passportDTO);

    ResponseEntity<?> saveRegisteredUser(UserDTO userDTO);

    ResponseEntity<?> activateUser(String activateCode);

    ResponseEntity<?> findUser(Long userId);
}
