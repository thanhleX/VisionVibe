package com.cx.visionvibebe.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {
    private String id;
    private String url;
    private String publicId;
    private Long productDetailId;
}
