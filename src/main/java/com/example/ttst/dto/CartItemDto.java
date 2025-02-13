package com.example.ttst.dto;

import com.example.ttst.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Long id;
    private String productName;
    private double productPrice;
    private int quantity;

    public static CartItemDto fromEntity(com.example.ttst.entity.CartItem cartItem) {
        Product product = cartItem.getProduct();
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
