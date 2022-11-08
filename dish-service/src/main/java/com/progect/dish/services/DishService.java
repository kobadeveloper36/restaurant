package com.progect.dish.services;

import com.progect.dish.controllers.dto.DishRequestDTO;
import com.progect.dish.controllers.dto.DishResponseDTO;
import com.progect.dish.entities.Dish;
import com.progect.dish.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId).orElseThrow(
                () -> new IllegalArgumentException("Dish with id: " + dishId + "not found"));
    }

    public Long createDish(DishRequestDTO dishRequestDTO) {
        Dish newDish = new Dish(dishRequestDTO.getName(), dishRequestDTO.getWeight(),
                dishRequestDTO.getComposition(), dishRequestDTO.getDescription(),
                dishRequestDTO.getCategory().getCategory().name(), dishRequestDTO.getPrice(),
                dishRequestDTO.getImg(), dishRequestDTO.getIs_Popular(), dishRequestDTO.getOrderId());
        return dishRepository.save(newDish).getDishId();
    }

    public DishResponseDTO updateDish(Long dishId, DishRequestDTO dishRequestDTO) {
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setName(dishRequestDTO.getName());
        dish.setWeight(dishRequestDTO.getWeight());
        dish.setDescription(dishRequestDTO.getDescription());
        dish.setComposition(dishRequestDTO.getComposition());
        dish.setCategory(dishRequestDTO.getCategory().toString());
        dish.setPrice(dishRequestDTO.getPrice());
        dish.setIsPopular(dishRequestDTO.getIs_Popular());
        return new DishResponseDTO(dishRepository.save(dish));
    }

    public DishResponseDTO deleteDish(Long dishId) {
        Dish dishById = getDishById(dishId);
        dishRepository.deleteById(dishId);
        return new DishResponseDTO(dishById);
    }

    public List<DishResponseDTO> getDishesById(Long orderId) {
        return dishRepository.findAllByOrderId(orderId).stream().map(DishResponseDTO::new).collect(Collectors.toList());
    }

    public Set<DishResponseDTO> getAllDishes() {
        return dishRepository.findAll().stream().map(DishResponseDTO::new).collect(Collectors.toSet());
    }
}
