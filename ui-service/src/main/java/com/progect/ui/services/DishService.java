package com.progect.ui.services;

import com.progect.ui.rest.DishServiceClient;
import com.progect.ui.rest.dto.dish.DishRequestDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DishService {
    private final DishServiceClient dishServiceClient;

    public DishService(DishServiceClient dishServiceClient) {
        this.dishServiceClient = dishServiceClient;
    }

    public List<DishResponseDTO> getAllDishes() {
        return dishServiceClient.getAllDishes();
    }

    public Set<DishResponseDTO> getAllDishesSet() {
        return new HashSet<>(getAllDishes());
    }

    public DishResponseDTO getDishById(Long dishId) {
        return dishServiceClient.getDishById(dishId);
    }

    public Long createDish(DishRequestDTO dishRequestDTO) {
        return dishServiceClient.createDish(dishRequestDTO);
    }

    public DishResponseDTO updateDishById(Long dishId, DishRequestDTO dishRequestDTO) {
        return dishServiceClient.updateDishById(dishId, dishRequestDTO);
    }

    public DishResponseDTO deleteDishById(Long dishId) {
        return dishServiceClient.deleteDishById(dishId);
    }
}
