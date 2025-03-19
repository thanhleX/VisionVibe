package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductSubCategoryDto;
import com.cx.visionvibebe.entity.ProductSubCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSubCategoryDtoMapper {
    ProductSubCategoryDto toProductSubCategoryDto(ProductSubCategory productSubCategory);
}
