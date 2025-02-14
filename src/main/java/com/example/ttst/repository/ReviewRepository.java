package com.example.ttst.repository;

import com.example.ttst.entity.Product;
import com.example.ttst.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewCode(String reviewCode);
    List<Review> findByProduct(Product product);
}
