package com.example.ttst.service;

import com.example.ttst.dto.ReviewDto;
import com.example.ttst.entity.Product;
import com.example.ttst.entity.Review;
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
    private final ProductRepository productRepository;

    // 리뷰 추가 기능
    public void addReview(Long productId, String comment, double rating) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = Review.builder()
                .product(product)
                .comment(comment)
                .rating(rating)
                .build();

        reviewRepository.save(review); //  리뷰 저장
        product.getReviews().add(review);
        product.updateRating(); // 평균 평점 업데이트
        productRepository.save(product);
    }

    //  특정 상품의 모든 리뷰 조회
    public List<ReviewDto> getReviews(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        return reviews.stream()
                .map(review -> ReviewDto.builder()
                        .id(review.getId())
                        .comment(review.getComment())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());
    }


    // 리뷰 삭제 기능
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Product product = review.getProduct();
        reviewRepository.delete(review);

        product.updateRating(); //  리뷰 삭제 후 평점 업데이트
        productRepository.save(product);
    }
}
