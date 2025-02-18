package com.example.ttst.service;

import com.example.ttst.dto.WishlistItemDto;
import com.example.ttst.dto.WishlistRequest;
import com.example.ttst.entity.Member;
import com.example.ttst.entity.Product;
import com.example.ttst.entity.WishlistItem;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.repository.ProductRepository;
import com.example.ttst.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //즐겨찾기 추가
    public WishlistItemDto addToWishlist(WishlistRequest request){
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //  이미 즐겨찾기에 추가된 경우 예외 처리
        if (wishlistRepository.findByMemberIdAndProductId(request.getMemberId(), request.getProductId()).isPresent()) {
            throw new RuntimeException("이미 즐겨찾기에 추가된 상품입니다.");
        }
        WishlistItem wishlistItem = WishlistItem.builder()
                .member(member)
                .product(product)
                .build();

        WishlistItem savedItem = wishlistRepository.save(wishlistItem);
        return WishlistItemDto.fromEntity(savedItem);
    }

    public List<WishlistItemDto> getWishlist(Long memberId){
        return wishlistRepository.findByMemberId(memberId)
                .stream()
                .map(WishlistItemDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 즐겨찾기에서 상품 제거
    public void removeFromWishlist(Long memberId, Long productId) {
        WishlistItem wishlistItem = wishlistRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("즐겨찾기에서 찾을 수 없는 상품입니다."));
        wishlistRepository.delete(wishlistItem);
    }
}
