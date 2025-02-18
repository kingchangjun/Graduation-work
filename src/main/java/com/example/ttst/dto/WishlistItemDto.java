package com.example.ttst.dto;

import com.example.ttst.entity.WishlistItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WishlistItemDto {
    private Long productId;
    private String productName;

    public static WishlistItemDto fromEntity(WishlistItem wishlistItem){
        return  WishlistItemDto.builder()
                .productId((long) wishlistItem.getProduct().getId())
                .productName(wishlistItem.getProduct().getName())
                .build();
    }
}
