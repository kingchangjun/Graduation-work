package com.example.ttst.repository;

import com.example.ttst.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //키워드 검색 기능
    List<Product> findByNameContaining(String keyword);

    //필터링 검색 기능
    List<Product> findByCategoryAndPriceBetweenAndRatingGreaterThanEqual(String category, double minPrice, double maxPrice, double rating);

    //여러 개의 상품을 ID 리스트로 가져오는 기능
    List<Product> findAllById(List<Long> ids);
}
