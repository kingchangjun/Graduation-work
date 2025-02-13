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

    // 장바구니에 상품 추가
    public void addToCart(Long memberId, Long productId, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 이미 장바구니에 있는 상품인지 확인
        Optional<CartItem> existingCartItem = cartItemRepository.findByMemberIdAndProductId(memberId, productId);

        if (existingCartItem.isPresent()) {
            // 이미 존재하면 수량 증가
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // 존재하지 않으면 새로 추가
            CartItem cartItem = CartItem.builder()
                    .member(member)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(cartItem);
        }
    }

    // 장바구니 목록 조회
    public List<CartItemDto> getCart(Long memberId) {
        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);

        return cartItems.stream()
                .map(CartItemDto::fromEntity)
                .collect(Collectors.toList());
    }
    //특정 상품 삭제
    public void removeFromCart(Long memberId, Long productId) {
        CartItem cartItem = cartItemRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 비우기
    public void clearCart(Long memberId) {
        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is already empty");
        }
        cartItemRepository.deleteAll(cartItems);
    }

}
