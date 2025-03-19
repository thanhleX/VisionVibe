package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewProductDetailRequest;
import com.cx.visionvibebe.dto.request.UpdateProductDetailRequest;
import com.cx.visionvibebe.dto.response.ProductDetailResponse;
import com.cx.visionvibebe.dto.response.ProductImageResponse;
import com.cx.visionvibebe.entity.*;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.*;
import com.cx.visionvibebe.repository.*;
import com.cx.visionvibebe.service.ProductDetailService;
import com.cx.visionvibebe.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final ProductMaterialRepository productMaterialRepository;
    private final ProductColorRepository productColorRepository;

    private final ProductDetailMapper productDetailMapper;
    private final ProductMapper productMapper;
    private final ProductColorMapper productColorMapper;
    private final ProductMaterialMapper productMaterialMapper;
    private final ProductImageMapper productImageMapper;

    private final ProductImageService productImageService;

    @Override
    public List<ProductDetailResponse> findAllProductDetailByProductName(String productName) {
        return productDetailRepository.findProductDetailByProductName(productName).stream()
                .map(this::toProductDetailResponse).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductDetailResponse addNewProductDetailByProductName(CreateNewProductDetailRequest request) throws IOException {
        //create product detail
        ProductDetail productDetail = productDetailMapper.toProductDetail(request);

        //add product
        Product product = productRepository.findProductByProductId(request.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        productDetail.setProduct(product);

        //add material
        if (request.getMaterialId() != null) {
            ProductMaterial productMaterial = productMaterialRepository.findByProductMaterialId(request.getMaterialId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MATERIAL_ID_NOT_FOUND));
            productDetail.setProductMaterial(productMaterial);
        }

        //add color
        ProductColor productColor = productColorRepository.findByProductColorId(request.getColorId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_COLOR_ID_NOT_FOUND));
        productDetail.setProductColor(productColor);

        //set active
        productDetail.setActive(true);

        // if material, color are same, thr error that product already existed
        productDetailRepository.findProductDetailByProductName(product.getName()).forEach((res) -> {
            if (res.getProductMaterial() == productDetail.getProductMaterial() && res.getProductColor() == productDetail.getProductColor()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_DUPLICATED);
            }
        });

        //save product detail to database, change to product detail response
        var newProductDetail = productDetailRepository.save(productDetail);
        var productDetailResponse = this.toProductDetailResponse(newProductDetail);

        //add product image response to product detail response
        List<ProductImageResponse> productImageResponseList = productImageService.uploadProductImagesToCloudinary(newProductDetail, request.getImages());
        productDetailResponse.setProductImageResponseList(productImageResponseList);

        return productDetailResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public ProductDetailResponse updateProductDetailById(Long id, UpdateProductDetailRequest request) throws IOException {
        var productDetail = productDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));
        productDetailMapper.updateProductDetail(request, productDetail);

        //add material
        if (request.getMaterialId() != null) {
            ProductMaterial productMaterial = productMaterialRepository.findByProductMaterialId(request.getMaterialId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MATERIAL_ID_NOT_FOUND));
            productDetail.setProductMaterial(productMaterial);
        }

        //add color
        ProductColor productColor = productColorRepository.findByProductColorId(request.getColorId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_COLOR_ID_NOT_FOUND));
        productDetail.setProductColor(productColor);

        // if material, color are same, thr error that product already existed
        productDetailRepository.findProductDetailByProductName(productDetail.getProduct().getName()).forEach((res) -> {
            if (res != productDetail && res.getProductMaterial() == productDetail.getProductMaterial() && res.getProductColor() == productDetail.getProductColor()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_DUPLICATED);
            }
        });

        //save product detail to db
        var productDetailResponse = toProductDetailResponse(productDetailRepository.save(productDetail));

        //add product image response to product detail response
        if (request.getImages() != null) {
            List<ProductImageResponse> productImageResponseList = productImageService.uploadProductImagesToCloudinary(productDetail, request.getImages());
            productImageResponseList.forEach(productImageResponse -> productDetailResponse.getProductImageResponseList().add(productImageResponse));
        }

        return productDetailResponse;
    }

    @Override
    public ProductDetailResponse findProductDetailById(Long id) {
        var productDetail = productDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));
        return toProductDetailResponse(productDetail);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public void deleteProductDetailById(Long id) {
        var productDetail = productDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));
        productDetail.setActive(false);
        productDetailRepository.save(productDetail);
    }

    public ProductDetailResponse toProductDetailResponse(ProductDetail productDetail) {
        var productDetailResponse = productDetailMapper.toProductDetailResponse(productDetail);
        productDetailResponse.setProductDto(productMapper.toProductDto(productDetail.getProduct()));
        productDetailResponse.setProductColorDto(productColorMapper.toProductColorDto(productDetail.getProductColor()));
        productDetailResponse.setProductMaterialDto(productMaterialMapper.toProductMaterialDto(productDetail.getProductMaterial()));
        productDetailResponse.setProductImageResponseList(productImageMapper.toProductImageResponseList(productDetail.getProductImages()));

        if (productDetailResponse.getProductImageResponseList() != null)
            productDetailResponse.getProductImageResponseList().forEach(productImageResponse -> productImageResponse.setProductDetailId(productDetailResponse.getId()));

        return productDetailResponse;
    }


}
