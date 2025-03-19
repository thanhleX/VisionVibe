package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductColorDto;
import com.cx.visionvibebe.dto.request.CreateNewProductColorRequest;
import com.cx.visionvibebe.dto.response.ProductColorResponse;
import com.cx.visionvibebe.entity.ProductColor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductColorMapper {
    ProductColorResponse toProductColorResponse(ProductColor productColor);

    ProductColor toProductColor(CreateNewProductColorRequest request);

    List<ProductColorDto> toProductColorDtoList(List<ProductColor> productColor);

    ProductColorDto toProductColorDto(ProductColor productColor);
}
