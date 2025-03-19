package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewBlogRequest;
import com.cx.visionvibebe.dto.request.UpdateBlogRequest;
import com.cx.visionvibebe.dto.response.BlogResponse;
import com.cx.visionvibebe.entity.Blog;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.BlogCategoryDtoMapper;
import com.cx.visionvibebe.mapper.BlogMapper;
import com.cx.visionvibebe.mapper.UserMapper;
import com.cx.visionvibebe.repository.BlogCategoryRepository;
import com.cx.visionvibebe.repository.BlogRepository;
import com.cx.visionvibebe.repository.UserRepository;
import com.cx.visionvibebe.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogMapper blogMapper;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final BlogCategoryDtoMapper blogCategoryDtoMapper;

    private final UploadService uploadService;

    @Override
    public Page<BlogResponse> findAllBlogsWithPaginationByBlogCategoryId(Long blogCategoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return blogRepository.findAllBlogsByBlogCategoryIdWithPagination(blogCategoryId, pageable).map(this::toBlogResponse);
    }

    @Override
    public BlogResponse findBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BLOG_ID_NOT_FOUND));
        return toBlogResponse(blog);
    }

    @Override
    public Page<BlogResponse> findAllCarouselBlog(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("carouselAt").descending());
        return blogRepository.findAllCarouselBlog(pageable).map(this::toBlogResponse);
    }

    private BlogResponse toBlogResponse(Blog blog) {
        BlogResponse blogResponse = blogMapper.toBlogResponse(blog);
        blogResponse.setUserDto(userMapper.toUserDto(blog.getUser()));
        blogResponse.setBlogCategoryDto(blogCategoryDtoMapper.toBlogCategoryDto(blog.getBlogCategory()));
        return blogResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WRITER')")
    public BlogResponse addNewBlog(CreateNewBlogRequest request) throws IOException {
        var user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));
        var blogCategory = blogCategoryRepository.findById(request.getBlogCategoryId()).orElseThrow(() -> new AppException(ErrorCode.BLOG_CATEGORY_ID_NOT_FOUND));
        Blog blog = blogMapper.toBlog(request);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setIsActive(true);
        blog.setBlogCategory(blogCategory);
        blog.setUser(user);
        blog.setCarouselAt(null);
        uploadService.uploadThumbnail(blog, request.getThumbnail());
        return toBlogResponse(blogRepository.save(blog));
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WRITER')")
    public BlogResponse changeCarousel(Long id) {
        var blog = blogRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BLOG_ID_NOT_FOUND));
        blog.setCarouselAt(LocalDateTime.now());
        return toBlogResponse(blogRepository.save(blog));
    }

    @Override
    public Page<BlogResponse> findAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return blogRepository.findAllBlogsWithPagination(pageable).map(this::toBlogResponse);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'WRITER')")
    public BlogResponse updateBlogById(Long blogId, UpdateBlogRequest request) throws IOException {
        var blog = blogRepository.findById(blogId).orElseThrow(() -> new AppException(ErrorCode.BLOG_ID_NOT_FOUND));
        blogMapper.updateBlogFromRequest(request, blog);
        blog.setUpdatedAt(LocalDateTime.now());

        if (request.getThumbnail() != null) {
            uploadService.uploadThumbnail(blog, request.getThumbnail());
        }
        return toBlogResponse(blogRepository.save(blog));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'WRITER')")
    public void deleteBlogById(Long blogId) {
        var blog = blogRepository.findById(blogId).orElseThrow(() -> new AppException(ErrorCode.BLOG_ID_NOT_FOUND));
        blog.setIsActive(false);
        blogRepository.save(blog);
    }
}
