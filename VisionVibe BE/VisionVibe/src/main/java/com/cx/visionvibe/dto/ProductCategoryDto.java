package com.cx.visionvibe.dto;

import com.cx.visionvibe.dto.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductCategoryDto {
    private Long id;
    private String name;
    private List<ProductResponse> productResponseList;
}
