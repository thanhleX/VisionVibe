package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.FrameMaterialCreationRequest;
import com.cx.visionvibe.dto.request.FrameMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.FrameMaterialResponse;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.FrameMaterialMapper;
import com.cx.visionvibe.repository.FrameMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrameMaterialService {
    private final FrameMaterialRepository frameMaterialRepository;

    private final FrameMaterialMapper frameMaterialMapper;

    public List<FrameMaterialResponse> findAllFrameMaterial() {
        return frameMaterialRepository.findAll().stream()
                .map(frameMaterialMapper::toFrameMaterialResponse).toList();
    }

    public FrameMaterialResponse findFrameMaterialById(Long id) {
        return frameMaterialMapper.toFrameMaterialResponse(frameMaterialRepository.findByFrameMaterialId(id)
                .orElseThrow(() -> new AppException(ErrorCode.FRAME_COLOR_ID_NOT_FOUND)));
    }

    public FrameMaterialResponse createFrameMaterial(FrameMaterialCreationRequest request) {
        var frameMaterial = frameMaterialMapper.toFrameMaterial(request);
        frameMaterial.setIsActive(true);

        return frameMaterialMapper.toFrameMaterialResponse(frameMaterialRepository.save(frameMaterial));
    }

    public FrameMaterialResponse updateFrameMaterial(Long id, FrameMaterialUpdateRequest request) {
        var frameMaterial = frameMaterialRepository.findByFrameMaterialId(id)
               .orElseThrow(() -> new AppException(ErrorCode.FRAME_MATERIAL_ID_NOT_FOUND));

        frameMaterialMapper.updateFrameMaterial(frameMaterial, request);

        return frameMaterialMapper.toFrameMaterialResponse(frameMaterialRepository.save(frameMaterial));
    }

    public void deleteFrameMaterial(Long id) {
        var frameMaterial = frameMaterialRepository.findByFrameMaterialId(id)
                .orElseThrow(() -> new AppException(ErrorCode.FRAME_MATERIAL_ID_NOT_FOUND));

        frameMaterial.setIsActive(false);

        frameMaterialRepository.save(frameMaterial);
    }
}
