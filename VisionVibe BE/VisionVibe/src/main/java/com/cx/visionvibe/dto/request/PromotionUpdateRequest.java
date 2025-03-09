package com.cx.visionvibe.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PromotionUpdateRequest {
    String name;
    String description;
    Double discountValue;
    LocalDateTime startDate;
    LocalDateTime endDate;
    MultipartFile image;
    List<Long> productId;
}
