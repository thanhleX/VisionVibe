package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.PromotionCreationRequest;
import com.cx.visionvibe.dto.request.PromotionUpdateRequest;
import com.cx.visionvibe.dto.response.ProductResponse;
import com.cx.visionvibe.dto.response.PromotionResponse;
import com.cx.visionvibe.entity.Product;
import com.cx.visionvibe.entity.Promotion;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.ProductMapper;
import com.cx.visionvibe.mapper.PromotionMapper;
import com.cx.visionvibe.repository.ProductRepository;
import com.cx.visionvibe.repository.PromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionService {
    PromotionRepository promotionRepository;
    ProductRepository productRepository;

    PromotionMapper promotionMapper;
    ProductMapper productMapper;

    UploadService uploadService;

    public List<PromotionResponse> findAllPromotions() {
        return promotionRepository.findAll().stream().map(this::toPromotionResponse).toList();
    }

    public PromotionResponse findPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND));
        return toPromotionResponse(promotion);
    }

    public PromotionResponse createPromotion(PromotionCreationRequest request) throws IOException {
        Promotion promotion = promotionMapper.toPromotion(request);
        promotion.setIsActive(true);
        promotion.setCreatedAt(LocalDateTime.now());

        List<Product> products = new ArrayList<>();
        for (Long productId : request.getProductId()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
            products.add(product);
        }

        promotion.setProducts(products);

        uploadService.uploadThumbnail(promotion, request.getImage());

        return toPromotionResponse(promotionRepository.save(promotion));
    }

    public PromotionResponse updatePromotion(Long id, PromotionUpdateRequest request) throws IOException {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND));
        promotionMapper.updatePromotion(promotion, request);
        promotion.setUpdatedAt(LocalDateTime.now());

        List<Product> products = new ArrayList<>();
        for (Long productId : request.getProductId()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
            products.add(product);
        }

        promotion.setProducts(products);

        if (request.getImage() != null && !request.getImage().isEmpty())
            uploadService.uploadThumbnail(promotion, request.getImage());

        return toPromotionResponse(promotionRepository.save(promotion));
    }

    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND));
        promotion.setIsActive(false);

        promotionRepository.save(promotion);
    }

    private PromotionResponse toPromotionResponse(Promotion promotion) {
        PromotionResponse promotionResponse = promotionMapper.toPromotionResponse(promotion);
        promotionResponse.setProductDtoList(promotion.getProducts().stream().map(productMapper::toProductDto).toList());
        return promotionResponse;
    }
}
