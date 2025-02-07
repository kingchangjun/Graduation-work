package com.example.ttst.controller;


import com.example.ttst.dto.ProductDto;
import com.example.ttst.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public List<ProductDto> search(@RequestParam String keyword) {
        System.out.println(keyword);
        return productService.getProducts(keyword);


    }
}
