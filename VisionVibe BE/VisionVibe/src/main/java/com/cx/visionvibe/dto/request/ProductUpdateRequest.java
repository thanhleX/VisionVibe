package com.cx.visionvibe.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ProductUpdateRequest {
    String productName;
    Double price;
    MultipartFile thumbnail;
    Long brandId;
    List<Long> promotions;
}
