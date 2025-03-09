package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.FrameColorCreationRequest;
import com.cx.visionvibe.dto.request.FrameColorUpdateRequest;
import com.cx.visionvibe.dto.response.FrameColorResponse;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.FrameColorMapper;
import com.cx.visionvibe.repository.FrameColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrameColorService {
    private final FrameColorRepository frameColorRepository;

    private final FrameColorMapper frameColorMapper;

    public List<FrameColorResponse> findAllFrameColor() {
        return frameColorRepository.findAll().stream()
                .map(frameColorMapper::toFrameColorResponse).toList();
    }

    public FrameColorResponse findFrameColorById(Long id) {
        return frameColorMapper.toFrameColorResponse(frameColorRepository.findByFrameColorId(id)
                .orElseThrow(() -> new AppException(ErrorCode.FRAME_COLOR_ID_NOT_FOUND)));
    }

    public FrameColorResponse createFrameColor(FrameColorCreationRequest request) {
        var frameColor = frameColorMapper.toFrameColor(request);
        frameColor.setIsActive(true);

        return frameColorMapper.toFrameColorResponse(frameColorRepository.save(frameColor));
    }

    public FrameColorResponse updateFrameColor(Long id, FrameColorUpdateRequest request) {
        var frameColor = frameColorRepository.findByFrameColorId(id)
               .orElseThrow(() -> new AppException(ErrorCode.FRAME_COLOR_ID_NOT_FOUND));

        frameColorMapper.updateFrameColor(frameColor, request);

        return frameColorMapper.toFrameColorResponse(frameColorRepository.save(frameColor));
    }

    public void deleteFrameColor(Long id) {
        var frameColor = frameColorRepository.findByFrameColorId(id)
                .orElseThrow(() -> new AppException(ErrorCode.FRAME_COLOR_ID_NOT_FOUND));

        frameColor.setIsActive(false);

        frameColorRepository.save(frameColor);
    }
}
