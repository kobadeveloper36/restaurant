package com.progect.ui.controllers;

import com.progect.ui.rest.dto.dish.DishResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class OrderedDish {
    private DishResponseDTO dishResponseDTO;
    private int countOfDishes;

    public OrderedDish(DishResponseDTO dishResponseDTO) {
        this.dishResponseDTO = dishResponseDTO;
        countOfDishes = 1;
    }

    public void addDish() {
        countOfDishes++;
    }

    public void removeDish() {
        countOfDishes--;
    }

    public double getSum() {
        return countOfDishes * dishResponseDTO.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedDish that = (OrderedDish) o;

        return Objects.equals(dishResponseDTO, that.dishResponseDTO);
    }

    @Override
    public int hashCode() {
        return dishResponseDTO != null ? dishResponseDTO.hashCode() : 0;
    }


}
