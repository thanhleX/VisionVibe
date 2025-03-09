package com.cx.visionvibe.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionDto {
    Long id;
    String name;
    String description;
    Double discountValue;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
