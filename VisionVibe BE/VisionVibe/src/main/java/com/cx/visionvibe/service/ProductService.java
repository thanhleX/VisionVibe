package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.*;
import com.cx.visionvibe.dto.request.ProductCreationRequest;
import com.cx.visionvibe.dto.request.ProductUpdateRequest;
import com.cx.visionvibe.dto.response.ProductResponse;
import com.cx.visionvibe.entity.FrameColor;
import com.cx.visionvibe.entity.Product;
import com.cx.visionvibe.entity.ProductCategory;
import com.cx.visionvibe.entity.ProductDetail;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.*;
import com.cx.visionvibe.repository.ProductCategoryRepository;
import com.cx.visionvibe.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductCategoryRepository productCategoryRepository;

    ProductMapper productMapper;
    ProductDetailMapper productDetailMapper;
    ProductCategoryMapper productCategoryMapper;
    FrameColorMapper frameColorMapper;
    LensColorMapper lensColorMapper;
    FrameMaterialMapper frameMaterialMapper;
    LensMaterialMapper lensMaterialMapper;
    ProductImageMapper productImageMapper;

    UploadService uploadService;

    public Page<ProductResponse> findAllProductByProductCategoryName(String productCategoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return productRepository.findAllProductsByProductCategoryNameWithPagination(productCategoryName, pageable)
                .map(this::toProductResponse);
    }

    public ProductResponse findProductByProductName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NAME_NOT_FOUND));
        return toProductResponse(product);
    }

    public ProductResponse findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        return toProductResponse(product);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductResponse createProduct(ProductCreationRequest request) throws IOException {
        if (productRepository.findByProductName(request.getProductName()).isPresent())
            throw new AppException(ErrorCode.PRODUCT_NAME_DUPLICATED);

        var product = productMapper.toProduct(request);

        product.setIsActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setProductCategory(productCategoryRepository.findById(request.getProductCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NOT_FOUND)));

        var newProduct = productRepository.save(product);

        uploadService.uploadThumbnail(newProduct, request.getThumbnail());

        return toProductResponse(newProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));

        product.setUpdatedAt(LocalDateTime.now());
        productMapper.updateProduct(product, request);

        if (request.getThumbnail() != null)
            uploadService.uploadThumbnail(product, request.getThumbnail());

        return toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        product.setIsActive(false);
        productRepository.save(product);
    }

    private ProductResponse toProductResponse(Product product) {
        var productResponse = productMapper.toProductResponse(product);

        productResponse.setProductCategoryDto(productCategoryMapper.toProductCategoryDto(product.getProductCategory()));
        if (product.getProductDetails() != null) {
            List<ProductDetailDto> productDetailDtoList = new ArrayList<>();

            for (ProductDetail productDetail : product.getProductDetails()) {
                if (productDetail.getIsActive()) {
                    FrameColorDto frameColorDto = frameColorMapper.toFrameColorDto(productDetail.getFrameColor());
                    LensColorDto lensColorDto = lensColorMapper.toLensColorDto(productDetail.getLensColor());
                    FrameMaterialDto frameMaterialDto = frameMaterialMapper.toFrameMaterialDto(productDetail.getFrameMaterial());
                    LensMaterialDto lensMaterialDto = lensMaterialMapper.toLensMaterialDto(productDetail.getLensMaterial());
                    List<ProductImageDto> productImageDtoList = productImageMapper.toProductImageDtoList(productDetail.getProductImages());
                    ProductDetailDto productDetailDto = productDetailMapper.toProductDetailDto(productDetail);

                    productDetailDto.setIsActive(productDetail.getIsActive());
                    productDetailDto.setFrameColorDto(frameColorDto);
                    productDetailDto.setLensColorDto(lensColorDto);
                    productDetailDto.setFrameMaterialDto(frameMaterialDto);
                    productDetailDto.setLensMaterialDto(lensMaterialDto);
                    productDetailDto.setProductImageDtoList(productImageDtoList);

                    productDetailDtoList.add(productDetailDto);
                }
            }
            productResponse.setProductDetailDtoList(productDetailDtoList);
        }
        return productResponse;
    }
}
