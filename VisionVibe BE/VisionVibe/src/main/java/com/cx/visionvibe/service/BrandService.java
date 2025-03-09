package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.BrandCreationRequest;
import com.cx.visionvibe.dto.request.BrandUpdateRequest;
import com.cx.visionvibe.dto.response.BrandResponse;
import com.cx.visionvibe.entity.Brand;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.BrandMapper;
import com.cx.visionvibe.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    private final UploadService uploadService;

    public List<BrandResponse> findAllBrand() {
        return brandRepository.findAll().stream().map(brandMapper::toBrandResponse).toList();
    }

    public BrandResponse findByBrandId(Long id) {
        return brandMapper.toBrandResponse(brandRepository.findByBrandId(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_ID_NOT_FOUND)));
    }

    public BrandResponse createBrand(BrandCreationRequest request) throws IOException {
        Brand brand = brandMapper.toBrand(request);
        brand.setIsActive(true);
        uploadService.uploadThumbnail(brand, request.getImage());

        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) throws IOException {
        Brand brand = brandRepository.findByBrandId(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_ID_NOT_FOUND));

        brandMapper.updateBrand(brand, request);

        if (request.getImage() != null && !request.getImage().isEmpty())
            uploadService.uploadThumbnail(brand, request.getImage());

        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findByBrandId(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_ID_NOT_FOUND));

        brand.setIsActive(false);
        brandRepository.save(brand);
    }
}
