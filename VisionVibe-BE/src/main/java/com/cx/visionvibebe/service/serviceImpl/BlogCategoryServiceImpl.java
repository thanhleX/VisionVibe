package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewBlogCategoryRequest;
import com.cx.visionvibebe.dto.response.BlogCategoryResponse;
import com.cx.visionvibebe.entity.BlogCategory;
import com.cx.visionvibebe.mapper.BlogCategoryMapper;
import com.cx.visionvibebe.repository.BlogCategoryRepository;
import com.cx.visionvibebe.service.BlogCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogCategoryServiceImpl implements BlogCategoryService {
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogCategoryMapper blogCategoryMapper;

    @Override
    public Set<BlogCategoryResponse> findAllBlogCategories() {
        return blogCategoryRepository.findAll().stream().map(blogCategoryMapper::toBlogCategoryResponse).collect(Collectors.toSet());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WRITER')")
    public BlogCategoryResponse addNewBlogCategory(CreateNewBlogCategoryRequest request) {
        BlogCategory blogCategory = blogCategoryMapper.toBlogCategory(request);
        return blogCategoryMapper.toBlogCategoryResponse(blogCategoryRepository.save(blogCategory));
    }
}
