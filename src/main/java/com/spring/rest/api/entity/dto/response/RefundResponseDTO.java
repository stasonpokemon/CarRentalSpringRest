package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_PRICE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponseDTO {

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
}