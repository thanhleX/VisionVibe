package com.cx.visionvibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FrameColorDto {
    private Long id;
    private String color;
}
