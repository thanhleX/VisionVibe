package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductImageDto;
import com.cx.visionvibebe.dto.response.ProductImageResponse;
import com.cx.visionvibebe.entity.ProductImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageResponse toProductImageResponse(ProductImage productImage);

    List<ProductImageResponse> toProductImageResponseList(List<ProductImage> productImageList);

    List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList);
}
