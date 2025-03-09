package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailResponse {
    private Long id;
    ProductDetailResponse productDetailResponse;
    Integer quantity;;
}
