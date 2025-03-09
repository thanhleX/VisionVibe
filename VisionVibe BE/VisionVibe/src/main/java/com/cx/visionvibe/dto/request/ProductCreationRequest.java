package com.cx.visionvibe.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    String productName;
    Double price;
    MultipartFile thumbnail;
    Long productCategoryId;
    Long brandId;
    List<Long> promotions;
}
