package com.progect.dish.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id", nullable = false)
    private Long dishId;

    @Column(name = "name", nullable = false, length = 50)
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

    @Column(name = "order_id")
    private Long orderId;

    public Dish(String name, Integer weight, String composition, String description,
                String category, Double price, Boolean isPopular, Long orderId) {
        this.name = name;
        this.weight = weight;
        this.composition = composition;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isPopular = isPopular;
        this.orderId = orderId;
    }

}
