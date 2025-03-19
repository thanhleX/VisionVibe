package com.cx.visionvibebe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewOrderDetailDto {
    private Long productDetailId;
    private Integer amount;
}
