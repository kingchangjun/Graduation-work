package com.example.ttst.repository;

import com.example.ttst.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByMemberId(Long memberId);//사용자 장바구니 조히
    Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId); //특정 상품 조회
}
