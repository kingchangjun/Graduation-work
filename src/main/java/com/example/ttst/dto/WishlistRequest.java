package com.example.ttst.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {
    private Long memberId;
    private Long productId;
}