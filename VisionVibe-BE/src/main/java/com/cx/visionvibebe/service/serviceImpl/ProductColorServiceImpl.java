package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.CreateNewProductColorRequest;
import com.cx.visionvibebe.dto.response.ProductColorResponse;
import com.cx.visionvibebe.mapper.ProductColorMapper;
import com.cx.visionvibebe.repository.ProductColorRepository;
import com.cx.visionvibebe.service.ProductColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductColorServiceImpl implements ProductColorService {
    private final ProductColorRepository productColorRepository;
    private final ProductColorMapper productColorMapper;

    @Override
    public List<ProductColorResponse> getAllColors() {
        return productColorRepository.findAll().stream().map(productColorMapper::toProductColorResponse).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public ProductColorResponse addNewColor(CreateNewProductColorRequest request) {
        var productColor = productColorMapper.toProductColor(request);
        return productColorMapper.toProductColorResponse(productColorRepository.save(productColor));
    }
}
