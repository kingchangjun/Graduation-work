package com.example.ttst.controller;

import com.example.ttst.dto.OrderDto;
import com.example.ttst.dto.OrderRequest;
import com.example.ttst.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //  주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // 주문 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long memberId) {
        return ResponseEntity.ok(orderService.getOrders(memberId));
    }

    //  주문 취소
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}
