package com.progect.ui.rest;

import com.progect.ui.rest.dto.dish.DishRequestDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "dish-service")
public interface DishServiceClient {
    @RequestMapping(value = "dishes/{dishId}", method = RequestMethod.GET)
    DishResponseDTO getDishById(@PathVariable Long dishId);

    @RequestMapping(value = "dishes/", method = RequestMethod.POST)
    Long createDish(DishRequestDTO dishRequestDTO);

    @RequestMapping(value = "dishes/{dishId}", method = RequestMethod.PUT)
    DishResponseDTO updateDishById(@PathVariable Long dishId, DishRequestDTO dishRequestDTO);

    @RequestMapping(value = "dishes/{dishId}", method = RequestMethod.DELETE)
    DishResponseDTO deleteDishById(@PathVariable Long dishId);

    @GetMapping("dishes/order/{orderId}")
    List<DishResponseDTO> getDishesById(@PathVariable Long orderId);

    @GetMapping("dishes/")
    List<DishResponseDTO> getAllDishes();
}
