package com.progect.dish.controllers;

import com.progect.dish.controllers.dto.DishRequestDTO;
import com.progect.dish.controllers.dto.DishResponseDTO;
import com.progect.dish.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{dishId}")
    public DishResponseDTO getDish(@PathVariable Long dishId) {
        return new DishResponseDTO(dishService.getDishById(dishId));
    }

    @PostMapping("/")
    public Long createDish(@RequestBody DishRequestDTO dishRequestDTO) {
        return dishService.createDish(dishRequestDTO);
    }

    @PutMapping("/{dishId}")
    public DishResponseDTO updateDish(@PathVariable Long dishId, @RequestBody DishRequestDTO dishRequestDTO) {
        return dishService.updateDish(dishId, dishRequestDTO);
    }

    @DeleteMapping("/{dishId}")
    public DishResponseDTO deleteDish(@PathVariable Long dishId) {
        return dishService.deleteDish(dishId);
    }
}
