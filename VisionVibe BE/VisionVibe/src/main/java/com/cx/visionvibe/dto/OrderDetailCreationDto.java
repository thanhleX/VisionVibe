package com.cx.visionvibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailCreationDto {
    private Long productDetailId;
    private Integer quantity;
}
