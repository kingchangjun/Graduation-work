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


    public List<ProductDto> filterProduct(String keyword, String category, double minPrice, double maxPrice, double minRating) {
        //필터링 조건 확인
        System.out.println("서비스에서 받은 필터링 조건: category=" + category + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", minRating=" + minRating);

        List<Product> products = productRepository.findByCategoryAndPriceBetweenAndRatingGreaterThanEqual(
                category, minPrice, maxPrice, minRating);

        //검색된 결과 로그 출력
        for (Product p : products) {
            System.out.println("검색 결과: " + p.getName() + ", 가격: " + p.getPrice());
        }

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
