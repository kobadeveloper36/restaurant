package com.progect.ui.services;

import com.progect.ui.rest.dto.dish.DishResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MainService {
    private final DishService dishService;
    private final CommentService commentService;

    private final OrderService orderService;

    private final UserService userService;

    public MainService(DishService dishService, CommentService commentService, OrderService orderService, UserService userService) {
        this.dishService = dishService;
        this.commentService = commentService;
        this.orderService = orderService;
        this.userService = userService;
    }

    public Set<DishResponseDTO> getPopularDishes() {
        Set<DishResponseDTO> allDishes = dishService.getAllDishesSet();
        return allDishes.stream()
                .filter(DishResponseDTO::getIs_Popular).collect(Collectors.toSet());
    }

    public Set<DishResponseDTO> getDishesByCategory(String category) {
        return dishService.getAllDishes().stream()
                .filter(x -> x.getCategory().equals(category)).collect(Collectors.toSet());
    }

    public Set<String> getCategoriesSet() {
        return dishService.getAllDishes().stream().map(DishResponseDTO::getCategory).collect(Collectors.toSet());
    }
}
