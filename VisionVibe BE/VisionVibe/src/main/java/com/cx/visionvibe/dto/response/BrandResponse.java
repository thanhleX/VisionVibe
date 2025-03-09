package com.cx.visionvibe.dto.response;

import com.cx.visionvibe.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private String thumbnailUrl;
    private String thumbnailPublicId;
    private Boolean isActive;
    private List<Product> products;
}
