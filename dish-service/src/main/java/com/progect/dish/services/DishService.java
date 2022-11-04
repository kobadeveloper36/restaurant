package com.progect.dish.services;

import com.progect.dish.controllers.dto.DishRequestDTO;
import com.progect.dish.controllers.dto.DishResponseDTO;
import com.progect.dish.entities.Dish;
import com.progect.dish.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId).orElseThrow(
                () -> new IllegalArgumentException("Dish with id: "+ dishId +"not found"));
    }

    public Long createDish(DishRequestDTO dishRequestDTO) {
        Dish newDish = new Dish(dishRequestDTO.getName(), dishRequestDTO.getWeight(),
                dishRequestDTO.getComposition(), dishRequestDTO.getDescription(),
                dishRequestDTO.getCategory(), dishRequestDTO.getPrice(), dishRequestDTO.getIs_Popular());
        return dishRepository.save(newDish).getDishId();
    }

    public DishResponseDTO updateDish(Long dishId, DishRequestDTO dishRequestDTO) {
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setName(dishRequestDTO.getName());
        dish.setWeight(dishRequestDTO.getWeight());
        dish.setDescription(dishRequestDTO.getDescription());
        dish.setComposition(dishRequestDTO.getComposition());
        dish.setCategory(dishRequestDTO.getCategory());
        dish.setPrice(dishRequestDTO.getPrice());
        dish.setIsPopular(dishRequestDTO.getIs_Popular());
        return new DishResponseDTO(dishRepository.save(dish));
    }

    public DishResponseDTO deleteDish(Long dishId) {
        Dish dishById = getDishById(dishId);
        dishRepository.deleteById(dishId);
        return new DishResponseDTO(dishById);
    }
}
