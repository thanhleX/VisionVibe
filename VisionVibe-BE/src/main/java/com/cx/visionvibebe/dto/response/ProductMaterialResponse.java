package com.cx.visionvibebe.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProductMaterialResponse {
    private Long id;
    private String material;
}
