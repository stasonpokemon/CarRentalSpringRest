package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.dto.RefundDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RefundMapper {

    Refund refundDTOToRefund(RefundDTO refundDTO);

    RefundDTO refundToRefundDTO(Refund refund);
}
