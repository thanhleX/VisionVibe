package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LensColorResponse {
    private Long id;
    private String name;
}
