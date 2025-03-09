package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.request.BrandCreationRequest;
import com.cx.visionvibe.dto.request.BrandUpdateRequest;
import com.cx.visionvibe.dto.response.BrandResponse;
import com.cx.visionvibe.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandCreationRequest request);

    BrandResponse toBrandResponse(Brand brand);

    void updateBrand(@MappingTarget Brand brand, BrandUpdateRequest request);
}
