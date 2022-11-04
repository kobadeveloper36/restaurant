package com.progect.dish.controllers.dto;

import com.progect.dish.entities.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DishResponseDTO {
    private Long dishId;
    private String name;
    private Integer weight;
    private String composition;
    private String description;
    private String category;
    private Double price;
    private Boolean is_Popular;

    public DishResponseDTO(Dish dish) {
        this.dishId = dish.getDishId();
        this.name = dish.getName();
        this.weight = dish.getWeight();
        this.composition = dish.getComposition();
        this.description = dish.getDescription();
        this.category = dish.getCategory();
        this.price = dish.getPrice();
        this.is_Popular = dish.getIsPopular();
    }

}
