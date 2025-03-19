package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewPromotionRequest;
import com.cx.visionvibebe.dto.response.PromotionResponse;
import com.cx.visionvibebe.dto.response.UpdatePromotionRequest;
import com.cx.visionvibebe.entity.Product;
import com.cx.visionvibebe.entity.Promotion;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.PromotionMapper;
import com.cx.visionvibebe.repository.ProductRepository;
import com.cx.visionvibebe.repository.PromotionRepository;
import com.cx.visionvibebe.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;

    private final UploadService uploadService;

    @Override
    public List<PromotionResponse> findAllPromotions() {
        return promotionRepository.findAllPromotion().stream()
                .map(this::toPromotionResponse).toList();
    }

    @Override
    public PromotionResponse findPromotionById(Long id) {
        return toPromotionResponse(promotionRepository.findPromotionById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND)));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public PromotionResponse addNewPromotion(CreateNewPromotionRequest request) throws IOException {
        Promotion promotion = promotionMapper.toPromotion(request);

        // find and map product to promotion
        List<Product> products = productRepository.findAllById(request.getProductId());
        products.forEach(product -> product.setPromotion(promotion));

        promotion.setProducts(products);
        promotion.setIsActive(true);

        uploadService.uploadThumbnail(promotion, request.getThumbnail());
        return toPromotionResponse(promotionRepository.save(promotion));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public PromotionResponse updatePromotion(Long id, UpdatePromotionRequest request) throws IOException {
        var promotion = promotionRepository.findPromotionById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND));

        promotionMapper.updatePromotion(request, promotion);

        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            // remove old products
            promotion.getProducts().forEach(product -> product.setPromotion(null));
            promotion.getProducts().clear();

            // add new products
            List<Product> products = productRepository.findAllById(request.getProductIds());
            products.forEach(product -> product.setPromotion(promotion));
            promotion.setProducts(products);
        }

        if (request.getThumbnail() != null)
            uploadService.uploadThumbnail(promotion, request.getThumbnail());

        return toPromotionResponse(promotionRepository.save(promotion));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public void deletePromotionById(Long id) {
        Promotion promotion = promotionRepository.findPromotionById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_ID_NOT_FOUND));
        promotion.setIsActive(false);
        promotionRepository.save(promotion);
    }

    private PromotionResponse toPromotionResponse(Promotion promotion) {
        return promotionMapper.toPromotionResponse(promotion);
    }
}
