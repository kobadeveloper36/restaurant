package com.progect.dish.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DishRequestDTO {
    private String name;
    private Integer weight;
    private String composition;
    private String description;
    private String category;
    private Double price;
    private Boolean is_Popular;
}
