package com.progect.ui.rest.dto.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.progect.ui.rest.dto.dish.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CategoryDTO {
    @Getter
    @Setter
    @JsonProperty(value = "category")
    private Category category;

    public CategoryDTO(String category) {
        this.category = Category.valueOf(category);
    }
}
