package com.cx.visionvibe.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionCreationRequest {
    String name;
    String description;
    Double discountValue;
    LocalDateTime startDate;
    LocalDateTime endDate;
    MultipartFile image;
    List<Long> productId;
}
