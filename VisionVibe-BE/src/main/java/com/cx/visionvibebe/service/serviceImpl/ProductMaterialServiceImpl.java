package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewProductMaterialRequest;
import com.cx.visionvibebe.dto.request.UpdateProductMaterialRequest;
import com.cx.visionvibebe.dto.response.ProductMaterialResponse;
import com.cx.visionvibebe.entity.ProductMaterial;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.ProductMaterialMapper;
import com.cx.visionvibebe.repository.ProductMaterialRepository;
import com.cx.visionvibebe.service.ProductMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMaterialServiceImpl implements ProductMaterialService {
    private final ProductMaterialRepository productMaterialRepository;

    private final ProductMaterialMapper productMaterialMapper;

    @Override
    public List<ProductMaterialResponse> findAllProductMaterials() {
        return productMaterialRepository.findAll().stream().map(this::toProductMaterialResponse).toList();
    }

    @Override
    public ProductMaterialResponse findProductMaterialById(Long id) {
        return toProductMaterialResponse(productMaterialRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MATERIAL_ID_NOT_FOUND)));
    }

    @Override
    public ProductMaterialResponse addNewProductMaterial(CreateNewProductMaterialRequest request) {
        var ProductMaterial = productMaterialMapper.toProductMaterial(request);
        return toProductMaterialResponse(productMaterialRepository.save(ProductMaterial));
    }

    @Override
    public ProductMaterialResponse updateProductMaterialById(UpdateProductMaterialRequest request, Long id) {
        var ProductMaterial = productMaterialRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MATERIAL_ID_NOT_FOUND));
        productMaterialMapper.updateProductMaterialByRequest(request, ProductMaterial);
        return toProductMaterialResponse(productMaterialRepository.save(ProductMaterial));
    }

    @Override
    public void deleteProductMaterialById(Long id) {
        var ProductMaterial = productMaterialRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MATERIAL_ID_NOT_FOUND));
        productMaterialRepository.delete(ProductMaterial);
    }

    private ProductMaterialResponse toProductMaterialResponse(ProductMaterial productMaterial) {
        return productMaterialMapper.toProductMaterialResponse(productMaterial);
    }
}
