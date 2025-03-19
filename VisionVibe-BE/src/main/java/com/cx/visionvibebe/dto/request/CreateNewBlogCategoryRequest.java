package com.cx.visionvibebe.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewBlogCategoryRequest {
    private String name;
}
