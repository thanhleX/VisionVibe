package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.LensMaterialDto;
import com.cx.visionvibe.dto.request.LensMaterialCreationRequest;
import com.cx.visionvibe.dto.request.LensMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.LensMaterialResponse;
import com.cx.visionvibe.entity.LensMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LensMaterialMapper {
    LensMaterialResponse toLensMaterialResponse(LensMaterial lensMaterial);

    LensMaterial toLensMaterial(LensMaterialCreationRequest request);

    void updateLensMaterial(@MappingTarget LensMaterial lensMaterial, LensMaterialUpdateRequest request);

    LensMaterialDto toLensMaterialDto(LensMaterial lensMaterial);
}
