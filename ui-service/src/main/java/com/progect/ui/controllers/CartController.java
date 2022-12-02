package com.progect.ui.controllers;

import com.progect.ui.rest.dto.comment.CommentRequestDTO;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.security.UserDetailsImpl;
import com.progect.ui.services.CommentService;
import com.progect.ui.services.DishService;
import com.progect.ui.services.MainService;
import com.progect.ui.services.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class CartController {
    private final MainService mainService;
    private final DishService dishService;
    private final OrderService orderService;
    private final CommentService commentService;
    private List<OrderedDish> orderedDishes;

    public CartController(MainService mainService, DishService dishService, OrderService orderService, CommentService commentService, List<OrderedDish> orderedDishes) {
        this.mainService = mainService;
        this.dishService = dishService;
        this.orderService = orderService;
        this.commentService = commentService;
        this.orderedDishes = orderedDishes;
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        model.addAttribute("orderedDishes", orderedDishes);
        if (userDetails != null) {
            UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
            model.addAttribute("name", userResponseDTO.getName());
            model.addAttribute("phone", userResponseDTO.getPhone());
            model.addAttribute("email", userResponseDTO.getEmail());
            model.addAttribute("address", userResponseDTO.getAddress());
            model.addAttribute("flat", userResponseDTO.getFlat());
            model.addAttribute("entry", userResponseDTO.getEntry());
            model.addAttribute("floor", userResponseDTO.getFloor());
        }

        double sum = orderedDishes.stream().map(OrderedDish::getSum).reduce(0.0, Double::sum);
        model.addAttribute("sum", sum);
        List<String> orderTimes = new ArrayList<>();
        LocalTime timeNow = LocalTime.now();
        IntStream.rangeClosed(1, 4).forEach(x ->
                orderTimes.add(timeNow.plusMinutes(40L * x).format(DateTimeFormatter.ofPattern("hh:mm"))));
        model.addAttribute("orderTimes", orderTimes);
        return "cart/cart";
    }

    @GetMapping("/cart/add/{dishId}")
    public String addToCart(@PathVariable Long dishId) {
        OrderedDish orderedDish = new OrderedDish(dishService.getDishById(dishId));
        if (orderedDishes.contains(orderedDish)) {
            orderedDishes.get(orderedDishes.indexOf(orderedDish)).addDish();
            return "redirect:/cart";
        } else {
            orderedDishes.add(orderedDish);
            return "redirect:/cart";
        }
    }

    @GetMapping("/cart/remove/{dishId}")
    public String removeOneFromCart(@PathVariable Long dishId) {
        OrderedDish orderedDish = new OrderedDish(dishService.getDishById(dishId));
        int index = orderedDishes.indexOf(orderedDish);
        if (orderedDishes.contains(orderedDish)) {
            if (orderedDishes.get(index).getCountOfDishes() == 1) {
                orderedDishes.remove(orderedDish);
            } else {
                orderedDishes.get(index).removeDish();
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{dishId}")
    public String removeFromCart(@PathVariable Long dishId) {
        OrderedDish orderedDish = new OrderedDish(dishService.getDishById(dishId));
        orderedDishes.remove(orderedDish);
        return "redirect:/cart";
    }

    @PostMapping("/cart/pickup")
    public String createPickupOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam String name, @RequestParam String phone, @RequestParam String email,
                                    @RequestParam String day, @RequestParam String time, @RequestParam String cutlery,
                                    @RequestParam String paymentKind, @RequestParam String notes) {
        if (orderedDishes.isEmpty()) {
            return "redirect:/menu";
        }
        boolean isDelivery = false;
        boolean isTableOrder = false;
        String deliveryAddress = null;
        LocalDate date = switch (day) {
            case "1" -> LocalDate.now();
            case "2" -> LocalDate.now().plusDays(1);
            default -> throw new IllegalStateException("Unexpected value: " + day);
        };
        LocalDateTime orderDate =
                LocalDateTime.of(date, LocalTime.parse(time));
        List<Long> dishes = getDishesId();
        Long userId = null;
        Double sum = orderedDishes.stream().map(OrderedDish::getSum).reduce(0.0, Double::sum);
        Long orderId = orderService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                orderDate, cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        orderedDishes = new ArrayList<>();
        if (userDetails != null) {
            return "redirect:/cart/addComment/" + orderId;
        }
        return "cart/orderConfirmed";
    }

    @PostMapping("/cart/delivery")
    public String createDeliveryOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestParam String name, @RequestParam String phone, @RequestParam String email,
                                      @RequestParam String street, @RequestParam String flat, @RequestParam String entry,
                                      @RequestParam String floor, @RequestParam String cutlery,
                                      @RequestParam String paymentKind, @RequestParam String notes) {
        if (orderedDishes.isEmpty()) {
            return "redirect:/menu";
        }
        boolean isDelivery = true;
        boolean isTableOrder = false;
        List<Long> dishes = getDishesId();
        Long userId = null;
        String deliveryAddress = "";
        if (entry != null) {
            deliveryAddress = street + ", " + flat;
            if (entry.equals("")) {
                deliveryAddress = deliveryAddress + ", " + entry;
                if (floor.equals("")) {
                    deliveryAddress = deliveryAddress + ", " + floor;
                }
            }
        }
        Double sum = orderedDishes.stream().map(OrderedDish::getSum).reduce(0.0, Double::sum);
        Long orderId = orderService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                LocalDateTime.now(), cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        orderedDishes = new ArrayList<>();
        if (userDetails != null) {
            return "redirect:/cart/addComment/" + orderId;
        }
        return "cart/orderConfirmed";
    }

    @GetMapping("/cart/addComment/{orderId}")
    public String addComment(@PathVariable Long orderId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());
        model.addAttribute("orderId", orderId);
        return "cart/addComment";
    }

    @PostMapping("/cart/addComment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestParam String text) {
        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        commentService.createComment(new CommentRequestDTO(text, userResponseDTO.getLogin(), LocalDate.now()));
        return "redirect:/";
    }

    private List<Long> getDishesId() {
        List<Long> dishes = new ArrayList<>();
        int countOfDishes;
        for (OrderedDish dish : orderedDishes) {
            countOfDishes = dish.getCountOfDishes();
            if (countOfDishes > 1) {
                for (int i = 0; i < countOfDishes; i++) {
                    dishes.add(dish.getDishResponseDTO().getDishId());
                }
            } else {
                dishes.add(dish.getDishResponseDTO().getDishId());
            }
        }
        return dishes;
    }
}
