package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductSubCategoryDto;
import com.cx.visionvibebe.dto.request.CreateNewProductSubCategoryRequest;
import com.cx.visionvibebe.dto.request.UpdateSubCategoryRequest;
import com.cx.visionvibebe.dto.response.ProductSubCategoryResponse;
import com.cx.visionvibebe.entity.ProductSubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductSubCategoryMapper {
    ProductSubCategory toSubcategory(CreateNewProductSubCategoryRequest request);

    ProductSubCategoryResponse toSubCategoryResponse(ProductSubCategory productSubCategory);

    ProductSubCategoryDto toProductSubCategoryDto(ProductSubCategory productSubCategory);

    void updateProductSubCategoryByRequest(UpdateSubCategoryRequest request, @MappingTarget ProductSubCategory productSubCategory);
}
