package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewBlogCategoryRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.BlogCategoryResponse;
import com.cx.visionvibebe.service.BlogCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("blog-categories")
@RequiredArgsConstructor
public class BlogCategoryController {
    private final BlogCategoryService blogCategoryService;

    @GetMapping
    public ApiResponse<Set<BlogCategoryResponse>> findAllBlogCategories() {
        return ApiResponse.<Set<BlogCategoryResponse>>builder()
                .result(blogCategoryService.findAllBlogCategories())
                .build();
    }

    @PostMapping
    public ApiResponse<BlogCategoryResponse> addNewBlogCategory(@RequestBody CreateNewBlogCategoryRequest request) {
        return ApiResponse.<BlogCategoryResponse>builder()
                .result(blogCategoryService.addNewBlogCategory(request))
                .build();
    }
}
