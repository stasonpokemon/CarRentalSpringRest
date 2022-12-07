package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.Role;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.entity.dto.UserDTO;
import com.spring.rest.api.exception.EntityNotFoundException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.repo.UserRepository;
import com.spring.rest.api.service.MailSenderService;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.CommonUtil;
import com.spring.rest.api.util.PassportUtil;
import com.spring.rest.api.util.tread.MailSenderThread;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailSenderService mailSenderService;

    @Value("${server.port}")
    private String SERVER_PORT;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findUser(Long userId) {
        ResponseEntity<?> response;
        try {
            UserDTO userDTO = userToUserDTO(findUserByIdOrThrowException(userId));
            response = new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<Sort.Order> orders = CommonUtil.getInstance().getOrdersFromRequest(sort, User.class);
            List<UserDTO> usersDTO = userRepository.findAll(Sort.by(orders)).stream().map(this::userToUserDTO).collect(Collectors.toList());
            if (usersDTO.isEmpty()) {
                response = new ResponseEntity<String>("There is no users", HttpStatus.NO_CONTENT);
                return response;
            }
            response = new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findPassportByUserId(Long userId) {
        ResponseEntity<?> response;
        try {
            User user = findUserByIdOrThrowException(userId);
            Passport passport = findPassportByUserOrThrowException(user);
            response = new ResponseEntity<PassportDTO>(modelMapper.map(passport, PassportDTO.class), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to find passport by userId", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    public ResponseEntity<?> createPassportForUser(Long userId, PassportDTO passportDTO) {
        ResponseEntity<?> response;
        try {
            User user = findUserByIdOrThrowException(userId);
            if (user.getPassport() != null) {
                return new ResponseEntity<String>(new StringBuilder()
                        .append("User with id = ")
                        .append(userId)
                        .append(" already has passport").toString(), HttpStatus.OK);
            }
            user.setPassport(modelMapper.map(passportDTO, Passport.class));
            response = new ResponseEntity<PassportDTO>(passportDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to create passport", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> updateUsersPassport(Long userId, PassportDTO passportDTO) {
        ResponseEntity<?> response;
        try {
            Passport passport = findPassportByUserOrThrowException(findUserByIdOrThrowException(userId));
            PassportUtil.getInstance().copyNotNullFieldsFromPassportDTOToPassport(passportDTO, passport);
            response = new ResponseEntity<PassportDTO>(modelMapper.map(passport, PassportDTO.class), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to create passport", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> saveRegisteredUser(UserDTO userDTO) {
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            return new ResponseEntity<String>("There is user with the same username", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            return new ResponseEntity<String>("There is user with the same email", HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<?> response;
        User user = modelMapper.map(userDTO, User.class);
        try {
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setRoles(Collections.singleton(Role.USER));
            response = new ResponseEntity<UserDTO>(userToUserDTO(userRepository.save(user)), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>("Unable to save registered user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Send activation code to user email
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \n Welcome to car rental website. Please, visit next link for activate your profile: http://localhost:%s/registration/activate/%s!",
                    user.getUsername(),
                    SERVER_PORT,
                    user.getActivationCode()
            );
            new MailSenderThread(mailSenderService, user.getEmail(), "Activation code", message).start();
        }
        return response;
    }

    @Override
    public ResponseEntity<?> activateUser(String activateCode) {
        ResponseEntity<?> response;
        try {
            User user = userRepository.findUserByActivationCode(activateCode).orElseThrow(() -> new EntityNotFoundException("Activation code is note found"));
            user.setActivationCode(null);
            user.setActive(true);
            response = new ResponseEntity<String>("User successfully activated", HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to activate user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    public ResponseEntity<?> blockUser(Long id) {
        ResponseEntity<?> response;
        try {
            User user = findUserByIdOrThrowException(id);
            if (!user.isActive()) {
                return new ResponseEntity<String>("User is already blocked", HttpStatus.OK);
            }
            user.setActive(false);
            response = new ResponseEntity<String>("User is blocked", HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to block user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> unlockUser(Long id) {
        ResponseEntity<?> response;
        try {
            User user = findUserByIdOrThrowException(id);
            if (user.isActive()) {
                return new ResponseEntity<String>("User is already unlocked", HttpStatus.OK);
            }
            user.setActive(true);
            response = new ResponseEntity<String>("User is unlocked", HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to unlock user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public User findUserByIdOrThrowException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(new StringBuilder()
                .append("User with id = ")
                .append(userId)
                .append(" not found").toString()));
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Passport findPassportByUserOrThrowException(User user) {
        return Optional.ofNullable(user.getPassport()).orElseThrow(() -> new EntityNotFoundException(new StringBuilder()
                .append("Passport with id = ")
                .append(user.getId())
                .append(" not found").toString()));
    }

    private UserDTO userToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (user.getPassport() != null) {
            userDTO.setPassport(modelMapper.map(user.getPassport(), PassportDTO.class));
        }
        return userDTO;
    }


}
