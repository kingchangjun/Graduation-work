package com.example.ttst.controller;

import com.example.ttst.dto.CartItemDto;
import com.example.ttst.dto.CartRequest;
import com.example.ttst.entity.CartItem;
import com.example.ttst.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        cartService.addToCart(cartRequest.getMemberId(), cartRequest.getProductId(), cartRequest.getQuantity());
        return ResponseEntity.ok("Product added to cart successfully!");
    }

    //  장바구니 목록 조회
    @GetMapping("/{memberId}")
    public List<CartItemDto> getCart(@PathVariable Long memberId) {
        return cartService.getCart(memberId);
    }

    // 특정 상품 삭제
    @DeleteMapping("/remove/{memberId}/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long memberId, @PathVariable Long productId) {
        cartService.removeFromCart(memberId, productId);
        return ResponseEntity.ok("Product removed from cart successfully!");
    }

    // 장바구니 비우기
    @DeleteMapping("/clear/{memberId}")
    public ResponseEntity<String> clearCart(@PathVariable Long memberId) {
        cartService.clearCart(memberId);
        return ResponseEntity.ok("Cart cleared successfully!");
    }
}
