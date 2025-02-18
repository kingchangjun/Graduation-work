package com.example.ttst.repository;

import com.example.ttst.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByMemberId(Long memebrId);//특정 사용자의 즐겨찾기 목록 조회
    Optional<WishlistItem> findByMemberIdAndProductId(Long memberId, Long productId); //특정 상품이 즐겨찾기에 있는지 확인
}
