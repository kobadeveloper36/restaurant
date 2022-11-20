package com.progect.order.services;

import com.progect.order.controllers.dto.OrderRequestDTO;
import com.progect.order.controllers.dto.OrderResponseDTO;
import com.progect.order.entities.Order;
import com.progect.order.repository.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("Order with id: " + orderId + "not found"));
    }

    public Long createOrder(OrderRequestDTO orderRequestDTO) {
        Order newOrder = new Order(orderRequestDTO.getCustomerName(), orderRequestDTO.getCustomerPhone(),
                orderRequestDTO.getCustomerEmail(), orderRequestDTO.getIsDelivery(),
                orderRequestDTO.getDeliveryAddress(), orderRequestDTO.getOrderDate(), orderRequestDTO.getCutlery(),
                orderRequestDTO.getPaymentKind(), orderRequestDTO.getIsTableOrder(), orderRequestDTO.getNotes(),
                orderRequestDTO.getDishes(), orderRequestDTO.getUserId(), orderRequestDTO.getSum());
        return orderRepository.save(newOrder).getOrderId();
    }

    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setCustomerPhone(orderRequestDTO.getCustomerPhone());
        order.setCustomerEmail(orderRequestDTO.getCustomerEmail());
        order.setIsDelivery(orderRequestDTO.getIsDelivery());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setOrderDate(orderRequestDTO.getOrderDate());
        order.setCutlery(orderRequestDTO.getCutlery());
        order.setPaymentKind(orderRequestDTO.getPaymentKind());
        order.setIsTableOrder(orderRequestDTO.getIsTableOrder());
        order.setNotes(orderRequestDTO.getNotes());
        order.setDishes(orderRequestDTO.getDishes());
        order.setUserId(order.getUserId());
        return new OrderResponseDTO(orderRepository.save(order));
    }

    public OrderResponseDTO deleteOrder(Long orderId) {
        Order orderById = getOrderById(orderId);
        Hibernate.initialize(orderById.getDishes());
        orderRepository.deleteById(orderId);
        return new OrderResponseDTO(orderById);
    }

    public List<OrderResponseDTO> getOrdersById(Long userId) {
        return orderRepository.findAllByUserId(userId).stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }
}
