package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.ProductDetailCreationRequest;
import com.cx.visionvibe.dto.request.ProductDetailUpdateRequest;
import com.cx.visionvibe.dto.response.ProductDetailResponse;
import com.cx.visionvibe.dto.response.ProductImageResponse;
import com.cx.visionvibe.entity.*;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.*;
import com.cx.visionvibe.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDetailService {
    ProductDetailRepository productDetailRepository;
    ProductRepository productRepository;
    FrameColorRepository frameColorRepository;
    LensColorRepository lensColorRepository;
    FrameMaterialRepository frameMaterialRepository;
    LensMaterialRepository lensMaterialRepository;

    ProductDetailMapper productDetailMapper;
    ProductMapper productMapper;
    FrameColorMapper frameColorMapper;
    LensColorMapper lensColorMapper;
    FrameMaterialMapper frameMaterialMapper;
    LensMaterialMapper lensMaterialMapper;
    ProductImageMapper productImageMapper;

    ProductImageService productImageService;

    public List<ProductDetailResponse> findAllProductDetailByProductName(String productName) {
        return productDetailRepository.findProductDetailByProductName(productName)
                .stream().map(this::toProductDetailResponse).toList();
    }

    public ProductDetailResponse findProductDetailById(Long id) {
        var productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));

        return toProductDetailResponse(productDetail);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductDetailResponse createProductDetailByProductName(ProductDetailCreationRequest request) throws IOException {
        ProductDetail productDetail = productDetailMapper.toProductDetail(request);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));

        productDetail.setProduct(product);

        setProductDetailAttributes(productDetail, request.getFrameColorId(), request.getLensColorId(),
                request.getLensMaterialId(), request.getFrameColorId());

        productDetail.setCreatedAt(LocalDateTime.now());
        productDetail.setIsActive(true);

        productDetailRepository.findProductDetailByProductName(product.getProductName()).forEach((res) -> {
            if (res.getFrameColor() == productDetail.getFrameColor()
                    && res.getLensColor() == productDetail.getLensColor()
                    && res.getFrameMaterial() == productDetail.getFrameMaterial()
                    && res.getLensMaterial() == productDetail.getLensMaterial()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_DUPLICATED);
            }
        });

        var newProductDetail = productDetailRepository.save(productDetail);
        var productDetailResponse = this.toProductDetailResponse(newProductDetail);

        List<ProductImageResponse> productImageResponseList = productImageService.uploadProductImagesToCloudinary(newProductDetail, request.getImages());
        productDetailResponse.setProductImageResponseList(productImageResponseList);

        return productDetailResponse;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public ProductDetailResponse updateProductDetailById(Long id, ProductDetailUpdateRequest request) throws IOException {
        var productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));

        setProductDetailAttributes(productDetail, request.getFrameColorId(), request.getLensColorId(),
                request.getLensMaterialId(), request.getFrameColorId());

        productDetailRepository.findProductDetailByProductName(productDetail.getProduct().getProductName()).forEach((res) -> {
            if (res.getFrameColor() == productDetail.getFrameColor()
                    && res.getLensColor() == productDetail.getLensColor()
                    && res.getFrameMaterial() == productDetail.getFrameMaterial()
                    && res.getLensMaterial() == productDetail.getLensMaterial()) {
                throw new AppException(ErrorCode.PRODUCT_DETAIL_DUPLICATED);
            }
        });

        productDetail.setUpdatedAt(LocalDateTime.now());

        var productDetailResponse = toProductDetailResponse(productDetailRepository.save(productDetail));

        if (request.getImages() != null) {
            List<ProductImageResponse> productImageResponseList = productImageService.uploadProductImagesToCloudinary(productDetail, request.getImages());
            productImageResponseList.forEach(productImageResponse -> productDetailResponse.getProductImageResponseList().add(productImageResponse));
        }

        return productDetailResponse;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public void deleteProductDetailById(Long id) {
        var productDetail = productDetailRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));
        productDetail.setIsActive(false);
        productDetailRepository.save(productDetail);
    }

    private void setProductDetailAttributes(ProductDetail productDetail, Long frameColorId, Long lensColorId, Long frameMaterialId, Long lensMaterialId) {
        if (frameColorId != null) {
            FrameColor frameColor = frameColorRepository.findByFrameColorId(frameColorId)
                    .orElseThrow(() -> new AppException(ErrorCode.FRAME_COLOR_ID_NOT_FOUND));
            productDetail.setFrameColor(frameColor);
        }

        if (lensColorId != null) {
            LensColor lensColor = lensColorRepository.findByLensColorId(lensColorId)
                    .orElseThrow(() -> new AppException(ErrorCode.LENS_COLOR_ID_NOT_FOUND));
            productDetail.setLensColor(lensColor);
        }

        if (frameMaterialId != null) {
            FrameMaterial frameMaterial = frameMaterialRepository.findByFrameMaterialId(frameMaterialId)
                    .orElseThrow(() -> new AppException(ErrorCode.FRAME_MATERIAL_ID_NOT_FOUND));
            productDetail.setFrameMaterial(frameMaterial);
        }

        if (lensMaterialId != null) {
            LensMaterial lensMaterial = lensMaterialRepository.findByLensMaterialId(lensMaterialId)
                    .orElseThrow(() -> new AppException(ErrorCode.LENS_MATERIAL_ID_NOT_FOUND));
            productDetail.setLensMaterial(lensMaterial);
        }
    }


    public ProductDetailResponse toProductDetailResponse(ProductDetail productDetail) {
        var productDetailResponse = productDetailMapper.toProductDetailResponse(productDetail);

        productDetailResponse.setProductDto(productMapper.toProductDto(productDetail.getProduct()));
        productDetailResponse.setFrameColorDto(frameColorMapper.toFrameColorDto(productDetail.getFrameColor()));
        productDetailResponse.setLensColorDto(lensColorMapper.toLensColorDto(productDetail.getLensColor()));
        productDetailResponse.setFrameMaterialDto(frameMaterialMapper.toFrameMaterialDto(productDetail.getFrameMaterial()));
        productDetailResponse.setLensMaterialDto(lensMaterialMapper.toLensMaterialDto(productDetail.getLensMaterial()));
        productDetailResponse.setProductImageResponseList(productImageMapper.toProductImageResponseList(productDetail.getProductImages()));

        if (productDetailResponse.getProductImageResponseList() != null)
            productDetailResponse.getProductImageResponseList()
                    .forEach(productImageResponse -> productImageResponse
                            .setProductDetailId(productDetailResponse.getId()));

        return productDetailResponse;
    }
}
