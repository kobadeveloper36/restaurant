package com.progect.ui.services;

import com.progect.ui.rest.DishServiceClient;
import com.progect.ui.rest.OrderServiceClient;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderServiceClient orderServiceClient;
    private final DishServiceClient dishServiceClient;

    public OrderService(OrderServiceClient orderServiceClient, DishServiceClient dishServiceClient) {
        this.orderServiceClient = orderServiceClient;
        this.dishServiceClient = dishServiceClient;
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

    public List<OrderResponseDTO> getAllOrders() {
        return orderServiceClient.getAllOrders();
    }

    public List<DishResponseDTO> getOrderDishes(OrderResponseDTO order) {
        return order.getDishes().stream().map(dishServiceClient::getDishById)
                .sorted(Comparator.comparing(DishResponseDTO::getDishId)).collect(Collectors.toList());
    }

    public Double getOrderSum(List<Long> dishes) {
        double orderSum = 0.0;
        List<DishResponseDTO> allDishes = dishServiceClient.getAllDishes();
        for (Long dishId : dishes) {
            for (DishResponseDTO dish : allDishes) {
                if (dish.getDishId().equals(dishId)) {
                    orderSum += dish.getPrice();
                }
            }
        }
        return orderSum;
    }

    public OrderResponseDTO deleteOrderById(Long orderId) {
        return orderServiceClient.deleteOrderById(orderId);
    }

    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO order) {
        return orderServiceClient.updateOrderById(orderId, order);
    }
}
