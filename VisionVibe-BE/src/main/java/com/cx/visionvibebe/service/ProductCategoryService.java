package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewProductCategoryRequest;
import com.cx.visionvibebe.dto.response.ProductCategoryResponse;
import com.cx.visionvibebe.dto.response.ProductCategoryResponseSimple;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductCategoryService {
    Page<ProductCategoryResponseSimple> findAllProductCategoriesWithPagination(int page, int size);

    List<ProductCategoryResponse> findAllProductCategories();

    ProductCategoryResponseSimple addNewProductCategory(CreateNewProductCategoryRequest request);

    void deleteProductCategory(Long id);
}
