package com.cx.visionvibebe.dto.request;

import com.cx.visionvibebe.dto.CreateNewOrderDetailDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewOrderRequest {
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;
    private String note;
    private Long paymentMethodId;
    private List<CreateNewOrderDetailDto> createNewOrderDetailDtoList;
}
