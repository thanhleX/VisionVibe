package com.cx.visionvibebe.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductDetailRequest {
    private Long colorId;
    private Long materialId;
    private Integer stock;
    private List<MultipartFile> images;
}
