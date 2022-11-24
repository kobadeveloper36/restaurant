package com.progect.ui.controllers;

import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.security.UserDetailsImpl;
import com.progect.ui.services.DishService;
import com.progect.ui.services.MainService;
import com.progect.ui.services.OrderService;
import com.progect.ui.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class AccountController {
    private final OrderService orderService;
    private final MainService mainService;
    private Set<String> categories;


    public AccountController(OrderService orderService, MainService mainService) {
        this.mainService = mainService;
        this.orderService = orderService;
        this.categories = mainService.getCategoriesSet();
    }

    @GetMapping("/account/orders-account")
    public String ordersAccount(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", categories);

        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        model.addAttribute("name", userResponseDTO.getName());
        List<OrderResponseDTO> orders = orderService.getOrdersById(userResponseDTO.getUserId());
        model.addAttribute("orders", orders);
        return "account/orders-account";
    }

    @GetMapping("/account/orders-account/order-info/{orderId}")
    public String orderInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long orderId,
                            Model model) {
        model.addAttribute("categories", categories);

        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        model.addAttribute("name", userResponseDTO.getName());
        OrderResponseDTO order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<DishResponseDTO> dishes = orderService.getOrderDishes(order);
        model.addAttribute("dishes", dishes);
        return "account/order-info";
    }
}
