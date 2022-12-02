package com.progect.ui.controllers;

import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.security.UserDetailsImpl;
import com.progect.ui.services.CommentService;
import com.progect.ui.services.MainService;
import com.progect.ui.services.OrderService;
import com.progect.ui.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {
    private final MainService mainService;

    private final OrderService orderService;
    private final CommentService commentService;

    private final UserService userService;

    public MainController(MainService mainService, OrderService orderService, CommentService commentService, UserService userService) {
        this.mainService = mainService;
        this.orderService = orderService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());
        Set<DishResponseDTO> popular = mainService.getPopularDishes();
        model.addAttribute("popular", popular);
        return "index";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);
        HashMap<String, Set<DishResponseDTO>> categoriesWithDishes = new HashMap<>();
        for (String category : categories) {
            categoriesWithDishes.put(category, mainService.getDishesByCategory(category));
        }
        model.addAttribute("categoriesWithDishes", categoriesWithDishes);
        return "menu";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        if (userDetails != null) {
            UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
            model.addAttribute("name", userResponseDTO.getName());
            model.addAttribute("phone", userResponseDTO.getPhone());
            model.addAttribute("email", userResponseDTO.getEmail());
        }

        Map<UserResponseDTO, CommentResponseDTO> comments = new HashMap<>();
        for (CommentResponseDTO commentResponseDTO : commentService.getAllComments()) {
            comments.put(userService.getUserByLogin(commentResponseDTO.getUserName()), commentResponseDTO);
        }
        model.addAttribute("comments", comments);
        return "aboutUs";
    }

    @PostMapping("/aboutUs")
    public String createTableOrder(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @RequestParam String name, @RequestParam String phone, @RequestParam String email, Model model) {
        boolean isDelivery = false;
        boolean isTableOrder = true;
        String deliveryAddress = null;
        List<Long> dishes = null;
        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        Long userId;
        if (userResponseDTO != null) {
            userId = userResponseDTO.getUserId();
        } else {
            userId = null;
        }

        Double sum = null;
        String cutlery = null;
        String paymentKind = null;
        String notes = null;
        orderService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                null, cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        return "redirect:/aboutUs";
    }

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        model.addAttribute("name", userResponseDTO.getName());
        model.addAttribute("userRole", userResponseDTO.getRole());
        return "account";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());
        return "admin";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
