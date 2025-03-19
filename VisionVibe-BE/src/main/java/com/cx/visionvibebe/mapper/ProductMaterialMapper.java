package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.ProductMaterialDto;
import com.cx.visionvibebe.dto.request.CreateNewProductMaterialRequest;
import com.cx.visionvibebe.dto.request.UpdateProductMaterialRequest;
import com.cx.visionvibebe.dto.response.ProductMaterialResponse;
import com.cx.visionvibebe.entity.ProductMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMaterialMapper {
    ProductMaterialDto toProductMaterialDto(ProductMaterial productMaterials);

    ProductMaterialResponse toProductMaterialResponse(ProductMaterial productMaterial);

    ProductMaterial toProductMaterial(CreateNewProductMaterialRequest request);

    void updateProductMaterialByRequest(UpdateProductMaterialRequest request, @MappingTarget ProductMaterial productMaterial);
}
