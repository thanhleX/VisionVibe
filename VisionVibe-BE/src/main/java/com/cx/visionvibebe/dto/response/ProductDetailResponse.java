package com.cx.visionvibebe.dto.response;

import com.cx.visionvibebe.dto.ProductColorDto;
import com.cx.visionvibebe.dto.ProductDto;
import com.cx.visionvibebe.dto.ProductMaterialDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponse {
    private Long id;
    private Integer stock;
    @JsonProperty(value = "isActive")
    private boolean isActive;
    private ProductDto productDto;
    private ProductColorDto productColorDto;
    private ProductMaterialDto productMaterialDto;
    private List<ProductImageResponse> productImageResponseList;
}
