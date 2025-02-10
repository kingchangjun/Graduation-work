package com.example.ttst.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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

}
