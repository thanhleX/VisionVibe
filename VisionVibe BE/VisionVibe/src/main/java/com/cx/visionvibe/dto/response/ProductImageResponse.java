package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImageResponse {
    private Long id;
    private String url;
    private String publicId;
    private Long productDetailId;
}
