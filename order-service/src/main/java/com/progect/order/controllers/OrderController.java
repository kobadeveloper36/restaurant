package com.progect.order.controllers;

import com.progect.order.controllers.dto.OrderRequestDTO;
import com.progect.order.controllers.dto.OrderResponseDTO;
import com.progect.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
        return new OrderResponseDTO(orderService.getOrderById(orderId));
    }

    @PostMapping("/")
    public Long createUser(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @PutMapping("/{orderId}")
    public OrderResponseDTO updateUser(@PathVariable Long orderId, @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.updateOrder(orderId, orderRequestDTO);
    }

    @DeleteMapping("/{orderId}")
    public OrderResponseDTO deleteUser(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> getOrdersById(@PathVariable Long userId) {
        return orderService.getOrdersById(userId);
    }

    @GetMapping("/")
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
