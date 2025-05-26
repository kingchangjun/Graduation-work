package com.example.ttst.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressRequest {
    private String receiverName;
    private String phoneNumber;
    private String zipCode;
    private String addressLine1;
    private String addressLine2;
    private String memo;
    private Long memberId;
    private String type; // "DEFAULT", "NEW", "RECENT"
    private boolean isDefault;
}
