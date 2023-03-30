package com.spring.rest.api.service;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.exception.BadRequestException;
import com.spring.rest.api.repo.PassportRepository;
import com.spring.rest.api.repo.UserRepository;
import com.spring.rest.api.service.impl.UserServiceImpl;
import com.spring.rest.api.util.PassportTestDataFactory;
import com.spring.rest.api.util.UserTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private MailSenderService mailSenderService;

    private UUID userId;

    private User firstUserWithPassport;

    private User secondUserWithoutPassport;

    private User newUser;

    private User blockedUser;

    private Passport newPassportForSecondUser;

    private UserResponseDTO firstUserResponseDTO;

    private CreateUserRequestDTO createUserRequestDTO;

    private PassportResponseDTO firstUserPassportResponseDTO;

    private PassportRequestDTO newPassportRequestDTOForSecondUser;

    private PassportRequestDTO updatedPassportRequestDTO;

    private PassportResponseDTO newPassportResponseDTOForSecondUser;

    private PassportResponseDTO updatedPassportResponseDTO;

    private Pageable pageRequest;

    private Page<UserResponseDTO> userDTOsPage;

    private Page<User> usersPage;


    @BeforeEach
    void init() {
        userId = UUID.randomUUID();

        firstUserWithPassport = UserTestDataFactory.buildUserWithPassport();

        secondUserWithoutPassport = UserTestDataFactory.buildUserWithOutPassport();

        Passport passportForUpdate = PassportTestDataFactory.buildPassport();

        firstUserResponseDTO = UserTestDataFactory.buildUserResponseDTO(firstUserWithPassport);

        UserResponseDTO secondUserResponseDTO = UserTestDataFactory.buildUserResponseDTO(secondUserWithoutPassport);

        newPassportForSecondUser = PassportTestDataFactory.buildPassportOfUser(secondUserWithoutPassport);

        newPassportRequestDTOForSecondUser = PassportTestDataFactory.buildPassportDTOFromPassport(newPassportForSecondUser);

        updatedPassportRequestDTO = PassportTestDataFactory.buildPassportDTOFromPassport(passportForUpdate);

        createUserRequestDTO = UserTestDataFactory.buildCreateUserRequestDTO();

        firstUserPassportResponseDTO = UserTestDataFactory.buildPassportResponseDTOFromUser(firstUserWithPassport);

        newPassportResponseDTOForSecondUser = PassportTestDataFactory.buildPassportResponseDTOFromPassport(newPassportForSecondUser);

        updatedPassportResponseDTO = PassportTestDataFactory.buildPassportResponseDTOFromPassport(passportForUpdate);

        newUser = UserTestDataFactory.buildNewUserFromCreateUserRequestDTO(createUserRequestDTO);

        blockedUser = UserTestDataFactory.buildBlockedUser();

        userDTOsPage = new PageImpl<>(List.of(firstUserResponseDTO, secondUserResponseDTO));

        usersPage = new PageImpl<>(List.of(firstUserWithPassport, secondUserWithoutPassport));

        pageRequest = PageRequest.of(0, 2);
    }


    @Test
    void findUser_WhenUserExistsWithSpecifiedId_ReturnUserDTO() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(firstUserWithPassport));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.findUser(userId);
        UserResponseDTO responseBody = (UserResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstUserResponseDTO, responseBody);

        verify(userRepository).findById(userId);
    }

    @Test
    void findUser_WhenUserDoesNotExistsWithSpecifiedId_ThrowsNotNullException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.findUser(userId));

        //then - verify the output
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void findAll_WhenThereIsUsersInDataBase_ReturnPageOfUserDTOs() {
        //given - precondition or setup
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.findAll(pageRequest);
        Page<UserResponseDTO> responseBody = (Page<UserResponseDTO>) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTOsPage, responseBody);

        verify(userRepository).findAll(pageRequest);
    }

    @Test
    void findAll_WhenThereIsUsersInNotDataBase_ThrowsNotFoundException() {
        //given - precondition or setup
        when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList()));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.findAll(pageRequest));

        //then - verify the output
        assertEquals("Not found Users", notFoundException.getMessage());

        verify(userRepository).findAll(pageRequest);
    }

    @Test
    void findPassportByUserId_WhenUserIdIsValidAndUserHasPassport_ReturnPassportDTO() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUserWithPassport));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.findPassportByUserId(userId);
        PassportResponseDTO responseBody = (PassportResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstUserPassportResponseDTO, responseBody);

        verify(userRepository).findById(userId);
    }

    @Test
    void findPassportByUserId_WhenUserIdIsValidAndUserHasNoPassport_ThrowsNotFoundException() {
        //given - precondition or setup
        secondUserWithoutPassport.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(secondUserWithoutPassport));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.findPassportByUserId(userId));

        //then - verify the output
        assertEquals(String.format("Passport not found for user with id = %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void findPassportByUserId_WhenUserIdIsInvalid_ThrowsNotFoundException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.findPassportByUserId(userId));

        //then - verify the output
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void createPassportForUser_WhenUserIdIsValidAndUserHasNotPassport_ReturnPassportDTO() {
        //given - precondition or setup
        when(passportRepository.save(newPassportForSecondUser)).thenReturn(newPassportForSecondUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(secondUserWithoutPassport));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.createPassportForUser(userId, newPassportRequestDTOForSecondUser);
        PassportResponseDTO responseBody = (PassportResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newPassportResponseDTOForSecondUser, responseBody);

        verify(userRepository).findById(userId);
        verify(passportRepository).save(any(Passport.class));
    }

    @Test
    void createPassportForUser_WhenUserIdIsValidAndUserHasPassport_ThrowsBadRequestException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUserWithPassport));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> userService.createPassportForUser(userId, newPassportRequestDTOForSecondUser));

        //then - verify the output
        assertEquals(String.format("User with id = %s already has passport", userId), badRequestException.getMessage());

        verify(userRepository).findById(userId);
        verify(passportRepository, never()).save(any(Passport.class));
    }

    @Test
    void createPassportForUser_WhenUserIdIsInvalid_ThrowsNotFoundException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.createPassportForUser(userId, newPassportRequestDTOForSecondUser));

        //then - verify the output
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
        verify(passportRepository, never()).save(any(Passport.class));
    }

    @Test
    void updateUsersPassport_WhenUserIdIsValidAndUserHasPassport_ReturnUpdatedPassport() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUserWithPassport));
        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.updateUsersPassport(userId, updatedPassportRequestDTO);
        PassportResponseDTO responseBody = (PassportResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPassportResponseDTO, responseBody);

        verify(userRepository).findById(userId);
    }

    @Test
    void updateUsersPassport_WhenUserIdIsValidAndUserHasNotPassport_ThrowsBadRequestException() {
        //given - precondition or setup
        secondUserWithoutPassport.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(secondUserWithoutPassport));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException =
                assertThrows(BadRequestException.class, () -> userService.updateUsersPassport(userId, updatedPassportRequestDTO));

        //then - verify the output
        assertEquals(String.format("Passport not found for user with id = %s", userId), badRequestException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void updateUsersPassport_WhenUserIdIsInvalid_ThrowsNotFoundException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException =
                assertThrows(NotFoundException.class, () -> userService.updateUsersPassport(userId, updatedPassportRequestDTO));

        //then - verify the output
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }


    @Test
    void saveRegisteredUser_WhenUserUsernameAndEmailNotExist_ReturnSavedUserAndSendMail() {
        //given - precondition or setup
        String expectedSavedUserUsername = createUserRequestDTO.getUsername();
        String expectedSavedUserEmail = createUserRequestDTO.getEmail();

        when(userRepository.findUserByUsername(createUserRequestDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail(createUserRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        doNothing().when(mailSenderService).send(any(String.class), any(String.class), any(String.class));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.saveRegisteredUser(createUserRequestDTO);
        UserResponseDTO responseBody = (UserResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSavedUserUsername, responseBody.getUsername());
        assertEquals(expectedSavedUserEmail, responseBody.getEmail());

        verify(userRepository).findUserByUsername(createUserRequestDTO.getUsername());
        verify(userRepository).findUserByEmail(createUserRequestDTO.getEmail());
        verify(userRepository).save(any(User.class));
        verify(mailSenderService).send(any(String.class), any(String.class), any(String.class));
    }

    @Test
    void saveRegisteredUser_WhenUserUsernameDoesNotExistsAndEmailExists_ThrowsBadRequestException() {
        //given - precondition or setup
        when(userRepository.findUserByUsername(createUserRequestDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail(createUserRequestDTO.getEmail())).thenReturn(Optional.of(secondUserWithoutPassport));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> userService.saveRegisteredUser(createUserRequestDTO));

        //then - verify the output
        assertEquals("There is user with the same email", badRequestException.getMessage());

        verify(userRepository).findUserByUsername(createUserRequestDTO.getUsername());
        verify(userRepository).findUserByEmail(createUserRequestDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(mailSenderService, never()).send(any(String.class), any(String.class), any(String.class));
    }

    @Test
    void saveRegisteredUser_WhenUserUsernameExists_ThrowsBadRequestException() {
        //given - precondition or setup
        when(userRepository.findUserByUsername(createUserRequestDTO.getUsername())).thenReturn(Optional.of(secondUserWithoutPassport));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> userService.saveRegisteredUser(createUserRequestDTO));

        //then - verify the output
        assertEquals("There is user with the same username", badRequestException.getMessage());

        verify(userRepository).findUserByUsername(createUserRequestDTO.getUsername());
        verify(userRepository, never()).findUserByEmail(createUserRequestDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(mailSenderService, never()).send(any(String.class), any(String.class), any(String.class));
    }

    @Test
    void activateUser_WhenUserWithSpecifiedActivatedCodeExists_ReturnSuccessfulStringMessage() {
        //given - precondition or setup
        String newUserActivationCode = newUser.getActivationCode();
        when(userRepository.findUserByActivationCode(newUserActivationCode)).thenReturn(Optional.of(newUser));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.activateUser(newUserActivationCode);
        String responseBody = (String) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully activated", responseBody);

        verify(userRepository).findUserByActivationCode(newUserActivationCode);
    }

    @Test
    void activateUser_WhenUserWithSpecifiedActivatedCodeDoesNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String wrongActivationCode = "wrong_activation_code";
        when(userRepository.findUserByActivationCode(wrongActivationCode)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.activateUser(wrongActivationCode));
        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals("Activation code is note found", notFoundException.getMessage());

        verify(userRepository).findUserByActivationCode(wrongActivationCode);
    }

    @Test
    void blockUser_WhenUserIdIsValidAndUserIsNotBlocked_ReturnSuccessfulStringMessage() {
        //given - precondition or setup
        UserResponseDTO expectedUserResponseDTO = UserTestDataFactory.buildUserResponseDTO(firstUserWithPassport);
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUserWithPassport));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.blockUser(userId);
        UserResponseDTO responseBody = (UserResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserResponseDTO, responseBody);

        verify(userRepository).findById(userId);
    }

    @Test
    void blockUser_WhenUserIdIsValidAndUserIsBlocked_ThrowsBadRequestException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.of(blockedUser));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> userService.blockUser(userId));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(String.format("User with id: %s is already blocked", userId), badRequestException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void blockUser_WhenUserIdIsInvalidAnd_ThrowsNotFound() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.blockUser(userId));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void unlockUser_WhenUserIdIsValidAndUserIsBlocked_ReturnSuccessfulStringMessage() {
        //given - precondition or setup
        UserResponseDTO expectedUserResponseDTO = UserTestDataFactory.buildUserResponseDTO(blockedUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(blockedUser));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = userService.unlockUser(userId);
        UserResponseDTO responseBody = (UserResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserResponseDTO, responseBody);

        verify(userRepository).findById(userId);
    }

    @Test
    void unlockUser_WhenUserIdIsValidAndUserIsNotBlocked_ThrowsBadRequestException() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUserWithPassport));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> userService.unlockUser(userId));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(String.format("User with id: %s is already unlocked", userId), badRequestException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void unlockUser_WhenUserIdIsInvalid_ThrowsNotFound() {
        //given - precondition or setup
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> userService.unlockUser(userId));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(String.format("Not found User with id: %s", userId), notFoundException.getMessage());

        verify(userRepository).findById(userId);
    }

    @Test
    void findUserByIdOrThrowException() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test

        //then - verify the output
    }

}