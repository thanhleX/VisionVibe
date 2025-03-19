package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.PromotionDto;
import com.cx.visionvibebe.dto.request.CreateNewPromotionRequest;
import com.cx.visionvibebe.dto.response.PromotionResponse;
import com.cx.visionvibebe.dto.response.UpdatePromotionRequest;
import com.cx.visionvibebe.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    PromotionResponse toPromotionResponse(Promotion promotion);

    Promotion toPromotion(CreateNewPromotionRequest request);

    PromotionDto toPromotionDto(Promotion promotion);

    void updatePromotion(UpdatePromotionRequest request, @MappingTarget Promotion promotion);
}
