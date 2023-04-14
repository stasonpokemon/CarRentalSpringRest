package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface that presents basic endpoints for working with User entity.
 */
@RequestMapping("/registration")
@Tag(name = "Registration Controller", description = "User registration management controller")
public interface RegistrationController {

    @Operation(
            summary = "Create new user",
            description = "This endpoint allows you to create new user in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "User successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PostMapping()
    ResponseEntity<UserResponseDTO> saveRegisteredUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO);

    @Operation(
            summary = "Activate user by activate code",
            description = "This endpoint allows you to activate an already existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "User successfully activated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/activate/{code}")
    ResponseEntity<UserResponseDTO> activateUser(
            @PathVariable("code") String activateCode);
}
