package com.spring.rest.api.entity.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_ORDER_DATE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_ORDER_DATE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_ORDER_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_ORDER_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_PRICE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_PRICE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @Schema(example = ORDER_UUID, description = ORDER_UUID_DESCRIPTION)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(example = CAR_UUID, description = CAR_UUID_DESCRIPTION)
    @JsonProperty(namespace = "carId", access = JsonProperty.Access.READ_ONLY)
    private UUID carId;

    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    @JsonProperty(namespace = "userId", access = JsonProperty.Access.READ_ONLY)
    private UUID userId;

    @Schema(example = REFUND_UUID, description = REFUND_UUID_DESCRIPTION)
    @JsonProperty(namespace = "refundId", access = JsonProperty.Access.READ_ONLY)
    private UUID refundId;

    @Schema(example = ORDER_PRICE, description = ORDER_PRICE_DESCRIPTION)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double price;

    @Schema(example = ORDER_ORDER_STATUS, description = ORDER_ORDER_STATUS_DESCRIPTION)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus orderStatus;

    @Schema(example = ORDER_ORDER_DATE, description = ORDER_ORDER_DATE_DESCRIPTION)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime orderDate;

    @Schema(example = ORDER_RENTAL_PERIOD, description = ORDER_RENTAL_PERIOD_DESCRIPTION)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer rentalPeriod;
}