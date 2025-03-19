package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductDetailDto;
import com.cx.visionvibebe.dto.request.CreateNewProductDetailRequest;
import com.cx.visionvibebe.dto.request.UpdateProductDetailRequest;
import com.cx.visionvibebe.dto.response.ProductDetailResponse;
import com.cx.visionvibebe.entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {
    ProductDetailResponse toProductDetailResponse(ProductDetail productDetail);

    ProductDetail toProductDetail(CreateNewProductDetailRequest request);

    ProductDetailDto toProductDetailDto(ProductDetail productDetail);

    void updateProductDetail(UpdateProductDetailRequest request, @MappingTarget ProductDetail productDetail);
}
