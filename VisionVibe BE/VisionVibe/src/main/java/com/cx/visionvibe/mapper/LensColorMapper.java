package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.LensColorDto;
import com.cx.visionvibe.dto.request.LensColorCreationRequest;
import com.cx.visionvibe.dto.request.LensColorUpdateRequest;
import com.cx.visionvibe.dto.response.LensColorResponse;
import com.cx.visionvibe.entity.LensColor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LensColorMapper {
    LensColorResponse toLensColorResponse(LensColor lensColor);

    LensColor toLensColor(LensColorCreationRequest request);

    void updateLensColor(@MappingTarget LensColor lensColor, LensColorUpdateRequest request);

    LensColorDto toLensColorDto(LensColor lensColor);
}
