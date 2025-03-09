package com.cx.visionvibe.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailUpdateRequest {
    Integer stock;
    Long frameColorId;
    Long frameMaterialId;
    Long lensColorId;
    Long lensMaterialId;
    List<MultipartFile> images;
}
