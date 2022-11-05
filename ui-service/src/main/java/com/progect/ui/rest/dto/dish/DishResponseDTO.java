package com.progect.ui.rest.dto.dish;

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
}
