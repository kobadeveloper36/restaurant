package com.progect.ui.services;

import com.progect.ui.rest.OrderServiceClient;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderServiceClient orderServiceClient;

    public OrderService(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    public List<OrderResponseDTO> getOrdersById(Long userId) {
        return orderServiceClient.getOrdersById(userId);
    }

    public Long createOrder(OrderRequestDTO orderRequestDTO) {
        return orderServiceClient.createOrder(orderRequestDTO);
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        return orderServiceClient.getOrderById(orderId);
    }
}
