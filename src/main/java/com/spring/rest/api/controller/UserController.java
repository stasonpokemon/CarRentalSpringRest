package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Interface that presents basic endpoints for working with User entity.
 */
@RequestMapping("/users")
@Tag(name = "User Controller", description = "User management controller")
public interface UserController {

    @Operation(
            summary = "Find all users",
            description = "This endpoint allows you to get all users from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following users",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    ResponseEntity<Page<UserResponseDTO>> findAll(
            @ParameterObject @PageableDefault Pageable pageable);

    @Operation(
            summary = "Find user by id",
            description = "This endpoint allows you to get user by id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bed request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> findUserById(
            @PathVariable("id") UUID userId);

    @Operation(
            summary = "Block user",
            description = "This endpoint allows you to block user that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "User successfully blocked",
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
    @PatchMapping("/{id}/block")
    ResponseEntity<UserResponseDTO> blockUser(
            @PathVariable("id") UUID userId);

    @Operation(
            summary = "Unlock user",
            description = "This endpoint allows you to unlock user that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "User successfully unlocked",
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
    @PatchMapping("/{id}/unlock")
    ResponseEntity<UserResponseDTO> unlockUser(
            @PathVariable("id") UUID userId);

    @Operation(
            summary = "Find user's orders",
            description = "This endpoint allows you to get user's orders by user's id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following orders",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Bed request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}/orders")
    ResponseEntity<Page<OrderResponseDTO>> findUsersOrders(
            @ParameterObject @PageableDefault Pageable pageable,
            @PathVariable("id") UUID userId);

    @Operation(
            summary = "Find user's passport",
            description = "This endpoint allows you to get user's passport by user's id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following passport",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bed request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}/passport")
    ResponseEntity<PassportResponseDTO> findUsersPassport(
            @PathVariable("id") UUID userId);

    @Operation(
            summary = "Create passport for user",
            description = "This endpoint allows you to create a new passport for already exists user")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Passport successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportResponseDTO.class))}),
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
    @PostMapping("/{id}/passport")
    ResponseEntity<PassportResponseDTO> createPassportForUser(
            @PathVariable("id") UUID userId,
            @RequestBody @Valid PassportRequestDTO passportRequestDTO);

    @Operation(
            summary = "Update user's passport",
            description = "This endpoint allows you to update an already exists user's passport")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Passport successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportResponseDTO.class))}),
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
    @PatchMapping("/{id}/passport")
    ResponseEntity<PassportResponseDTO> editUsersPassport(
            @PathVariable("id") UUID userId,
            @RequestBody PassportRequestDTO passportRequestDTO);

}
