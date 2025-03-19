package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductCategoryDto;
import com.cx.visionvibebe.dto.request.CreateNewProductCategoryRequest;
import com.cx.visionvibebe.dto.response.ProductCategoryResponse;
import com.cx.visionvibebe.dto.response.ProductCategoryResponseSimple;
import com.cx.visionvibebe.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryResponseSimple toProductCategoryResponseSimple(ProductCategory productCategory);

    ProductCategoryResponse toProductCategoryResponse(ProductCategory productCategory);

    ProductCategory toProductCategory(CreateNewProductCategoryRequest request);

    ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
}
