package com.example.ttst.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Review {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reviewCode;

    private String comment;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //상품 이름 json응답에 포함
    public String getProductName(){
        return product.getName();
    }


    //리뷰 저장 전 리뷰 코드 자동 생성
    @PrePersist
    public void generateReviewCode(){
        if(this.reviewCode == null){
            this.reviewCode = product.getId()+"_"+member.getId();
        }
    }

}
