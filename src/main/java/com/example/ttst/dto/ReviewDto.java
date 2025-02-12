package com.example.ttst.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReviewDto {
    private Long id;
    private String comment;
    private double rating;
}
