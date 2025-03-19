package com.cx.visionvibebe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDto {
    private Long id;
    private Integer stock;
    @JsonProperty(value = "isActive")
    private boolean isActive;
    private ProductDto productDto;
    private ProductColorDto productColorDto;
    private ProductMaterialDto productMaterialDto;
    private List<ProductImageDto> productImageDtoList;
}
