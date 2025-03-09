package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.FrameMaterialDto;
import com.cx.visionvibe.dto.request.FrameMaterialCreationRequest;
import com.cx.visionvibe.dto.request.FrameMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.FrameMaterialResponse;
import com.cx.visionvibe.entity.FrameMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FrameMaterialMapper {
    FrameMaterialResponse toFrameMaterialResponse(FrameMaterial frameMaterial);

    FrameMaterial toFrameMaterial(FrameMaterialCreationRequest request);

    void updateFrameMaterial(@MappingTarget FrameMaterial frameMaterial, FrameMaterialUpdateRequest request);

    FrameMaterialDto toFrameMaterialDto(FrameMaterial frameMaterial);
}
