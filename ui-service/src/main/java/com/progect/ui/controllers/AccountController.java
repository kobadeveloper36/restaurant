package com.progect.ui.controllers;

import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AccountController {
    private final MainService mainService;
    private String userName;

    public AccountController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/account/{userId}")
    public String account(Model model, @PathVariable Long userId) {
        userName = mainService.getUserById(userId).getName();
        model.addAttribute("userName", userName);
        return "account";
    }

    @GetMapping("/account/{userId}/orders-account")
    public String ordersAccount(Model model, @PathVariable Long userId) {
        userName = mainService.getUserById(userId).getName();
        model.addAttribute("name", userName);
        List<OrderResponseDTO> orders = mainService.getOrdersById(userId);
        model.addAttribute("orders", orders);
        return "orders-account";
    }

    @GetMapping("/orders-account/order-info/{orderId}")
    public String orderInfo(Model model, @PathVariable Long orderId) {
        if (userName == null) {
            model.addAttribute("name", "Користувач");
        } else {
            model.addAttribute("name", userName);
        }
        OrderResponseDTO order = mainService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<DishResponseDTO> dishes = mainService.getDishesById(orderId);
        model.addAttribute("dishes", dishes);
        return "order-info";
    }
}
