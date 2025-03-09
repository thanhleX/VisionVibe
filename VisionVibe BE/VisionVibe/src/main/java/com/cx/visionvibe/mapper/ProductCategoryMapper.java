package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.ProductCategoryDto;
import com.cx.visionvibe.dto.request.ProductCategoryCreationRequest;
import com.cx.visionvibe.dto.request.ProductCategoryUpdateRequest;
import com.cx.visionvibe.dto.response.ProductCategoryResponse;
import com.cx.visionvibe.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryResponse toProductCategoryResponse(ProductCategory productCategory);

    ProductCategory toProductCategory(ProductCategoryCreationRequest request);

    ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);

    void updateProductCategory(@MappingTarget ProductCategory productCategory, ProductCategoryUpdateRequest request);
}
