package com.progect.ui.controllers;

import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final MainService mainService;

    private final DishService dishService;

    private final CommentService commentService;

    private final OrderService orderService;

    private final UserService userService;

    private Set<String> categories;

    public AdminController(MainService mainService, DishService dishService, CommentService commentService, OrderService orderService, UserService userService) {
        this.mainService = mainService;
        this.categories = mainService.getCategoriesSet();
        this.dishService = dishService;
        this.commentService = commentService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/admin/orders")
    public String orders(Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        List<OrderResponseDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/admin/orders")
    public String searchedOrders(@RequestParam Long searchedId, Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        Set<OrderResponseDTO> orders = new HashSet<>(orderService.getAllOrders())
                .stream().filter(x -> x.getOrderId().equals(searchedId)).collect(Collectors.toSet());
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/admin/order/page/{orderId}")
    public String orderPage(@PathVariable Long orderId, Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        OrderResponseDTO order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<DishResponseDTO> orderDishes = orderService.getOrderDishes(order);
        model.addAttribute("orderDishes", orderDishes);
        return "admin/orderPresentPage";
    }

    @GetMapping("/admin/order/page/add/")
    public String orderPage(Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        model.addAttribute("orderId", getLastOrdersId(orderService.getAllOrders()) + 1);
        model.addAttribute("customers", userService.getAllUsers());
        model.addAttribute("customerName", "");
        model.addAttribute("customerPhone", "");
        model.addAttribute("customerEmail", "");
        model.addAttribute("isDelivery", false);
        model.addAttribute("isTableOrder", false);
        model.addAttribute("isPickup", false);
        model.addAttribute("deliveryAddress", "");
        model.addAttribute("orderDate", LocalDateTime.now());
        model.addAttribute("isCash", false);
        model.addAttribute("isCard", false);
        List<DishResponseDTO> allDishes = dishService.getAllDishes();
        Map<DishResponseDTO, Integer> orderedDishes = new HashMap<>();
        for (DishResponseDTO dish : allDishes) {
            orderedDishes.put(dish, 0);
        }
        model.addAttribute("orderedDishes", orderedDishes);
        model.addAttribute("orderNotes", "");
        model.addAttribute("orderSum", 0);
        model.addAttribute("actionPath", "/admin/order/add");
        return "admin/orderPage";
    }

    @GetMapping("/admin/comments")
    public String comments(Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        Set<CommentResponseDTO> comments = new HashSet<>(commentService.getAllComments());
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @PostMapping("/admin/comments")
    public String searchedComments(@RequestParam String searchedLogin, Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        Set<CommentResponseDTO> comments = new HashSet<>(commentService.getAllComments())
                .stream().filter(x -> x.getUserName().equals(searchedLogin.toLowerCase())).collect(Collectors.toSet());
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @GetMapping("/admin/comment/page/add")
    public String addCommentPage(Model model) {
        model.addAttribute("categories", categories);

        List<CommentResponseDTO> comments = commentService.getAllComments();
        long commentId = getLastCommentId(comments) + 1;
        model.addAttribute("commentId", commentId);
        LocalDate creationDate = LocalDate.now();
        model.addAttribute("creationDate", creationDate);
        String commentText = "";
        model.addAttribute("commentText", commentText);
        List<String> logins = userService.getAllUsers().stream()
                .map(UserResponseDTO::getLogin).collect(Collectors.toList());
        model.addAttribute("logins", logins);
        String actionPath = "/admin/comment/add";
        model.addAttribute("actionPath", actionPath);
        return "/admin/commentPage";
    }

    @GetMapping("/admin/comment/page/edit/{commentId}")
    public String editCommentPage(@PathVariable Long commentId, Model model) {
        model.addAttribute("categories", categories);

        CommentResponseDTO comment = commentService.getCommentById(commentId);
        model.addAttribute("commentId", commentId);
        model.addAttribute("creationDate", comment.getCreationDate());
        model.addAttribute("commentText", comment.getText());
        model.addAttribute("commentUserName", comment.getUserName());
        String actionPath = "/admin/comment/edit/" + comment.getCommentId();
        model.addAttribute("actionPath", actionPath);
        return "/admin/commentPage";
    }

    @GetMapping("/admin/{category}")
    public String dishesCategory(@PathVariable String category, Model model) {
        model.addAttribute("categories", categories);

        Set<DishResponseDTO> dishes = mainService.getDishesByCategory(category);
        model.addAttribute("dishes", dishes);
        return "admin/dishesCategory";
    }

    @GetMapping("/admin/dish/page/edit/{dishId}")
    public String editDishPage(@PathVariable Long dishId, Model model) {
        model.addAttribute("categories", categories);


        DishResponseDTO dish = dishService.getDishById(dishId);
        model.addAttribute("dishId", dishId);
        model.addAttribute("dishName", dish.getName());
        model.addAttribute("dishWeight", dish.getWeight());
        model.addAttribute("dishPrice", dish.getPrice());
        model.addAttribute("dishComposition", dish.getComposition());
        model.addAttribute("dishDescription", dish.getDescription());
        model.addAttribute("category", dish.getCategory());
        model.addAttribute("dishImage", dish.getImg());
        model.addAttribute("isPopular", dish.getIs_Popular());
        String actionPath = "/admin/dish/edit/" + dishId;
        model.addAttribute("actionPath", actionPath);
        return "/admin/dishPage";
    }

    @GetMapping("/admin/dish/page/add/{category}")
    public String addDishPage(@PathVariable String category, Model model) {
        model.addAttribute("categories", categories);


        List<DishResponseDTO> dishes = dishService.getAllDishes();
        long dishId = getLastDishId(dishes) + 1;
        model.addAttribute("dishId", dishId);
        String dishName = "";
        model.addAttribute("dishName", dishName);
        String dishWeight = "";
        model.addAttribute("dishWeight", dishWeight);
        String dishPrice = "";
        model.addAttribute("dishPrice", dishPrice);
        String dishComposition = "";
        model.addAttribute("dishComposition", dishComposition);
        String dishDescription = "";
        model.addAttribute("dishDescription", dishDescription);
        if (category.equals("none")) {
            model.addAttribute("category", "");
        } else {
            model.addAttribute("category", category);
        }
        boolean isPopular = false;
        model.addAttribute("isPopular", isPopular);
        String dishImage = "dish.png";
        model.addAttribute("dishImage", dishImage);
        String actionPath = "/admin/dish/add";
        model.addAttribute("actionPath", actionPath);
        return "/admin/dishPage";
    }

    private long getLastDishId(List<DishResponseDTO> dishes) {
        if (dishes.isEmpty()) {
            return 0;
        } else {
            return dishes.get(dishes.size() - 1).getDishId();
        }
    }

    private long getLastCommentId(List<CommentResponseDTO> comments) {
        if (comments.isEmpty()) {
            return 0;
        } else {
            return comments.get(comments.size() - 1).getCommentId();
        }
    }

    private long getLastOrdersId(List<OrderResponseDTO> orders) {
        if (orders.isEmpty()) {
            return 0;
        } else {
            return orders.get(orders.size() - 1).getOrderId();
        }
    }

    private long getLastUserId(List<UserResponseDTO> users) {
        if (users.isEmpty()) {
            return 0;
        } else {
            return users.get(users.size() - 1).getUserId();
        }
    }
}
