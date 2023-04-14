package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * This interface presents the basic contract for converting Refund to RefundDTO and vice versa.
 */
@Mapper
public interface RefundMapper {

    @Mapping(target = "orderId", source = "order.id")
    RefundResponseDTO refundToRefundResponseDTO(Refund refund);

    Refund createRefundRequestDTOToRefund(CreateRefundRequestDTO createRefundRequestDTO);
}
