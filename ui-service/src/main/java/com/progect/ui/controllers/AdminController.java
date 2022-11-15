package com.progect.ui.controllers;

import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.services.CommentService;
import com.progect.ui.services.DishService;
import com.progect.ui.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final MainService mainService;

    private final DishService dishService;

    private final CommentService commentService;

    private Set<String> categories;

    public AdminController(Set<String> categories, MainService mainService, DishService dishService, CommentService commentService) {
        this.mainService = mainService;
        this.categories = mainService.getCategoriesSet();
        this.dishService = dishService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/comments")
    public String comments(Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        Set<CommentResponseDTO> comments = new HashSet<>(commentService.getAllComments());
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @GetMapping("/admin/{category}")
    public String dishesCategory(@PathVariable String category, Model model) {
        Set<String> categories = mainService.getCategoriesSet();
        model.addAttribute("categories", categories);

        Set<DishResponseDTO> dishes = mainService.getDishesByCategory(category);
        model.addAttribute("dishes", dishes);
        return "admin/dishesCategory";
    }

    @GetMapping("/admin/dish/page/edit/{dishId}")
    public String editDishPage(@PathVariable Long dishId, Model model) {
        Set<String> categories = mainService.getCategoriesSet();
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
        Set<String> categories = mainService.getCategoriesSet();
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
}
