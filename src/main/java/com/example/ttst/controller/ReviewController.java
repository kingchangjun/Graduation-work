package com.example.ttst.controller;

import com.example.ttst.dto.ReviewDto;
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

    // 리뷰 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto.getId(), reviewDto.getComment(), reviewDto.getRating());
        return ResponseEntity.ok("Review added successfully!");
    }

    // 특정 상품의 모든 리뷰 조회 API
    @GetMapping("/{productId}")
    public List<ReviewDto> getReviews(@PathVariable Long productId) {
        return reviewService.getReviews(productId);
    }


    // 리뷰 삭제 API
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully!");
    }
}
