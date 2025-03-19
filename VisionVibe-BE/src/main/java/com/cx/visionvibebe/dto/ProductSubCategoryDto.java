package com.cx.visionvibebe.dto;

import com.cx.visionvibebe.dto.response.ProductResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProductSubCategoryDto {
    private Long id;
    private String name;
    private List<ProductResponse> productResponseList;
}
