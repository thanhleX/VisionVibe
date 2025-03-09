package com.cx.visionvibe.dto.request;

import com.cx.visionvibe.dto.OrderDetailCreationDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    String customerName;
    String email;
    String phone;
    String address;
    String note;
    Long paymentMethodId;
    List<OrderDetailCreationDto> orderDetailCreationDtoList;
}
