package com.example.ttst.service;

import com.example.ttst.dto.DeliveryAddressRequest;
import com.example.ttst.entity.DeliveryAddress;
import com.example.ttst.entity.Member;
import com.example.ttst.repository.DeliveryAddressRepository;
import com.example.ttst.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final MemberRepository memberRepository;

    public void saveAddress(Member member, DeliveryAddressRequest request, DeliveryAddress.AddressType type) {
        if (type == DeliveryAddress.AddressType.DEFAULT) {
            // 기존 기본 주소가 있다면 해제
            deliveryAddressRepository.findByMemberAndIsDefaultTrue(member)
                    .ifPresent(addr -> {
                        addr.setDefault(false);
                        deliveryAddressRepository.save(addr);
                    });
        }

        DeliveryAddress address = DeliveryAddress.builder()
                .receiverName(request.getReceiverName())
                .phoneNumber(request.getPhoneNumber())
                .zipCode(request.getZipCode())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .memo(request.getMemo())
                .type(type)
                .member(member)
                .isDefault(type == DeliveryAddress.AddressType.DEFAULT)
                .build();

        deliveryAddressRepository.save(address);
    }

    public List<DeliveryAddress> getAddressesByMember(Member member) {
        return deliveryAddressRepository.findByMember(member);
    }
}
