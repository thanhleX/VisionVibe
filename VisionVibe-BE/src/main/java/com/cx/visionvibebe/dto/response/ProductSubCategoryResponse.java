package com.cx.visionvibebe.dto.response;

import com.cx.visionvibebe.dto.ProductCategoryDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSubCategoryResponse {
    private Long id;
    private String name;
    private String thumbnailUrl;
    private String thumbnailPublicId;
    private boolean isActive;
    private ProductCategoryDto productCategoryDto;
    private String description;
}
