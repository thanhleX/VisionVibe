package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.ProductImageDto;
import com.cx.visionvibe.dto.response.ProductImageResponse;
import com.cx.visionvibe.entity.ProductImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageResponse toProductImageResponse(ProductImage productImage);

    List<ProductImageResponse> toProductImageResponseList(List<ProductImage> productImageList);

    List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList);
}
