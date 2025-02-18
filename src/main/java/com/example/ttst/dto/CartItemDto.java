package com.example.ttst.dto;

import com.example.ttst.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private double totalPrice;

    public static CartItemDto fromEntity(com.example.ttst.entity.CartItem cartItem) {
        Product product = cartItem.getProduct();
        return CartItemDto.builder()
                .productId(cartItem.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}
