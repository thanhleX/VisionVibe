package com.cx.visionvibebe.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodResponse {
    private Long id;
    private String name;
    private String fontawesomeLogo;
}
