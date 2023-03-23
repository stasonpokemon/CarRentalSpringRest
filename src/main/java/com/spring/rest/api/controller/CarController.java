package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.request.CreatOrUpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/cars")
@Tag(name = "Car Controller", description = "Car management controller")
public interface CarController {

    @Operation(
            summary = "Find all cars",
            description = "This endpoint allows you to get all cars from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following cars",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResponseDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/all")
    ResponseEntity<?> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(
            summary = "Find all cars not marked as deleted",
            description = "This endpoint allows you to get all cars not marked as deleted from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following cars",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResponseDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Cars not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    ResponseEntity<?> findAllNotMarkedAsDeleted(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(
            summary = "Find car by id",
            description = "This endpoint allows you to get car by id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following car",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
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
    ResponseEntity<?> findCar(
            @PathVariable(name = "id") Long carId);

    @Operation(
            summary = "Create new car",
            description = "This endpoint allows you to create a new car in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @PostMapping
    ResponseEntity<?> createCar(
            @RequestBody @Valid CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO);

    @Operation(
            summary = "Update car",
            description = "This endpoint allows you to update an already existing car in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
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
    @PatchMapping("/{id}")
    ResponseEntity<?> updateCar(
            @PathVariable("id") Long carId,
            @RequestBody @Valid CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO);

    @Operation(
            summary = "Fix broken car",
            description = "This endpoint allows you to fix broken car that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully fixed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
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
    @PatchMapping("/{id}/fix")
    ResponseEntity<?> fixBrokenCar(
            @PathVariable("id") Long carId);

    @Operation(
            summary = "Mark car as deleted",
            description = "This endpoint allows you to mark car as deleted that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Car successfully mark as deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class))}),
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
    @PatchMapping("/{id}/remove")
    ResponseEntity<String> markCarAsDeleted(
            @PathVariable("id") Long carId);
}
