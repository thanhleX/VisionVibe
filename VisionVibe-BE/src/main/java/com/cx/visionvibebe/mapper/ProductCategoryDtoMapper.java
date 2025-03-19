package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductCategoryDto;
import com.cx.visionvibebe.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryDtoMapper {
    ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
}
