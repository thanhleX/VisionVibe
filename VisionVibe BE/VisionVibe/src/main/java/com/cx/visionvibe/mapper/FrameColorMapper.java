package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.FrameColorDto;
import com.cx.visionvibe.dto.request.FrameColorCreationRequest;
import com.cx.visionvibe.dto.request.FrameColorUpdateRequest;
import com.cx.visionvibe.dto.response.FrameColorResponse;
import com.cx.visionvibe.entity.FrameColor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FrameColorMapper {
    FrameColorResponse toFrameColorResponse(FrameColor frameColor);

    FrameColor toFrameColor(FrameColorCreationRequest request);

    FrameColorDto toFrameColorDto(FrameColor frameColor);

    void updateFrameColor(@MappingTarget FrameColor frameColor, FrameColorUpdateRequest request);
}
