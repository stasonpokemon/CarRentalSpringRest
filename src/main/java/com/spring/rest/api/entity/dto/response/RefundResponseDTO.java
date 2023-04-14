package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGED;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_PRICE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_REFUND_DATE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_UUID;

/**
 * This class presents a DTO, which is available via OrderController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = REFUND_UUID, description = REFUND_UUID)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = REFUND_DAMAGED, description = REFUND_DAMAGED)
    private boolean damaged;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = REFUND_DAMAGE_DESCRIPTION, description = REFUND_DAMAGE_DESCRIPTION)
    private String damageDescription;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = REFUND_PRICE, description = REFUND_PRICE)
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = REFUND_REFUND_DATE, description = REFUND_REFUND_DATE)
    private LocalDateTime refundDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = ORDER_UUID, description = ORDER_UUID)
    private UUID orderId;
}
