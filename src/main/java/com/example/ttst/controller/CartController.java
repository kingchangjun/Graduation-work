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

    //  장바구니 추가
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(@RequestBody CartRequest request) {
        CartItemDto cartItem = cartService.addToCart(request.getMemberId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    // 상품 수량 업데이트
    @PutMapping("/update")
    public ResponseEntity<CartItemDto> updateCart(@RequestBody CartRequest request) {
        CartItemDto cartItem = cartService.updateCartItem(request.getMemberId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    // 장바구니 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<CartItemDto>> getCart(@PathVariable Long memberId) {
        return ResponseEntity.ok(cartService.getCart(memberId));
    }

    // 장바구니 총 가격 조회
    @GetMapping("/total/{memberId}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long memberId) {
        return ResponseEntity.ok(cartService.getTotalPrice(memberId));
    }

    // 장바구니 비우기
    @DeleteMapping("/clear/{memberId}")
    public ResponseEntity<String> clearCart(@PathVariable Long memberId) {
        cartService.clearCart(memberId);
        return ResponseEntity.ok("Cart cleared successfully!");
    }

    // 특정 상품 삭제
    @DeleteMapping("/remove/{memberId}/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long memberId, @PathVariable Long productId) {
        cartService.removeFromCart(memberId, productId);
        return ResponseEntity.ok("Product removed from cart successfully!");
    }

}
