package com.spring.rest.api.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGE_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_DAMAGE_DESCRIPTION_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_PRICE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.REFUND_PRICE_DESCRIPTION;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRefundRequestDTO {

    @NotNull(message = "isDamaged field must be filled")
    @Schema(example = REFUND_DAMAGE_STATUS, description = REFUND_DAMAGE_STATUS_DESCRIPTION)
    private boolean damaged;

    @Schema(example = REFUND_DAMAGE_DESCRIPTION, description = REFUND_DAMAGE_DESCRIPTION_DESCRIPTION)
    private String damageDescription;

    @Min(value = 0, message = "Price must be 0 or greater than 0")
    @Schema(example = REFUND_PRICE, description = REFUND_PRICE_DESCRIPTION)
    private double price;

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Invalid order id")
    @NotBlank(message = "Order id can't be null")
    @Schema(example = ORDER_UUID, description = ORDER_UUID_DESCRIPTION)
    private String orderId;

}
