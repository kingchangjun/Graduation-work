package com.example.ttst.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA 기본 생성자.
@AllArgsConstructor


public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private double price;
    private String category;
    private double rating;

    @Builder//JPA에서 ID필드를 무시하도록 설정.
    public Product(String name, String category, double price, double rating) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    public void updateRating(){
        if (reviews.isEmpty()) {
            this.rating = 0.0;
        } else {
            double avgRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            this.rating = Math.round(avgRating * 10.0) / 10.0; // 소수점 한 자리 반올림
        }
    }


}
