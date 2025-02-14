package com.example.ttst.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReviewRequest {
    private Long memberId;
    private Long productId;
    private String comment;
    private double rating;
}
