package com.example.ttst.dto;

import com.example.ttst.entity.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private String reviewCode;
    private String comment;
    private double rating;
    private String productName; //  해당 리뷰가 어떤 상품에 대한 것인지 표시

    // Review 엔티티를 ReviewDto로 변환
    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                .reviewCode(review.getReviewCode())
                .comment(review.getComment())
                .rating(review.getRating())
                .productName(review.getProduct().getName()) // Product의 이름만 포함
                .build();
    }
}
