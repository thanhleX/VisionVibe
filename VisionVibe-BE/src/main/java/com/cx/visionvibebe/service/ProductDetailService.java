package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewProductDetailRequest;
import com.cx.visionvibebe.dto.request.UpdateProductDetailRequest;
import com.cx.visionvibebe.dto.response.ProductDetailResponse;

import java.io.IOException;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> findAllProductDetailByProductName(String productName);

    ProductDetailResponse addNewProductDetailByProductName(CreateNewProductDetailRequest request) throws IOException;

    ProductDetailResponse updateProductDetailById(Long id, UpdateProductDetailRequest request) throws IOException;

    ProductDetailResponse findProductDetailById(Long id);

    void deleteProductDetailById(Long id);
}
