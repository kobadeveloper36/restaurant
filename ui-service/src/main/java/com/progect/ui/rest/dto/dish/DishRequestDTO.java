package com.progect.ui.rest.dto.dish;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DishRequestDTO {
    private String name;
    private Integer weight;
    private String composition;
    private String description;
    private CategoryDTO category;
    private Double price;
    private Boolean is_Popular;
    private Long orderId;
}
