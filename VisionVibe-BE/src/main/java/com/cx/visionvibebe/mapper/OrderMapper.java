package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.request.CreateNewOrderRequest;
import com.cx.visionvibebe.dto.response.OrderResponse;
import com.cx.visionvibebe.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Orders orders);

    Orders toOrder(CreateNewOrderRequest request);
}
