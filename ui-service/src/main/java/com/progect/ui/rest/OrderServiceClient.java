package com.progect.ui.rest;

import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @RequestMapping(value = "orders/{orderId}", method = RequestMethod.GET)
    OrderResponseDTO getOrderById(@PathVariable Long orderId);

    @RequestMapping(value = "orders/", method = RequestMethod.POST)
    Long createOrder(OrderRequestDTO orderRequestDTO);

    @RequestMapping(value = "orders/{orderId}", method = RequestMethod.PUT)
    OrderResponseDTO updateOrderById(@PathVariable Long orderId, OrderRequestDTO orderRequestDTO);

    @RequestMapping(value = "orders/{orderId}", method = RequestMethod.DELETE)
    OrderResponseDTO deleteOrderById(@PathVariable Long orderId);

    @GetMapping("orders/user/{userId}")
    List<OrderResponseDTO> getOrdersById(@PathVariable Long userId);

    @GetMapping("orders/")
    List<OrderResponseDTO> getAllOrders();
}
