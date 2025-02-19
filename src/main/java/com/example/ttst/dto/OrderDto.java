package com.example.ttst.dto;
import com.example.ttst.entity.Order;
import com.example.ttst.entity.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderDto {
    private Long orderId;
    private List<OrderItemDto> items;
    private OrderStatus status;
    private double totalPrice;

    public static OrderDto fromEntity(Order order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .items(order.getOrderItems().stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
