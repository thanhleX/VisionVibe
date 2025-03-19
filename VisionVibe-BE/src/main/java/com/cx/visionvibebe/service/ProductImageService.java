package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.response.ProductImageResponse;
import com.cx.visionvibebe.entity.ProductDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {

    List<ProductImageResponse> uploadProductImagesToCloudinary(ProductDetail productDetail, List<MultipartFile> images) throws IOException;

    String deleteImage(Long id) throws IOException;
}
