package com.spring.rest.api.entity.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_UUID, description = ORDER_UUID)
    private UUID id;

    @Schema(example = CAR_UUID, description = CAR_UUID)
    @JsonProperty(namespace = "carId", access = JsonProperty.Access.READ_ONLY)
    private UUID carId;

    @Schema(example = USER_UUID, description = USER_UUID)
    @JsonProperty(namespace = "userId", access = JsonProperty.Access.READ_ONLY)
    private UUID userId;

    @Schema(example = REFUND_UUID, description = REFUND_UUID)
    @JsonProperty(namespace = "refundId", access = JsonProperty.Access.READ_ONLY)
    private UUID refundId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_PRICE, description = ORDER_PRICE)
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_ORDER_STATUS, description = ORDER_ORDER_STATUS)
    private OrderStatus orderStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_ORDER_DATE, description = ORDER_ORDER_DATE)
    private LocalDateTime orderDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_RENTAL_PERIOD, description = ORDER_RENTAL_PERIOD)
    private Integer rentalPeriod;
}