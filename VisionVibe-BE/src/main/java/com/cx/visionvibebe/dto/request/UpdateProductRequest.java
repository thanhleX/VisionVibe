package com.cx.visionvibebe.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private String name;
    private Double price;
    private MultipartFile thumbnail;
    private String description;
}
