package com.progect.dish.controllers.dto;

import com.progect.dish.entities.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class DishResponseDTO {
    private Long dishId;
    private String name;
    private Integer weight;
    private String composition;
    private String description;
    private CategoryDTO category;
    private Double price;
    private Boolean is_Popular;
    private String img;

    public DishResponseDTO(Dish dish) {
        this.dishId = dish.getDishId();
        this.name = dish.getName();
        this.weight = dish.getWeight();
        this.composition = dish.getComposition();
        this.description = dish.getDescription();
        this.category = new CategoryDTO(dish.getCategory());
        this.price = dish.getPrice();
        this.is_Popular = dish.getIsPopular();
        this.img = dish.getImg();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishResponseDTO that = (DishResponseDTO) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(weight, that.weight)) return false;
        if (!Objects.equals(composition, that.composition)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (!Objects.equals(price, that.price)) return false;
        if (!Objects.equals(img, that.img)) return false;
        return Objects.equals(is_Popular, that.is_Popular);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (composition != null ? composition.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (is_Popular != null ? is_Popular.hashCode() : 0);
        return result;
    }
}
