package com.example.ttst.service;

import com.example.ttst.dto.CartItemDto;
import com.example.ttst.entity.CartItem;
import com.example.ttst.entity.Member;
import com.example.ttst.entity.Product;
import com.example.ttst.repository.CartItemRepository;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    // 장바구니에 상품 추가(중복 시 수량 증가)
    public CartItemDto addToCart(Long memberId, Long productId, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 이미 장바구니에 있는 상품인지 확인
        CartItem cartItem = cartItemRepository.findByMemberIdAndProductId(memberId, productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .member(member)
                            .product(product)
                            .quantity(quantity)
                            .build();
                    return cartItemRepository.save(newItem);
                });

        return CartItemDto.fromEntity(cartItem);

    }
    public CartItemDto updateCartItem(Long memberId, Long productId, int quantity) {
        CartItem cartItem = cartItemRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        CartItem updatedItem = cartItemRepository.save(cartItem);
        return CartItemDto.fromEntity(updatedItem);
    }

    // 장바구니 목록 조회
    public List<CartItemDto> getCart(Long memberId) {
        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);

        return cartItemRepository.findByMemberId(memberId)
                .stream()
                .map(CartItemDto::fromEntity)
                .collect(Collectors.toList());
    }
    //특정 상품 삭제
    public void removeFromCart(Long memberId, Long productId) {
        CartItem cartItem = cartItemRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        cartItemRepository.delete(cartItem);
    }

    //장바구니 총 가격 계산
    public double getTotalPrice(Long memberId) {
        return cartItemRepository.findByMemberId(memberId)
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }


    // 장바구니 비우기
    public void clearCart(Long memberId) {
        cartItemRepository.deleteAll(cartItemRepository.findByMemberId(memberId));
    }
}
