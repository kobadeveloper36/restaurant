package com.progect.ui.rest.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDTO {
    private String name;
    private Integer weight;
    private String composition;
    private String description;
    private String category;
    private Double price;
    private Boolean is_Popular;
    private String img;
    private Long orderId;
}
