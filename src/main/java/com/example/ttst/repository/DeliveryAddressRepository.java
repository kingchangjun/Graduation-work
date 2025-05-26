package com.example.ttst.repository;

import com.example.ttst.entity.DeliveryAddress;
import com.example.ttst.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByMember(Member member);
    Optional<DeliveryAddress> findByMemberAndIsDefaultTrue(Member member);
}