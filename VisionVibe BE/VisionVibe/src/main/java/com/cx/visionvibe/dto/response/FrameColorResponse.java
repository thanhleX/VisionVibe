package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FrameColorResponse {
    private Long id;
    private String color;
}
