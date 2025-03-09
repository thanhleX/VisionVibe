package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.response.OrderDetailResponse;
import com.cx.visionvibe.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
