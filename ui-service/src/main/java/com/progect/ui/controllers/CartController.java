package com.progect.ui.controllers;

import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    private final MainService mainService;
    private final List<OrderedDish> orderedDishes;

    public CartController(MainService mainService, List<OrderedDish> orderedDishes) {
        this.mainService = mainService;
        this.orderedDishes = orderedDishes;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("orderedDishes", orderedDishes);
        return "cart/cart";
    }

    @PostMapping("/cart/add/{dishId}")
    public String addToCart(@PathVariable Long dishId) {
        List<OrderedDish> dishes = orderedDishes.stream()
                .filter(x -> x.equals(new OrderedDish(mainService.getDishById(dishId))))
                .peek(OrderedDish::addDish).toList();
        if (dishes.get(0) != null) {
            dishes.get(0).addDish();
        } else {
            orderedDishes.add(new OrderedDish(mainService.getDishById(dishId)));
        }
        return "redirect: /cart";
    }

    @PostMapping("/cart/remove/{dishId}")
    public String removeOneFromCart(@PathVariable Long dishId) {
        OrderedDish dish = findDish(dishId);
        if (dish.getCountOfDishes() > 0) {
            orderedDishes.remove(dish);
        } else {
            dish.removeDish();
        }
        return "redirect: /cart";
    }

    @PostMapping("/cart/delete/{dishId}")
    public String removeFromCart(@PathVariable Long dishId) {
        orderedDishes.remove(findDish(dishId));
        return "redirect: /cart";
    }

    @GetMapping("/cart/pickup")
    public String pickup(Model model) {
        model.addAttribute("orderedDishes", orderedDishes);
        return "cart/pickup";
    }

    @PostMapping("/cart/pickup")
    public String createPickupOrder(@RequestParam String name, @RequestParam String phone, @RequestParam String email,
                                    @RequestParam String day, @RequestParam String time, @RequestParam String cutlery,
                                    @RequestParam String paymentKind, @RequestParam String notes) {
        boolean isDelivery = false;
        boolean isTableOrder = false;
        String deliveryAddress = null;
        List<Long> dishes = getDishesId();
        Long userId = null;
        Double sum = orderedDishes.stream().map(OrderedDish::getSum).reduce(0.0, Double::sum);
        mainService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        return "redirect: /";
    }

    @GetMapping("/cart/delivery")
    public String delivery(Model model) {
        model.addAttribute("orderedDishes", orderedDishes);
        return "cart/delivery";
    }

    @PostMapping("/cart/delivery")
    public String createDeliveryOrder(@RequestParam String name, @RequestParam String phone, @RequestParam String email,
                                      @RequestParam String street, @RequestParam String flat, @RequestParam String entry,
                                      @RequestParam String floor, @RequestParam String day, @RequestParam String time,
                                      @RequestParam String cutlery, @RequestParam String paymentKind,
                                      @RequestParam String notes) {
        boolean isDelivery = true;
        boolean isTableOrder = false;
        List<Long> dishes = getDishesId();
        Long userId = null;
        String deliveryAddress = "";
        if (entry != null) {
            deliveryAddress = street + ", " + flat + ", " + entry;
            if (floor != null) {
                deliveryAddress = deliveryAddress + ", " + floor;
            }
        }

        Double sum = orderedDishes.stream().map(OrderedDish::getSum).reduce(0.0, Double::sum);
        mainService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        return "redirect: /";
    }

    private List<Long> getDishesId() {
        List<Long> dishes = new ArrayList<>();
        int countOfDishes = 0;
        for (OrderedDish dish : orderedDishes) {
            countOfDishes = dish.getCountOfDishes();
            if (countOfDishes > 1) {
                for (int i = 0; i < countOfDishes; i++) {
                    dishes.add(dish.getDishResponseDTO().getDishId());
                }
            }
            dishes.add(dish.getDishResponseDTO().getDishId());
        }
        return dishes;
    }

    private OrderedDish findDish(Long dishId) {
        List<OrderedDish> dishes = orderedDishes.stream()
                .filter(x -> x.equals(new OrderedDish(mainService.getDishById(dishId))))
                .peek(OrderedDish::addDish).toList();
        return dishes.get(0);
    }
}
