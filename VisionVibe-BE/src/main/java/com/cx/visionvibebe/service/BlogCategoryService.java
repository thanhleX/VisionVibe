package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewBlogCategoryRequest;
import com.cx.visionvibebe.dto.response.BlogCategoryResponse;

import java.util.Set;

public interface BlogCategoryService {
    Set<BlogCategoryResponse> findAllBlogCategories();

    BlogCategoryResponse addNewBlogCategory(CreateNewBlogCategoryRequest request);

}
