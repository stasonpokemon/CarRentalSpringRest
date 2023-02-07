package com.spring.rest.api.service;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.entity.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> blockUser(Long userId);

    ResponseEntity<?> unlockUser(Long userId);

    ResponseEntity<?> findPassportByUserId(Long userId);

    ResponseEntity<?> createPassportForUser(Long userId, PassportDTO passportDTO);

    ResponseEntity<?> updateUsersPassport(Long userId, PassportDTO passportDTO);

    ResponseEntity<?> saveRegisteredUser(UserDTO userDTO);

    ResponseEntity<?> activateUser(String activateCode);

    ResponseEntity<?> findUser(Long userId);

    User findUserByIdOrThrowException(Long userId);

    Passport findPassportByUserOrThrowException(User user);

}
