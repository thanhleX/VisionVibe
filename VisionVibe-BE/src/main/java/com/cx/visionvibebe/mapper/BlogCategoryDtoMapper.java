package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.BlogCategoryDto;
import com.cx.visionvibebe.entity.BlogCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogCategoryDtoMapper {
    BlogCategoryDto toBlogCategoryDto(BlogCategory blogCategory);
}
