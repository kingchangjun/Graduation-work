package com.example.ttst.service;

import com.example.ttst.dto.ProductDto;
import com.example.ttst.entity.Product;
import com.example.ttst.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts(String keyword) {
        List<Product> products = productRepository.findByNameContaining(keyword);

        return products.stream()
                .map(product -> ProductDto.builder()
                        .id((long) product.getId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .rating(product.getRating())
                        .build())
                .collect(Collectors.toList());
    }

    public void addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .rating(productDto.getRating())
                .build();
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> filterProduct(String keyword, String category, double minpPice, double maxPrice, double minRating) {

        //JPa 쿼리 실행 필터링 조건을 만족하는 상품 리스트 가져오기
        List<Product> products = productRepository.findByCategoryAndPriceBetweenAndRatingGreaterThanEqual(category, minpPice, maxPrice, minRating);

        //Product 객체 리스트를 ProductDto 리스트로 변환.
        return products.stream()
                .map(product -> ProductDto.builder()
                        .id((long) product.getId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .rating(product.getRating())
                        .build())
                .collect(Collectors.toList());
    }
    public List<ProductDto> compareProducts(List<Long> productIds) {

        //JPA를 사용해서 상품 ID 리스트에 해당하는 상품들을 가져오기
        List<Product> products = productRepository.findAllById(productIds);

        //Product를 ProductDto 변환
        return products.stream()
                .map(product -> ProductDto.builder()
                        .id((long) product.getId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .rating(product.getRating())
                        .build())
                .collect(Collectors.toList());
    }





}
