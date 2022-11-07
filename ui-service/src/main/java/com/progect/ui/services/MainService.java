package com.progect.ui.services;

import com.progect.ui.rest.CommentServiceClient;
import com.progect.ui.rest.DishServiceClient;
import com.progect.ui.rest.OrderServiceClient;
import com.progect.ui.rest.UserServiceClient;
import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.dish.enums.Category;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MainService {
    private final DishServiceClient dishServiceClient;

    private final CommentServiceClient commentServiceClient;

    private final OrderServiceClient orderServiceClient;

    private final UserServiceClient userServiceClient;

    public MainService(DishServiceClient dishServiceClient, CommentServiceClient commentServiceClient, OrderServiceClient orderServiceClient, UserServiceClient userServiceClient) {
        this.dishServiceClient = dishServiceClient;
        this.commentServiceClient = commentServiceClient;
        this.orderServiceClient = orderServiceClient;
        this.userServiceClient = userServiceClient;
    }

    public Set<DishResponseDTO> getPopularDishes() {
        Set<DishResponseDTO> allDishes = dishServiceClient.getAllDishes();
        return allDishes.stream()
                .filter(DishResponseDTO::getIs_Popular).collect(Collectors.toSet());
    }

    public List<DishResponseDTO> getDishesById(Long dishId) {
        return dishServiceClient.getDishesById(dishId);
    }

    public Set<DishResponseDTO> getDishesByCategory(Category category) {
        return dishServiceClient.getAllDishes().stream()
                .filter(x -> x.getCategory().getCategory().equals(category)).collect(Collectors.toSet());
    }

    public List<CommentResponseDTO> getAllComments() {
        return commentServiceClient.getAllComments();
    }

    public UserResponseDTO getUserById(Long userId) {
        return userServiceClient.getUserById(userId);
    }

    public List<CommentResponseDTO> getCommentsById(Long userId) {
        return commentServiceClient.getCommentsById(userId);
    }

    public List<OrderResponseDTO> getOrdersById(Long userId) {
        return orderServiceClient.getOrdersById(userId);
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        return orderServiceClient.getOrderById(orderId);
    }

    public DishResponseDTO getDishById(Long dishId) {
        return dishServiceClient.getDishById(dishId);
    }

    public void createOrder(OrderRequestDTO orderRequestDTO) {
        orderServiceClient.createOrder(orderRequestDTO);
    }
}
