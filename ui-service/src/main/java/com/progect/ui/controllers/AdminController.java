package com.progect.ui.controllers;

import com.progect.ui.UiApplication;
import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.rest.dto.user.UsersRoles;
import com.progect.ui.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
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

    private final String uploadPath;

    public AdminController(MainService mainService, DishService dishService, CommentService commentService, OrderService orderService, UserService userService) {
        this.mainService = mainService;
        this.dishService = dishService;
        this.commentService = commentService;
        this.orderService = orderService;
        this.userService = userService;
        String location = UiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        this.uploadPath = location.substring(0, location.indexOf("file:")) + "/restaurant/uploads/img/";
    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        List<UserResponseDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/admin/users")
    public String searchedUsers(@RequestParam String searchedLogin, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        Set<UserResponseDTO> users = new HashSet<>(userService.getAllUsers())
                .stream().filter(x -> x.getLogin().equals(searchedLogin.toLowerCase())).collect(Collectors.toSet());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/admin/user/page/add/")
    public String addUser(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        model.addAttribute("userId", getLastUserId(userService.getAllUsers()) + 1);
        model.addAttribute("userName", "");
        model.addAttribute("userPhone", "");
        model.addAttribute("userEmail", "");
        model.addAttribute("userAddress", "");
        model.addAttribute("userFlat", "");
        model.addAttribute("userEntrance", "");
        model.addAttribute("userFloor", "");
        model.addAttribute("userLogin", "");
        model.addAttribute("roles", UsersRoles.values());
        model.addAttribute("actionPath", "/admin/user/add");
        String userImage = "user.png";
        model.addAttribute("userImage", userImage);
        model.addAttribute("comments", new ArrayList<CommentResponseDTO>());
        model.addAttribute("orders", new ArrayList<OrderResponseDTO>());

        return "admin/userPage";
    }

    @GetMapping("/admin/user/page/edit/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        UserResponseDTO user = userService.getUserById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", user.getName());
        model.addAttribute("userPhone", user.getPhone());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userAddress", user.getAddress());
        model.addAttribute("userFlat", user.getFlat());
        model.addAttribute("userEntrance", user.getEntry());
        model.addAttribute("userFloor", user.getFloor());
        model.addAttribute("userLogin", user.getLogin());
        List<UsersRoles> roles = new ArrayList<>();
        UsersRoles userRole = UsersRoles.valueOf(user.getRole());
        roles.add(userRole);
        List<UsersRoles> allroles = new ArrayList<>(Arrays.stream(UsersRoles.values()).toList());
        allroles.remove(userRole);
        roles.addAll(allroles);
        model.addAttribute("roles", roles);
        model.addAttribute("actionPath", "/admin/user/edit/" + userId);
        String imgFile = user.getImgFile();
        if (imgFile.equals("user.png")) {
            model.addAttribute("userImage", "user.png");
        } else {
            if (new File(uploadPath + "users/" + imgFile).exists()) {
                model.addAttribute("userImage", imgFile);
            } else {
                model.addAttribute("userImage", "user.png");
            }
        }
        model.addAttribute("comments", commentService.getCommentsByUserName(user.getLogin()));
        model.addAttribute("orders", orderService.getOrdersById(userId));

        return "admin/userPage";
    }

    @GetMapping("/admin/orders")
    public String orders(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        List<OrderResponseDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/admin/orders")
    public String searchedOrders(@RequestParam Long searchedId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        Set<OrderResponseDTO> orders = new HashSet<>(orderService.getAllOrders())
                .stream().filter(x -> x.getOrderId().equals(searchedId)).collect(Collectors.toSet());
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/admin/order/page/{orderId}")
    public String orderPage(@PathVariable Long orderId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        OrderResponseDTO order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<DishResponseDTO> orderDishes = orderService.getOrderDishes(order);
        model.addAttribute("orderDishes", orderDishes);
        return "admin/orderPresentPage";
    }

    @GetMapping("/admin/order/page/add/")
    public String orderAddingPage(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        model.addAttribute("orderId", AdminController.getLastOrdersId(orderService.getAllOrders()) + 1);
        model.addAttribute("customers", userService.getAllUsers());
        model.addAttribute("customerName", "");
        model.addAttribute("customerPhone", "");
        model.addAttribute("customerEmail", "");
        model.addAttribute("isDelivery", false);
        model.addAttribute("isTableOrder", false);
        model.addAttribute("isPickup", false);
        model.addAttribute("deliveryAddress", "");
        model.addAttribute("orderDate", LocalDateTime.now().toString());
        model.addAttribute("isCash", false);
        model.addAttribute("isCard", false);
        List<DishResponseDTO> allDishes = dishService.getAllDishes();
        Map<DishResponseDTO, Integer> orderedDishes = new HashMap<>();
        for (DishResponseDTO dish : allDishes) {
            orderedDishes.put(dish, 0);
        }
        model.addAttribute("orderedDishes", orderedDishes);
        model.addAttribute("orderNotes", "");
        model.addAttribute("actionPath", "/admin/order/add");
        return "admin/orderPage";
    }

    @GetMapping("/admin/order/page/edit/{orderId}")
    public String orderEditingPage(@PathVariable Long orderId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        OrderResponseDTO order = orderService.getOrderById(orderId);
        model.addAttribute("orderId", orderId);
        List<UserResponseDTO> users = userService.getAllUsers();
        List<UserResponseDTO> customers = new ArrayList<>();
        if (order.getUserId() != null) {
            UserResponseDTO user = userService.getUserById(order.getUserId());
            users.remove(user);
            customers.add(user);
            customers.add(new UserResponseDTO(0L, "Неавторизований користувач"));
            customers.addAll(users);
        } else {
            customers.add(new UserResponseDTO(0L, "Неавторизований користувач"));
            customers.addAll(userService.getAllUsers());
        }
        model.addAttribute("customers", customers);
        model.addAttribute("customerName", order.getCustomerName());
        model.addAttribute("customerPhone", order.getCustomerPhone());
        model.addAttribute("customerEmail", order.getCustomerEmail());
        boolean isDelivery = order.getIsDelivery();
        Boolean isTableOrder = order.getIsTableOrder();
        boolean isPickup = !isDelivery && !isTableOrder;
        model.addAttribute("isDelivery", isDelivery);
        model.addAttribute("isTableOrder", isTableOrder);
        model.addAttribute("isPickup", isPickup);
        model.addAttribute("deliveryAddress", order.getDeliveryAddress());
        model.addAttribute("orderDate", order.getOrderDate().toString());
        boolean isCash = false;
        boolean isCard = false;
        String paymentKind = order.getPaymentKind();
        if (paymentKind != null) {
            if (paymentKind.equals("Готівкою")) {
                isCash = true;
            } else {
                isCard = true;
            }
        } else {
            isCard = true;
        }
        model.addAttribute("isCash", isCash);
        model.addAttribute("isCard", isCard);
        List<DishResponseDTO> allDishes = dishService.getAllDishes();
        Map<DishResponseDTO, Integer> orderedDishes = new HashMap<>();
        for (DishResponseDTO dish : allDishes) {
            orderedDishes.put(dish,
                    order.getDishes().stream().filter(x -> x.equals(dish.getDishId())).toList().size());
        }
        model.addAttribute("orderedDishes", orderedDishes);
        model.addAttribute("orderNotes", order.getNotes());
        model.addAttribute("actionPath", "/admin/order/edit/" + orderId);
        return "admin/orderPage";
    }

    @GetMapping("/admin/comments")
    public String comments(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        Set<CommentResponseDTO> comments = new HashSet<>(commentService.getAllComments());
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @PostMapping("/admin/comments")
    public String searchedComments(@RequestParam String searchedLogin, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        Set<CommentResponseDTO> comments = new HashSet<>(commentService.getAllComments())
                .stream().filter(x -> x.getUserName().equals(searchedLogin.toLowerCase())).collect(Collectors.toSet());
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @GetMapping("/admin/comment/page/add")
    public String addCommentPage(Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        List<CommentResponseDTO> comments = commentService.getAllComments();
        long commentId = AdminController.getLastCommentId(comments) + 1;
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
        return "admin/commentPage";
    }

    @GetMapping("/admin/comment/page/edit/{commentId}")
    public String editCommentPage(@PathVariable Long commentId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        CommentResponseDTO comment = commentService.getCommentById(commentId);
        model.addAttribute("commentId", commentId);
        model.addAttribute("creationDate", comment.getCreationDate());
        model.addAttribute("commentText", comment.getText());
        List<String> users = new ArrayList<>(userService.getAllUsers().stream().map(UserResponseDTO::getLogin).toList());
        List<String> logins = new ArrayList<>();
        if (comment.getUserName() != null) {
            String user = comment.getUserName();
            users.remove(user);
            logins.add(user);
            logins.addAll(users);
        } else {
            logins = userService.getAllUsers().stream().map(UserResponseDTO::getLogin).toList();
        }
        model.addAttribute("logins", logins);

        String actionPath = "/admin/comment/edit/" + comment.getCommentId();
        model.addAttribute("actionPath", actionPath);
        return "admin/commentPage";
    }

    @GetMapping("/admin/{category}")
    public String dishesCategory(@PathVariable String category, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());

        Set<DishResponseDTO> dishes = mainService.getDishesByCategory(category);
        model.addAttribute("dishes", dishes);
        return "admin/dishesCategory";
    }

    @GetMapping("/admin/dish/page/edit/{dishId}")
    public String editDishPage(@PathVariable Long dishId, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());


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
        return "admin/dishPage";
    }

    @GetMapping("/admin/dish/page/add/{category}")
    public String addDishPage(@PathVariable String category, Model model) {
        model.addAttribute("categories", mainService.getCategoriesSet());


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
        return "admin/dishPage";
    }

    private long getLastDishId(List<DishResponseDTO> dishes) {
        if (dishes.isEmpty()) {
            return 0;
        } else {
            return dishes.get(dishes.size() - 1).getDishId();
        }
    }

    public static long getLastCommentId(List<CommentResponseDTO> comments) {
        if (comments.isEmpty()) {
            return 0;
        } else {
            return comments.get(comments.size() - 1).getCommentId();
        }
    }

    public static long getLastOrdersId(List<OrderResponseDTO> orders) {
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
