package com.cx.visionvibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImageDto {
    private Long id;
    private String url;
    private String publicId;
}
