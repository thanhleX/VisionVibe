package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.request.OrderCreationRequest;
import com.cx.visionvibe.dto.response.OrderResponse;
import com.cx.visionvibe.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse torderResponse(Order order);

    Order toOrder(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);
}
