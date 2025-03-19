package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewProductRequest;
import com.cx.visionvibebe.dto.request.UpdateProductRequest;
import com.cx.visionvibebe.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<ProductResponse> findAllProductByProductSubCategoryName(String productSubCategoryName, int page, int size);

    List<ProductResponse> findAllProductByProductSubCategoryId(Long id);

    ProductResponse findProductByProductName(String productName);

    ProductResponse findProductById(Long id);

    ProductResponse createNewProduct(CreateNewProductRequest request) throws IOException;

    ProductResponse updateProductById(UpdateProductRequest request, Long id) throws IOException;

    void deleteProductById(Long id);
}
