package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.ProductSubCategoryDto;
import com.cx.visionvibebe.dto.request.CreateNewProductCategoryRequest;
import com.cx.visionvibebe.dto.response.ProductCategoryResponse;
import com.cx.visionvibebe.dto.response.ProductCategoryResponseSimple;
import com.cx.visionvibebe.entity.ProductCategory;
import com.cx.visionvibebe.entity.ProductSubCategory;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.ProductCategoryMapper;
import com.cx.visionvibebe.mapper.ProductSubCategoryMapper;
import com.cx.visionvibebe.repository.ProductCategoryRepository;
import com.cx.visionvibebe.repository.ProductSubCategoryRepository;
import com.cx.visionvibebe.service.ProductCategoryService;
import com.cx.visionvibebe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductService productService;
    private final ProductSubCategoryRepository productSubCategoryRepository;

    private final ProductCategoryMapper productCategoryMapper;
    private final ProductSubCategoryMapper productSubCategoryMapper;

    @Override
    public Page<ProductCategoryResponseSimple> findAllProductCategoriesWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productCategoryRepository.findAll(pageable).map(productCategoryMapper::toProductCategoryResponseSimple);
    }

    @Override
    public List<ProductCategoryResponse> findAllProductCategories() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();

        return productCategoryList.stream().map(productCategory -> {
            ProductCategoryResponse productCategoryResponse = productCategoryMapper.toProductCategoryResponse(productCategory);
            List<ProductSubCategoryDto> productSubCategoryDtoList = productSubCategoryRepository.findAllProductSubCategoryByProductCategoryName(productCategory.getName()).stream().map(this::toProductSubCategoryDto).toList();
            productCategoryResponse.setProductSubCategoryDtoList(productSubCategoryDtoList);
            return productCategoryResponse;
        }).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductCategoryResponseSimple addNewProductCategory(CreateNewProductCategoryRequest request) {
        ProductCategory productCategory = productCategoryMapper.toProductCategory(request);
        productCategory.setIsActive(true);
        return productCategoryMapper.toProductCategoryResponseSimple(productCategoryRepository.save(productCategory));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public void deleteProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NOT_FOUND));
        productCategory.setIsActive(false);
        productCategoryRepository.save(productCategory);
    }

    private ProductSubCategoryDto toProductSubCategoryDto(ProductSubCategory productSubCategory) {
        ProductSubCategoryDto productSubCategoryDto = productSubCategoryMapper.toProductSubCategoryDto(productSubCategory);
        productSubCategoryDto.setProductResponseList(productService.findAllProductByProductSubCategoryId(productSubCategoryDto.getId()));
        return productSubCategoryDto;
    }
}
