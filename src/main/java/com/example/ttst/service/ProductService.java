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

    //상품 여러개 추가하는 기능.
    public void addMultipleProducts(List<ProductDto> productDtoList) {
        List<Product> products = productDtoList.stream()
                .map(dto -> Product.builder()
                        .name(dto.getName())
                        .category(dto.getCategory())
                        .price(dto.getPrice())
                        .rating(dto.getRating())
                        .build())
                .collect(Collectors.toList());

        productRepository.saveAll(products);
    }


    public List<ProductDto> searchProducts(String keyword, String category, Double minPrice, Double maxPrice, Double minRating) {
        // 기본값 적용 (NULL이면 기본값 사용)
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;
        if (minRating == null) minRating = 0.0;

        System.out.println(" 검색 요청: keyword=" + keyword + ", category=" + category +
                ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", minRating=" + minRating);

        List<Product> products = productRepository.searchWithFilters(keyword, category, minPrice, maxPrice, minRating);

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
        List<Product> products = productRepository.findAllById(productIds);

        // 비교 요청이 들어오면 로그 출력
        System.out.println(" 비교할 제품 ID 목록: " + productIds);

        return products.stream()
                .map(product -> {
                    System.out.println("비교 제품: " + product.getName() + " (가격: " + product.getPrice() + ")");
                    return ProductDto.builder()
                            .id((long) product.getId())
                            .name(product.getName())
                            .category(product.getCategory())
                            .price(product.getPrice())
                            .rating(product.getRating())
                            .build();
                })
                .collect(Collectors.toList());
    }







}
