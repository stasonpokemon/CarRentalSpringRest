package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.Role;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.entity.mapper.PassportMapper;
import com.spring.rest.api.entity.mapper.UserMapper;
import com.spring.rest.api.exception.BadRequestException;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.PassportRepository;
import com.spring.rest.api.repo.UserRepository;
import com.spring.rest.api.service.MailSenderService;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.PassportUtil;
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
    public ResponseEntity<UserResponseDTO> findUser(UUID userId) {

        log.info("Finding user by id: {}", userId);

        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(findUserByIdOrThrowException(userId));
        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userResponseDTO, HttpStatus.OK);

        log.info("Find user: {} by id: {}", userResponseDTO, userId);

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable) {

        log.info("Finding all users");

        Page<UserResponseDTO> userResponseDTOPage = new PageImpl<>(userRepository.findAll(pageable)
                .stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList()));


        if (userResponseDTOPage.isEmpty()) {
            throw new NotFoundException(User.class);
        }

        ResponseEntity<Page<UserResponseDTO>> response = new ResponseEntity<>(userResponseDTOPage, HttpStatus.OK);

        log.info("Find all users: {}", userResponseDTOPage.getContent());

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PassportResponseDTO> findPassportByUserId(UUID userId) {

        log.info("Finding passport by userId: {}", userId);

        Passport passport = findUserByIdOrThrowException(userId).getPassport();

        if (passport == null) {
            throw new NotFoundException(String.format("Passport not found for user with id = %s", userId));
        }

        ResponseEntity<PassportResponseDTO> response = new ResponseEntity<>(passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);

        log.info("Find passport: {} by userId: {}", passport, userId);

        return response;
    }


    @Override
    public ResponseEntity<PassportResponseDTO> createPassportForUser(UUID userId,
                                                                     PassportRequestDTO passportRequestDTO) {

        log.info("Creating new passport: {} for user with id: {}", passportRequestDTO, userId);

        User user = findUserByIdOrThrowException(userId);

        if (user.getPassport() != null) {
            throw new BadRequestException(String.format("User with id = %s already has passport", userId));
        }

        Passport passport = passportMapper.passportDTOtoPassport(passportRequestDTO);
        passport.setUser(user);
        passport = passportRepository.save(passport);

        ResponseEntity<PassportResponseDTO> response = new ResponseEntity<>(
                passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);

        log.info("Creat new passport: {} for user with id: {}", passport, userId);

        return response;
    }

    @Override
    public ResponseEntity<PassportResponseDTO> updateUsersPassport(UUID userId,
                                                                   PassportRequestDTO passportRequestDTO) {

        log.info("Updating user's passport: {} by userId: {}", passportRequestDTO, userId);

        Passport passport = findPassportByUserOrThrowException(findUserByIdOrThrowException(userId));
        PassportUtil.getInstance().copyNotNullFieldsFromPassportDTOToPassport(passportRequestDTO, passport);
        ResponseEntity<PassportResponseDTO> response = new ResponseEntity<>(passportMapper.passportToPassportResponseDTO(passport), HttpStatus.OK);

        log.info("Update user's passport: {} by userId: {}", passport, userId);

        return response;
    }

    @Override
    public ResponseEntity<UserResponseDTO> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO) {

        log.info("Saving registered user: {}", createUserRequestDTO);

        if (userRepository.findUserByUsername(createUserRequestDTO.getUsername()).isPresent()) {

            log.warn("There is user with username: {}", createUserRequestDTO.getUsername());

            throw new BadRequestException("There is user with the same username");
        }

        if (userRepository.findUserByEmail(createUserRequestDTO.getEmail()).isPresent()) {

            log.warn("There is user with email: {}", createUserRequestDTO.getEmail());

            throw new BadRequestException("There is user with the same email");
        }

        User user = userMapper.createUserRequestDTOToUser(createUserRequestDTO);
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));

        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userMapper.userToUserResponseDTO(userRepository.save(user)), HttpStatus.OK);

        log.info("Save registered user: {}", user);

        // Send activation code to user email
        String message = String.format("Hello, %s! \n Welcome to car rental website. Please, visit next link for activate your profile: http://localhost:%s/registration/activate/%s!",
                user.getUsername(),
                SERVER_PORT,
                user.getActivationCode()
        );

        mailSenderService.send(user.getEmail(), "Activation code", message);

        return response;
    }

    @Override
    public ResponseEntity<UserResponseDTO> activateUser(String activateCode) {

        log.info("Activating user");

        User user = userRepository.findUserByActivationCode(activateCode)
                .orElseThrow(() -> new NotFoundException("Activation code is note found"));
        user.setActivationCode(null);
        user.setActive(true);

        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);

        log.info("Activate user: {}", user);

        return response;
    }


    @Override
    public ResponseEntity<UserResponseDTO> blockUser(UUID id) {

        log.info("Blocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (!user.isActive()) {
            throw new BadRequestException(String.format("User with id: %s is already blocked", id));
        }

        user.setActive(false);
        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);

        log.info("Block user: {}", user);

        return response;
    }

    @Override
    public ResponseEntity<UserResponseDTO> unlockUser(UUID id) {

        log.info("Unlocking user with id: {}", id);

        User user = findUserByIdOrThrowException(id);

        if (user.isActive()) {
            throw new BadRequestException(String.format("User with id: %s is already unlocked", id));
        }

        user.setActive(true);

        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userMapper.userToUserResponseDTO(user), HttpStatus.OK);

        log.info("Unlock user: {}", user);

        return response;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public User findUserByIdOrThrowException(UUID userId) {

        log.info("Finding user by id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                User.class, userId));

        log.info("Find user: {}", user);

        return user;
    }

    private Passport findPassportByUserOrThrowException(User user) {

        log.info("Finding passport by user: {}", user);

        Passport passport = Optional.ofNullable(user.getPassport())
                .orElseThrow(() -> new BadRequestException(
                        String.format("Passport not found for user with id = %s", user.getId())));

        log.info("Finding passport: {} by user: {}", passport, user);

        return passport;
    }
}
