package com.spring.rest.api.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD;
import static com.spring.rest.api.util.swagger.OpenApiConstants.ORDER_RENTAL_PERIOD_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestDTO {

    @NotNull(message = "Please fill the rental period")
    @Min(value = 1, message = "Rental period should be greater than 1")
    @Schema(example = ORDER_RENTAL_PERIOD, description = ORDER_RENTAL_PERIOD_DESCRIPTION)
    private Integer rentalPeriod;

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Invalid car id")
    @NotBlank(message = "Car id can't be null")
    @Schema(example = CAR_UUID, description = CAR_UUID_DESCRIPTION)
    private String carId;

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Invalid user id")
    @NotBlank(message = "User id can't be null")
    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    private String userId;

}
