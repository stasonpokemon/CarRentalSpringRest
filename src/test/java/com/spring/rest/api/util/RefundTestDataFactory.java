package com.spring.rest.api.util;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import com.spring.rest.api.entity.mapper.RefundMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefundTestDataFactory {

    private static final RefundMapper refundMapper = Mappers.getMapper(RefundMapper.class);

    public static CreateRefundRequestDTO buildCreateRefundRequestDTOWithoutDamage() {
        return CreateRefundRequestDTO.builder()
                .damaged(false)
                .damageDescription("")
                .price(0).build();
    }

    public static CreateRefundRequestDTO buildCreateRefundRequestDTOWithDamage() {
        return CreateRefundRequestDTO.builder()
                .damaged(true)
                .damageDescription("With damage")
                .price(1000).build();
    }

    public static Refund buildRefundWithoutDamage(CreateRefundRequestDTO createRefundRequestDTO,
                                                  Order order) {
        order.getCar().setBusy(false);

        Refund refund = Refund.builder()
                .damaged(createRefundRequestDTO.isDamaged())
                .damageDescription("")
                .price(0)
                .refundDate(LocalDateTime.now())
                .build();

        order.setRefund(refund);

        refund.setOrder(order);

        return refund;
    }


    public static Refund buildRefundWithDamage(CreateRefundRequestDTO createRefundRequestDTO,
                                               Order order) {
        order.getCar().setBroken(true);
        order.getCar().setDamageStatus(createRefundRequestDTO.getDamageDescription());

        Refund refund = Refund.builder()
                .damaged(createRefundRequestDTO.isDamaged())
                .damageDescription(createRefundRequestDTO.getDamageDescription())
                .price(createRefundRequestDTO.getPrice())
                .refundDate(LocalDateTime.now()).build();

        order.setRefund(refund);

        refund.setOrder(order);

        return refund;
    }

    public static RefundResponseDTO buildRefundResponseDTO(Refund refund) {
        return refundMapper.refundToRefundResponseDTO(refund);
    }

}
