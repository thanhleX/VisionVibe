package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewProductMaterialRequest;
import com.cx.visionvibebe.dto.request.UpdateProductMaterialRequest;
import com.cx.visionvibebe.dto.response.ProductMaterialResponse;

import java.util.List;

public interface ProductMaterialService {
    List<ProductMaterialResponse> findAllProductMaterials();

    ProductMaterialResponse findProductMaterialById(Long id);

    ProductMaterialResponse addNewProductMaterial(CreateNewProductMaterialRequest request);

    ProductMaterialResponse updateProductMaterialById(UpdateProductMaterialRequest request, Long id);

    void deleteProductMaterialById(Long id);
}
