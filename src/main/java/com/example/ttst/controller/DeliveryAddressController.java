package com.example.ttst.controller;
import com.example.ttst.entity.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.ttst.dto.DeliveryAddressRequest;
import com.example.ttst.entity.DeliveryAddress;
import com.example.ttst.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delivery-address")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @PostMapping
    public ResponseEntity<String> saveNewAddress(@AuthenticationPrincipal Member member,
                                                 @RequestBody DeliveryAddressRequest request) {
        deliveryAddressService.saveAddress(member, request, DeliveryAddress.AddressType.NEW);
        return ResponseEntity.ok("새 배송지 저장 완료");
    }


    @PostMapping("/recent")
    public ResponseEntity<String> saveRecentAddress(@AuthenticationPrincipal Member member,
                                                    @RequestBody DeliveryAddressRequest request) {
        deliveryAddressService.saveAddress(member, request, DeliveryAddress.AddressType.RECENT);
        return ResponseEntity.ok("최근 배송지 저장 완료");
    }

    @PostMapping("/default")
    public ResponseEntity<String> saveDefaultAddress(@AuthenticationPrincipal Member member,
                                                     @RequestBody DeliveryAddressRequest request) {
        deliveryAddressService.saveAddress(member, request, DeliveryAddress.AddressType.DEFAULT);
        return ResponseEntity.ok("기본 배송지 저장 완료");
    }

    @GetMapping
    public ResponseEntity<List<DeliveryAddress>> getMyAddresses(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(deliveryAddressService.getAddressesByMember(member));
    }
}


/*
* 사용자가 “기본 배송지”를 선택하면 type: "DEFAULT", isDefault: true 로 요청을 보내기.
“새 배송지”는 type: "NEW"이고, 기본 주소로 설정하지 않는다면 isDefault: false.
“최근 배송지”는 배송 완료 시 백엔드에서 자동으로 추가하면 됨.
* */