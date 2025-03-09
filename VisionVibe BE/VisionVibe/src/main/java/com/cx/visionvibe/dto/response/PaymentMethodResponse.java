package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentMethodResponse {
    private Long id;
    private String name;
    private String fontawesomeLogo;
}
