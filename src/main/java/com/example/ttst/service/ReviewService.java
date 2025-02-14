package com.example.ttst.service;

import com.example.ttst.dto.ReviewDto;
import com.example.ttst.entity.Member;
import com.example.ttst.entity.Product;
import com.example.ttst.entity.Review;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.repository.ProductRepository;
import com.example.ttst.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //  리뷰 추가 (한 번만 작성 가능)
    public ReviewDto addReview(Long memberId, Long productId, String comment, double rating) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String reviewCode = productId + "_" + memberId;

        //  기존 리뷰가 있는지 확인 후 예외 발생
        if (reviewRepository.findByReviewCode(reviewCode).isPresent()) {
            throw new RuntimeException("이미 작성한 리뷰가 있습니다. 수정하려면 리뷰 수정을 이용해 주세요.");
        }

        // 새로운 리뷰 생성
        Review review = Review.builder()
                .reviewCode(reviewCode)
                .member(member)
                .product(product)
                .comment(comment)
                .rating(rating)
                .build();

        Review savedReview = reviewRepository.save(review);
        return ReviewDto.fromEntity(savedReview);
    }

    //  리뷰 수정 (여러 번 가능)
    public ReviewDto updateReview(Long memberId, Long productId, String newComment, double newRating) {
        String reviewCode = productId + "_" + memberId;
        Review review = reviewRepository.findByReviewCode(reviewCode)
                .orElseThrow(() -> new RuntimeException("수정할 리뷰가 존재하지 않습니다. 먼저 리뷰를 작성하세요."));

        review.setComment(newComment);
        review.setRating(newRating);
        Review updatedReview = reviewRepository.save(review);
        return ReviewDto.fromEntity(updatedReview);
    }

    // 리뷰 삭제
    public void deleteReview(Long productId, Long memberId) {
        String reviewCode = productId + "_" + memberId;
        Review review = reviewRepository.findByReviewCode(reviewCode)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }

    //  특정 상품의 모든 리뷰 조회 (상품 정보 포함)
    public List<ReviewDto> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviews.stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
    }
}

