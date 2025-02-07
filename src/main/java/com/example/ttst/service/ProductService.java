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


}
