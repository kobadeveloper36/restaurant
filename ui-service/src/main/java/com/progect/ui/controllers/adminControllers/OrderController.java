package com.progect.ui.controllers.adminControllers;

import com.progect.ui.controllers.AdminController;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/admin/order/add")
    public String addOrder(@RequestParam Long customerId, @RequestParam String customerName,
                           @RequestParam String customerPhone, @RequestParam String customerEmail,
                           @RequestParam String orderType, @RequestParam String deliveryAddress,
                           @RequestParam String orderDate, @RequestParam String cutlery,
                           @RequestParam String paymentKind, @RequestParam List<Long> dishesIds,
                           @RequestParam List<Integer> dishesQuantities, @RequestParam String notes) {
        boolean isDelivery = false;
        boolean isTableOrder = false;
        switch (orderType) {
            case "Доставка" -> {
                isDelivery = true;
            }
            case "Замовлення столика" -> {
                isTableOrder = true;
                deliveryAddress = null;
            }
            case "Самовивіз" -> deliveryAddress = null;
        }
        List<Long> dishes = new ArrayList<>();
        for (int i = 0; i < dishesQuantities.size(); i++) {
            if (dishesQuantities.get(i) > 0) {
                for (int j = 0; j < dishesQuantities.get(i); j++) {
                    dishes.add(dishesIds.get(i));
                }
            }
        }
        if (customerId == 0) {
            customerId = null;
        } else {
            orderService.setOrderToUserById(customerId,
                    AdminController.getLastOrdersId(orderService.getAllOrders()) + 1);
        }
        Double orderSum = orderService.getOrderSum(dishes);
        OrderRequestDTO newOrder = new OrderRequestDTO(customerName, customerPhone, customerEmail, isDelivery,
                deliveryAddress, LocalDateTime.parse(orderDate), cutlery,
                paymentKind, isTableOrder, notes,
                dishes, customerId, orderSum);
        orderService.createOrder(newOrder);
        return "redirect:/admin/orders";
    }

    @PostMapping("/admin/order/edit/{orderId}")
    public String editOrder(@PathVariable Long orderId, @RequestParam Long customerId,
                            @RequestParam String customerName, @RequestParam String customerPhone,
                            @RequestParam String customerEmail, @RequestParam String orderType,
                            @RequestParam String deliveryAddress, @RequestParam String orderDate,
                            @RequestParam String cutlery, @RequestParam String paymentKind,
                            @RequestParam List<Long> dishesIds, @RequestParam List<Integer> dishesQuantities,
                            @RequestParam String notes) {

        boolean isDelivery = false;
        boolean isTableOrder = false;
        switch (orderType) {
            case "Доставка" -> isDelivery = true;
            case "Замовлення столика" -> {
                isTableOrder = true;
                deliveryAddress = null;
            }
            case "Самовивіз" -> deliveryAddress = null;
        }
        List<Long> dishes = new ArrayList<>();
        for (int i = 0; i < dishesQuantities.size(); i++) {
            if (dishesQuantities.get(i) > 0) {
                for (int j = 0; j < dishesQuantities.get(i); j++) {
                    dishes.add(dishesIds.get(i));
                }
            }
        }
        if (customerId == 0) {
            customerId = null;
        } else {
            orderService.setOrderToUserById(customerId, orderId);
        }
        Double orderSum = orderService.getOrderSum(dishes);
        OrderRequestDTO order = new OrderRequestDTO(customerName, customerPhone, customerEmail, isDelivery,
                deliveryAddress, LocalDateTime.parse(orderDate), cutlery,
                paymentKind, isTableOrder, notes,
                dishes, customerId, orderSum);
        OrderResponseDTO updateOrder = orderService.updateOrder(orderId, order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/order/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return "redirect:/admin/orders";
    }
}
