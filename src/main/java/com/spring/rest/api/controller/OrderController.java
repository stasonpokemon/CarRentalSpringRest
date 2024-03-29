package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
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
 * Interface that presents basic endpoints for working with Order entity.
 */
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Order management controller")
public interface OrderController {

    @Operation(
            summary = "Find all orders",
            description = "This endpoint allows you to get all orders from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following orders",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    ResponseEntity<Page<OrderResponseDTO>> findAll(
            @ParameterObject @PageableDefault Pageable pageable);

    @Operation(
            summary = "Find order by id",
            description = "This endpoint allows you to get order by id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))}),
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
    ResponseEntity<?> findById(
            @PathVariable("id") UUID orderId);

    @Operation(
            summary = "Find refund by id order's id",
            description = "This endpoint allows you to get order's refund by order's id from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Found the following refund",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefundResponseDTO.class))}),
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
    @GetMapping("/{id}/refund")
    ResponseEntity<RefundResponseDTO> findOrdersRefund(
            @PathVariable("id") UUID orderId);

    @Operation(
            summary = "Create new order",
            description = "This endpoint allows you to create a new order in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Order successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))}),
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
    @PostMapping()
    ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO);

    @Operation(
            summary = "Accept order",
            description = "This endpoint allows you to accept order that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Order successfully accepted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))}),
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
    @PatchMapping("/{orderId}/accept")
    ResponseEntity<OrderResponseDTO> acceptOrder(
            @PathVariable("orderId") UUID orderId);

    @Operation(
            summary = "Cancel order",
            description = "This endpoint allows you to cancel order that already exist in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Order successfully canceled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))}),
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
    @PatchMapping("/{orderId}/cancel")
    ResponseEntity<?> cancelOrder(
            @PathVariable("orderId") UUID orderId);

    @Operation(
            summary = "Create refund",
            description = "This endpoint allows you to create a new order's refund in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Refund successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefundResponseDTO.class))}),
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
    @PostMapping("/refund")
    ResponseEntity<RefundResponseDTO> createOrdersRefund
            (@RequestBody @Valid CreateRefundRequestDTO createRefundRequestDTO);
}
