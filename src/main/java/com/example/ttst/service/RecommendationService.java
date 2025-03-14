package com.example.ttst.service;

import com.example.ttst.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    // 추천용 임시 데이터
    private static final List<String> productDataList = List.of(
            "Laptop, Electronics, 1200.0, 4.5",
            "Smartphone, Electronics, 800.0, 4.7",
            "Washing Machine, Home Appliance, 500.0, 4.3"
    );

    public List<Product> getRecommendations() {
        List<Product> recommendedProducts = new ArrayList<>();

        for (String line : productDataList) {
            String[] values = line.split(",");

            if (values.length == 4) { // 데이터 유효성 검사
                String name = values[0].trim();
                String category = values[1].trim();
                double price = Double.parseDouble(values[2].trim());
                double rating = Double.parseDouble(values[3].trim());

                // Product 객체 생성 후 리스트에 추가
                recommendedProducts.add(new Product(name, category, price, rating));
            } else {
                System.out.println("잘못된 데이터 형식: " + line);
            }
        }
        return recommendedProducts;
    }
}
