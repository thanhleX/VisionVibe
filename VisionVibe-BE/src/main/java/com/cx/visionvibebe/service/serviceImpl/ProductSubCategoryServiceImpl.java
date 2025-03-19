package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewProductSubCategoryRequest;
import com.cx.visionvibebe.dto.request.UpdateSubCategoryRequest;
import com.cx.visionvibebe.dto.response.ProductSubCategoryResponse;
import com.cx.visionvibebe.entity.ProductSubCategory;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.ProductCategoryMapper;
import com.cx.visionvibebe.mapper.ProductSubCategoryMapper;
import com.cx.visionvibebe.repository.ProductCategoryRepository;
import com.cx.visionvibebe.repository.ProductSubCategoryRepository;
import com.cx.visionvibebe.service.ProductSubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService {
    private final ProductSubCategoryRepository productSubCategoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final ProductSubCategoryMapper productSubCategoryMapper;
    private final ProductCategoryMapper productCategoryMapper;

    private final UploadService uploadService;

    @Override
    public Page<ProductSubCategoryResponse> findProductSubCategoriesByProductCategoryIdWithPagination(Long productCategoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productSubCategoryRepository.findAllProductSubCategoryByProductCategoryId(productCategoryId, pageable).map(this::toProductSubCategoryResponse);
    }

    @Override
    public ProductSubCategoryResponse findProductSubCategoryById(Long id) {
        var productSubCategory = productSubCategoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_SUB_CATEGORY_ID_NOT_FOUND));
        return toProductSubCategoryResponse(productSubCategory);
    }

    @Override
    public List<ProductSubCategoryResponse> findProductSubCategoryByProductCategoryName(String categoryName) {
        return productSubCategoryRepository.findByCategoryName(categoryName).stream().map(this::toProductSubCategoryResponse).toList();
    }

    private ProductSubCategoryResponse toProductSubCategoryResponse(ProductSubCategory productSubCategory) {
        ProductSubCategoryResponse categoryResponse = productSubCategoryMapper.toSubCategoryResponse(productSubCategory);
        categoryResponse.setProductCategoryDto(productCategoryMapper.toProductCategoryDto(productSubCategory.getProductCategory()));

        return categoryResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductSubCategoryResponse addNewProductSubCategory(CreateNewProductSubCategoryRequest request) throws IOException {
        ProductSubCategory productSubCategory = productSubCategoryMapper.toSubcategory(request);

        var category = productCategoryRepository.findById(request.getProductCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_ID_NOT_FOUND));

        productSubCategory.setProductCategory(category);
        productSubCategory.setIsActive(true);

        uploadService.uploadThumbnail(productSubCategory, request.getThumbnail());

        return this.toProductSubCategoryResponse(productSubCategoryRepository.save(productSubCategory));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public ProductSubCategoryResponse updateProductSubCategoryById(UpdateSubCategoryRequest request, Long id) throws IOException {
        ProductSubCategory productSubCategory = productSubCategoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_SUB_CATEGORY_ID_NOT_FOUND));
        productSubCategoryMapper.updateProductSubCategoryByRequest(request, productSubCategory);

        if (request.getThumbnail() != null) {
            uploadService.deleteThumbnail(productSubCategory);
            uploadService.uploadThumbnail(productSubCategory, request.getThumbnail());
        }

        return toProductSubCategoryResponse(productSubCategoryRepository.save(productSubCategory));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public void deleteProductSubCategoryById(Long id) {
        ProductSubCategory productSubCategory = productSubCategoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_SUB_CATEGORY_ID_NOT_FOUND));
        productSubCategory.setIsActive(false);
        productSubCategoryRepository.save(productSubCategory);
    }
}
