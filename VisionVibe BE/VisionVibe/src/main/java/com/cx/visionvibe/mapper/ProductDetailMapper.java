package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.ProductDetailDto;
import com.cx.visionvibe.dto.request.ProductDetailCreationRequest;
import com.cx.visionvibe.dto.request.ProductDetailUpdateRequest;
import com.cx.visionvibe.dto.response.ProductDetailResponse;
import com.cx.visionvibe.entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {
    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);

    ProductDetail toProductDetail(ProductDetailCreationRequest request);

    ProductDetailDto toProductDetailDto(ProductDetail productDetail);

    void updateProductDetail(@MappingTarget ProductDetail productDetail,ProductDetailUpdateRequest request);
}
