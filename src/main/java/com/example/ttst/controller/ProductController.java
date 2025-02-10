package com.example.ttst.controller;


import com.example.ttst.dto.ProductDto;
import com.example.ttst.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public List<ProductDto> search(@RequestParam String keyword) {
        return productService.getProducts(keyword);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.ok("Product added successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
