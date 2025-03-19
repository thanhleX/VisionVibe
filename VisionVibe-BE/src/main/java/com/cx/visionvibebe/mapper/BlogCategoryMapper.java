package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.request.CreateNewBlogCategoryRequest;
import com.cx.visionvibebe.dto.response.BlogCategoryResponse;
import com.cx.visionvibebe.entity.BlogCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogCategoryMapper {
    BlogCategoryResponse toBlogCategoryResponse(BlogCategory blogCategory);

    BlogCategory toBlogCategory(CreateNewBlogCategoryRequest request);
}
