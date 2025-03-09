package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.ProductCategoryCreationRequest;
import com.cx.visionvibe.dto.request.ProductCategoryUpdateRequest;
import com.cx.visionvibe.dto.response.ProductCategoryResponse;
import com.cx.visionvibe.entity.ProductCategory;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.ProductCategoryMapper;
import com.cx.visionvibe.repository.ProductCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCategoryService {
    ProductCategoryRepository productCategoryRepository;

    ProductCategoryMapper productCategoryMapper;

    public List<ProductCategoryResponse> findAllProductCategories() {
        return productCategoryRepository.findAll().stream()
                .map(productCategoryMapper::toProductCategoryResponse).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductCategoryResponse createProductCategory(ProductCategoryCreationRequest request) {
        ProductCategory productCategory = productCategoryMapper.toProductCategory(request);
        productCategory.setIsActive(true);
        return productCategoryMapper.toProductCategoryResponse(productCategory);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public ProductCategoryResponse updateProductCategory(Long id, ProductCategoryUpdateRequest request) {
        var productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NOT_FOUND));
        productCategoryMapper.updateProductCategory(productCategory, request);

        return productCategoryMapper.toProductCategoryResponse(productCategoryRepository.save(productCategory));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public void deleteProductCategory(Long id) {
        var productCategory = productCategoryRepository.findById(id)
               .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NOT_FOUND));
        productCategory.setIsActive(false);

        productCategoryRepository.save(productCategory);
    }
}
