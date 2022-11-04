package com.progect.dish.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id", nullable = false)
    private Long dishId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "composition", nullable = false)
    private String composition;

    @Lob
    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "is_popular", nullable = false)
    private Boolean isPopular = false;

    public Dish(String name, Integer weight, String composition, String description,
                String category, Double price, Boolean isPopular) {
        this.name = name;
        this.weight = weight;
        this.composition = composition;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isPopular = isPopular;
    }

    public Dish() {
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }
}
