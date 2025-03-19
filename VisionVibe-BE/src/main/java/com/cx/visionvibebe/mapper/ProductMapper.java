package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductDto;
import com.cx.visionvibebe.dto.request.CreateNewProductRequest;
import com.cx.visionvibebe.dto.request.UpdateProductRequest;
import com.cx.visionvibebe.dto.response.ProductResponse;
import com.cx.visionvibebe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    Product toProduct(CreateNewProductRequest request);

    ProductDto toProductDto(Product product);

    void updateProduct(UpdateProductRequest request, @MappingTarget Product product);
}
