package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.request.PromotionCreationRequest;
import com.cx.visionvibe.dto.PromotionDto;
import com.cx.visionvibe.dto.request.PromotionUpdateRequest;
import com.cx.visionvibe.dto.response.PromotionResponse;
import com.cx.visionvibe.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    PromotionDto toPromotionDto(Promotion promotion);

    PromotionResponse toPromotionResponse(Promotion promotion);

    Promotion toPromotion(PromotionCreationRequest request);

    void updatePromotion(@MappingTarget Promotion promotion, PromotionUpdateRequest request);
}
