package com.cx.visionvibebe.dto.response;

import com.cx.visionvibebe.dto.ProductDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double discountPercentage;
    private Boolean isActive;
    private String thumbnailUrl;
    private String thumbnailPublicId;
    private List<ProductDto> productDtoList;
}
