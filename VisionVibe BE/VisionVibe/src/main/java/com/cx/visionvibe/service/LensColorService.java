package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.LensColorCreationRequest;
import com.cx.visionvibe.dto.request.LensColorUpdateRequest;
import com.cx.visionvibe.dto.response.LensColorResponse;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.LensColorMapper;
import com.cx.visionvibe.repository.LensColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LensColorService {
    private final LensColorRepository lensColorRepository;

    private final LensColorMapper lensColorMapper;

    public List<LensColorResponse> findAllLensColor() {
        return lensColorRepository.findAll().stream()
                .map(lensColorMapper::toLensColorResponse).toList();
    }

    public LensColorResponse findLensColorById(Long id) {
        return lensColorMapper.toLensColorResponse(lensColorRepository.findByLensColorId(id)
                .orElseThrow(() -> new AppException(ErrorCode.LENS_COLOR_ID_NOT_FOUND)));
    }

    public LensColorResponse createLensColor(LensColorCreationRequest request) {
        var lensColor = lensColorMapper.toLensColor(request);
        lensColor.setIsActive(true);

        return lensColorMapper.toLensColorResponse(lensColorRepository.save(lensColor));
    }

    public LensColorResponse updateLensColor(Long id, LensColorUpdateRequest request) {
        var lensColor = lensColorRepository.findByLensColorId(id)
               .orElseThrow(() -> new AppException(ErrorCode.LENS_COLOR_ID_NOT_FOUND));

        lensColorMapper.updateLensColor(lensColor, request);

        return lensColorMapper.toLensColorResponse(lensColorRepository.save(lensColor));
    }

    public void deleteLensColor(Long id) {
        var lensColor = lensColorRepository.findByLensColorId(id)
                .orElseThrow(() -> new AppException(ErrorCode.LENS_COLOR_ID_NOT_FOUND));

        lensColor.setIsActive(false);

        lensColorRepository.save(lensColor);
    }
}
