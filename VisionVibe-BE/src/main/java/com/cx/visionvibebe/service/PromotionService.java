package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewPromotionRequest;
import com.cx.visionvibebe.dto.response.PromotionResponse;
import com.cx.visionvibebe.dto.response.UpdatePromotionRequest;

import java.io.IOException;
import java.util.List;

public interface PromotionService {
    List<PromotionResponse> findAllPromotions();

    PromotionResponse findPromotionById(Long id);

    PromotionResponse addNewPromotion(CreateNewPromotionRequest request) throws IOException;

    PromotionResponse updatePromotion(Long id, UpdatePromotionRequest request) throws IOException;

    void deletePromotionById(Long id);
}
