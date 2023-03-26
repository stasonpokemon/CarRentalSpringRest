package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean damaged;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String damageDescription;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime refundDate;
}