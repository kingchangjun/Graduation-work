package com.example.ttst.controller;

import com.example.ttst.dto.ReviewDto;
import com.example.ttst.dto.ReviewRequest;
import com.example.ttst.entity.Review;
import com.example.ttst.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //  리뷰 추가 (한 번만 가능)
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request) {
        try {
            ReviewDto review = reviewService.addReview(request.getMemberId(), request.getProductId(), request.getComment(), request.getRating());
            return ResponseEntity.ok(review);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  리뷰 수정 (여러 번 가능)
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody ReviewRequest request) {
        try {
            ReviewDto review = reviewService.updateReview(request.getMemberId(), request.getProductId(), request.getComment(), request.getRating());
            return ResponseEntity.ok(review);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  리뷰 삭제
    @DeleteMapping("/{productId}/{memberId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long productId, @PathVariable Long memberId) {
        reviewService.deleteReview(productId, memberId);
        return ResponseEntity.ok("Review deleted successfully!");
    }

    //  특정 상품의 모든 리뷰 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }
}

