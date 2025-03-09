package com.cx.visionvibe.dto.response;

import com.cx.visionvibe.dto.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
public class ProductDetailResponse {
    Long id;
    Integer stock;
    @JsonProperty(value = "isActive")
    Boolean isActive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt;
    ProductDto productDto;
    FrameColorDto frameColorDto;
    LensColorDto lensColorDto;
    FrameMaterialDto frameMaterialDto;
    LensMaterialDto lensMaterialDto;
    List<ProductImageResponse> productImageResponseList;
}
