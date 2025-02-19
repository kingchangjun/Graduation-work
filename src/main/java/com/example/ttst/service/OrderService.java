package com.example.ttst.service;

import com.example.ttst.dto.OrderDto;
import com.example.ttst.dto.OrderItemRequest;
import com.example.ttst.dto.OrderRequest;
import com.example.ttst.entity.*;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.repository.OrderRepository;
import com.example.ttst.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //주문 생성
    public OrderDto createOrder(OrderRequest request){
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(()-> new RuntimeException("Member not found"));

        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice() * itemRequest.getQuantity())
                    .build();

            totalPrice += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.ORDERED)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(item -> item.setOrder(order));

        return OrderDto.fromEntity(orderRepository.save(order));
    }

    //  주문 목록 조회
    public List<OrderDto> getOrders(Long memberId) {
        return orderRepository.findByMemberId(memberId)
                .stream()
                .map(OrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}