package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.ProductDto;
import com.cx.visionvibe.dto.request.ProductCreationRequest;
import com.cx.visionvibe.dto.request.ProductUpdateRequest;
import com.cx.visionvibe.dto.response.ProductResponse;
import com.cx.visionvibe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "promotions", ignore = true)
    Product toProduct(ProductCreationRequest request);

    ProductDto toProductDto(Product product);

    @Mapping(target = "promotions", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
