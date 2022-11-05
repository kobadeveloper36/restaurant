package com.progect.dish.repository;

import com.progect.dish.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByOrderId(Long orderId);
}