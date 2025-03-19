package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.request.CreateNewBlogRequest;
import com.cx.visionvibebe.dto.request.UpdateBlogRequest;
import com.cx.visionvibebe.dto.response.BlogResponse;
import com.cx.visionvibebe.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    BlogResponse toBlogResponse(Blog blog);

    Blog toBlog(CreateNewBlogRequest request);

    void updateBlogFromRequest(UpdateBlogRequest request, @MappingTarget Blog blog);
}
