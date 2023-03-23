package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.Role;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.entity.dto.UserDTO;
import com.spring.rest.api.entity.mapper.PassportMapper;
import com.spring.rest.api.entity.mapper.UserMapper;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.PassportRepository;
import com.spring.rest.api.repo.UserRepository;
import com.spring.rest.api.service.MailSenderService;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.PassportUtil;
import com.spring.rest.api.util.tread.MailSenderThread;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${server.port}")
    private String SERVER_PORT;
    private final UserRepository userRepository;

    private final PassportRepository passportRepository;

    private final MailSenderService mailSenderService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findUser(Long userId) {

        log.info("Finding user by id: {}", userId);

        UserDTO userDTO = userMapper.userToUserDTO(findUserByIdOrThrowException(userId));
        ResponseEntity<?> response = new ResponseEntity<>(userDTO, HttpStatus.OK);

        log.info("Find user: {} by id: {}", userDTO, userId);

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("Finding all users");

        List<UserDTO> usersDTO = userRepository.findAll(pageable)
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());

        if (usersDTO.isEmpty()) {
            return new ResponseEntity<>("There is no users", HttpStatus.NO_CONTENT);
        }

        ResponseEntity<?> response = new ResponseEntity<Page<UserDTO>>(new PageImpl<>(usersDTO), HttpStatus.OK);

        log.info("Find all users: {}", usersDTO);

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findPassportByUserId(Long userId) {

        log.info("Finding passport by userId: {}", userId);

        User user = findUserByIdOrThrowException(userId);
        Passport passport = findPassportByUserOrThrowException(user);
        ResponseEntity<?> response = new ResponseEntity<>(passportMapper.passportToPassportDTO(passport), HttpStatus.OK);

        log.info("Find passport: {} by userId: {}", passport, userId);

        return response;
    }


    @Override
    public ResponseEntity<?> createPassportForUser(Long userId, PassportDTO passportDTO) {

        log.info("Creating new passport: {} for user with id: {}", passportDTO, userId);

        User user = findUserByIdOrThrowException(userId);

        if (user.getPassport() != null) {
            return new ResponseEntity<>(String.format("User with id = %s already has passport", userId), HttpStatus.OK);
        }

        Passport passport = passportMapper.passportDTOtoPassport(passportDTO);
        passport.setUser(user);
        passportRepository.save(passport);
        ResponseEntity<?> response = new ResponseEntity<>(passportDTO, HttpStatus.OK);

        log.info("Creat new passport: {} for user with id: {}", passport, userId);

        return response;
    }

    @Override
    public ResponseEntity<?> updateUsersPassport(Long userId, PassportDTO passportDTO) {

        log.info("Updating user's passport: {} by userId: {}", passportDTO, userId);

        Passport passport = findPassportByUserOrThrowException(findUserByIdOrThrowException(userId));
        PassportUtil.getInstance().copyNotNullFieldsFromPassportDTOToPassport(passportDTO, passport);
        ResponseEntity<?> response = new ResponseEntity<>(passportMapper.passportToPassportDTO(passport), HttpStatus.OK);

        log.info("Update user's passport: {} by userId: {}", passport, userId);

        return response;
    }

    @Override
    public ResponseEntity<?> saveRegisteredUser(UserDTO userDTO) {

        log.info("Saving registered user: {}", userDTO);

        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {

            log.warn("There is user with username: {}", userDTO.getUsername());

            return new ResponseEntity<>("There is user with the same username", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {

            log.warn("There is user with email: {}", userDTO.getEmail());

            return new ResponseEntity<>("There is user with the same email", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> response;

        User user = userMapper.userDTOtoUser(userDTO);

        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        response = new ResponseEntity<>(userMapper.userToUserDTO(userRepository.save(user)), HttpStatus.OK);

        log.info("Save registered user: {}", user);

        // Send activation code to user email
        String message = String.format("Hello, %s! \n Welcome to car rental website. Please, visit next link for activate your profile: http://localhost:%s/registration/activate/%s!",
                user.getUsername(),
                SERVER_PORT,
                user.getActivationCode()
        );
        new MailSenderThread(mailSenderService, user.getEmail(), "Activation code", message).start();

        return response;
    }

    @Override
    public ResponseEntity<?> activateUser(String activateCode) {

        log.info("Activating user");

        User user = userRepository.findUserByActivationCode(activateCode)
                .orElseThrow(() -> new NotFoundException("Activation code is note found"));
        user.setActivationCode(null);
        user.setActive(true);

        ResponseEntity<?> response = new ResponseEntity<>("User successfully activated", HttpStatus.OK);

        log.info("Activate user: {}", user);

        return response;
    }


    @Override
    public ResponseEntity<?> blockUser(Long id) {

        log.info("Blocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (!user.isActive()) {
            return new ResponseEntity<>("User is already blocked", HttpStatus.OK);
        }

        user.setActive(false);
        ResponseEntity<?> response = new ResponseEntity<>("User is blocked", HttpStatus.OK);

        log.info("Block user: {}", user);

        return response;
    }

    @Override
    public ResponseEntity<?> unlockUser(Long id) {

        log.info("Unlocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (user.isActive()) {
            return new ResponseEntity<>("User is already unlocked", HttpStatus.OK);
        }

        user.setActive(true);
        ResponseEntity<?> response = new ResponseEntity<>("User is unlocked", HttpStatus.OK);

        log.info("Unlock user: {}", user);

        return response;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public User findUserByIdOrThrowException(Long userId) {

        log.info("Finding user by id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                User.class, userId));

        log.info("Find user: {}", user);

        return user;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Passport findPassportByUserOrThrowException(User user) {

        log.info("Finding passport by user: {}", user);

        Passport passport = Optional.ofNullable(user.getPassport())
                .orElseThrow(() -> new NotFoundException(Passport.class, user.getId()));

        log.info("Finding passport: {} by user: {}", passport, user);

        return passport;
    }
}
