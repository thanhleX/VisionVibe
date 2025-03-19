package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewProductColorRequest;
import com.cx.visionvibebe.dto.response.ProductColorResponse;

import java.util.List;

public interface ProductColorService {
    List<ProductColorResponse> getAllColors();

    ProductColorResponse addNewColor(CreateNewProductColorRequest request);

}
