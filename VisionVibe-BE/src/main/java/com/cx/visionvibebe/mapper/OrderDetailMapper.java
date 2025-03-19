package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.response.OrderDetailResponse;
import com.cx.visionvibebe.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
