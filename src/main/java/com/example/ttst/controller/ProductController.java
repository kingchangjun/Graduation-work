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

    /*@GetMapping("/search")<- 똑같은거 두 개 사용하면 안됨. 아래에 필터링 기능에 search있으니깐 이건 삭제.
    public List<ProductDto> search(@RequestParam String keyword) {
        return productService.getProducts(keyword);
    }*/
    @PostMapping("/add-multiple")
    public ResponseEntity<String> addMultipleProducts(@RequestBody List<ProductDto> productDtoList) {
        productService.addMultipleProducts(productDtoList);
        return ResponseEntity.ok("Products added successfully");
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

    @GetMapping("/search")
    public List<ProductDto> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating) {

        return productService.searchProducts(keyword, category, minPrice, maxPrice, minRating);
    }


    @GetMapping("/compare")
    public List<ProductDto> compareProducts(@RequestParam List<Long> ids) {
        return productService.compareProducts(ids);
    }


}
