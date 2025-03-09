package com.cx.visionvibe.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cx.visionvibe.dto.response.ProductImageResponse;
import com.cx.visionvibe.entity.ProductDetail;
import com.cx.visionvibe.entity.ProductImage;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.ProductImageMapper;
import com.cx.visionvibe.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    private final ProductImageMapper productImageMapper;

    private final Cloudinary cloudinary;

    public List<ProductImageResponse> uploadProductImagesToCloudinary(ProductDetail productDetail, List<MultipartFile> images) throws IOException {
        if (images == null || images.isEmpty())
            return null;

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(image.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
            String publicId = uploadResult.get("public_id").toString();

            ProductImage productImage = ProductImage.builder()
                    .url(url)
                    .publicId(publicId)
                    .productDetail(productDetail)
                    .build();

            productImages.add(productImage);
        }

        List<ProductImage> savedProductImages = productImageRepository.saveAll(productImages);

        return savedProductImages.stream().map(this::toProductImageresponse).toList();
    }

    public String deleteImage(Long id) throws IOException {
        var productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_ID_NOT_FOUND));

        Map<?, ?> destroyResult = cloudinary.uploader().destroy(productImage.getPublicId(), ObjectUtils.emptyMap());
        productImageRepository.delete(productImage);

        return destroyResult.get("result").toString();
    }

    private ProductImageResponse toProductImageresponse(ProductImage productImage) {
        ProductImageResponse productImageResponse = productImageMapper.toProductImageResponse(productImage);

        productImageResponse.setProductDetailId(productImage.getProductDetail().getId());

        return productImageResponse;
    }
}
