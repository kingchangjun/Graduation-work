package com.example.ttst.controller;

import com.example.ttst.dto.WishlistItemDto;
import com.example.ttst.dto.WishlistRequest;
import com.example.ttst.repository.WishlistRepository;
import com.example.ttst.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    //즐겨찾기 추가
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistRequest request) {
        try {
            WishlistItemDto wishlistItem = wishlistService.addToWishlist(request);
            return ResponseEntity.ok(wishlistItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<WishlistItemDto>> getWishlist(@PathVariable Long memberId) {
        return ResponseEntity.ok(wishlistService.getWishlist(memberId));
    }

    //  즐겨찾기에서 상품 제거
    @DeleteMapping("/remove/{memberId}/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long memberId, @PathVariable Long productId) {
        wishlistService.removeFromWishlist(memberId, productId);
        return ResponseEntity.ok("상품이 즐겨찾기에서 삭제되었습니다.");
    }
}
