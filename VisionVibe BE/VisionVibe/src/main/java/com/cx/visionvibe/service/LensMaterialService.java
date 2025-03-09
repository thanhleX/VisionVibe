package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.LensColorCreationRequest;
import com.cx.visionvibe.dto.request.LensMaterialCreationRequest;
import com.cx.visionvibe.dto.request.LensMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.LensMaterialResponse;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.LensMaterialMapper;
import com.cx.visionvibe.repository.LensMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LensMaterialService {
    private final LensMaterialRepository lensMaterialRepository;

    private final LensMaterialMapper lensMaterialMapper;

    public List<LensMaterialResponse> findAllLensMaterial() {
        return lensMaterialRepository.findAll().stream()
                .map(lensMaterialMapper::toLensMaterialResponse).toList();
    }

    public LensMaterialResponse findLensMaterialById(Long id) {
        return lensMaterialMapper.toLensMaterialResponse(lensMaterialRepository.findByLensMaterialId(id)
                .orElseThrow(() -> new AppException(ErrorCode.LENS_MATERIAL_ID_NOT_FOUND)));
    }

    public LensMaterialResponse createLensMaterial(LensMaterialCreationRequest request) {
        var lensMaterial = lensMaterialMapper.toLensMaterial(request);
        lensMaterial.setIsActive(true);

        return lensMaterialMapper.toLensMaterialResponse(lensMaterialRepository.save(lensMaterial));
    }

    public LensMaterialResponse updateLensMaterial(Long id, LensMaterialUpdateRequest request) {
        var lensMaterial = lensMaterialRepository.findByLensMaterialId(id)
               .orElseThrow(() -> new AppException(ErrorCode.LENS_MATERIAL_ID_NOT_FOUND));

        lensMaterialMapper.updateLensMaterial(lensMaterial, request);

        return lensMaterialMapper.toLensMaterialResponse(lensMaterialRepository.save(lensMaterial));
    }

    public void deleteLensMaterial(Long id) {
        var lensMaterial = lensMaterialRepository.findByLensMaterialId(id)
                .orElseThrow(() -> new AppException(ErrorCode.LENS_MATERIAL_ID_NOT_FOUND));

        lensMaterial.setIsActive(false);

        lensMaterialRepository.save(lensMaterial);
    }
}
