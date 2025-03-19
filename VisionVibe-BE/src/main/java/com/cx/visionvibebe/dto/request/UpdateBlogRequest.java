package com.cx.visionvibebe.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBlogRequest {
    private String title;
    private String subTitle;
    private String description;
    private MultipartFile thumbnail;
}
