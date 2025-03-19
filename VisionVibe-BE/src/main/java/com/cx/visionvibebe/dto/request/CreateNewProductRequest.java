package com.cx.visionvibebe.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewProductRequest {
    private String name;
    private Double price;
    private MultipartFile thumbnail;
    private String description;
    private Long productSubCategoryId;
}
