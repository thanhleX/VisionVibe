package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewBlogRequest;
import com.cx.visionvibebe.dto.request.UpdateBlogRequest;
import com.cx.visionvibebe.dto.response.BlogResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface BlogService {

    Page<BlogResponse> findAllBlogsWithPaginationByBlogCategoryId(Long blockCategoryId, int page, int size);

    BlogResponse findBlogById(Long id);

    Page<BlogResponse> findAllCarouselBlog(int page, int size);

    BlogResponse addNewBlog(CreateNewBlogRequest request) throws IOException;

    BlogResponse changeCarousel(Long id);

    Page<BlogResponse> findAllBlogs(int page, int size);

    BlogResponse updateBlogById(Long blogId, UpdateBlogRequest request) throws IOException;

    void deleteBlogById(Long blogId);
}
