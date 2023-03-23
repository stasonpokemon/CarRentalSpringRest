package com.spring.rest.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {

    @NotNull(message = "isDamaged field must be filled")
    private boolean damaged;

    private String damageDescription;

    @Min(value = 0, message = "Price must be 0 or greater than 0")
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime refundDate;
}