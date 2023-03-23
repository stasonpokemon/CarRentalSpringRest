package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RefundMapper {

    RefundResponseDTO refundToRefundDTO(Refund refund);

    Refund createRefundRequestDTOToRefund(CreateRefundRequestDTO createRefundRequestDTO);
}
