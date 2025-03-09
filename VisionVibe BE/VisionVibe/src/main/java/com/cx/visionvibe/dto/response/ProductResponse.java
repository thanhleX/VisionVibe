package com.cx.visionvibe.dto.response;

import com.cx.visionvibe.dto.ProductCategoryDto;
import com.cx.visionvibe.dto.ProductDetailDto;
import com.cx.visionvibe.dto.PromotionDto;
import com.cx.visionvibe.entity.Brand;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String productName;
    Double price;
    String thumbnailUrl;
    String thumbnailPublicId;
    Boolean isActive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt;
    ProductCategoryDto productCategoryDto;
    Brand brand;
    List<ProductDetailDto> productDetailDtoList;
    List<PromotionDto> promotionDtoList;
}
