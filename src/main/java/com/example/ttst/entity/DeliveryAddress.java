package com.example.ttst.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiverName;
    private String phoneNumber;
    private String zipCode;
    private String addressLine1;
    private String addressLine2;
    private String memo;

    @Enumerated(EnumType.STRING)
    private AddressType type; // 배송지 유형

    public enum AddressType {
        DEFAULT, // 기본 배송지
        NEW,     // 새 배송지
        RECENT   // 최근 사용한 배송지
    }

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isDefault; // 기본 배송지 여부
}
