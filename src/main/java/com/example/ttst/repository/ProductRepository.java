package com.example.ttst.repository;

import com.example.ttst.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //키워드 검색 기능 대소문자 무시,
    List<Product> findByNameContaining(String keyword);

    //필터링 검색 기능
    @Query("SELECT p FROM Product p WHERE "
            + "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
            + "AND (:category IS NULL OR p.category = :category) "
            + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR p.price <= :maxPrice) "
            + "AND (:minRating IS NULL OR p.rating >= :minRating)")
    List<Product> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minRating") Double minRating
    );

}
