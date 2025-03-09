package com.cx.visionvibe.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BrandCreationRequest {
    private String name;
    private String description;
    private MultipartFile image;
    private List<Long> productId;
}
