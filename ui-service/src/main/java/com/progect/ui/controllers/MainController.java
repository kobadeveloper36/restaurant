package com.progect.ui.controllers;

import com.progect.ui.rest.dto.comment.CommentResponseDTO;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.dish.enums.Category;
import com.progect.ui.rest.dto.order.OrderRequestDTO;
import com.progect.ui.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Set<DishResponseDTO> popular = mainService.getPopularDishes();
        model.addAttribute("popular", popular);
        return "index";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        Set<DishResponseDTO> soups = mainService.getDishesByCategory(Category.SOUPS);
        model.addAttribute("soups", soups);
        Set<DishResponseDTO> meat = mainService.getDishesByCategory(Category.MEAT);
        model.addAttribute("meat", meat);
        Set<DishResponseDTO> fowl = mainService.getDishesByCategory(Category.FOWL);
        model.addAttribute("fowl", fowl);
        Set<DishResponseDTO> seafood = mainService.getDishesByCategory(Category.SEAFOOD);
        model.addAttribute("seafood", seafood);
        Set<DishResponseDTO> handmade = mainService.getDishesByCategory(Category.HANDMADE);
        model.addAttribute("handmade", handmade);
        Set<DishResponseDTO> noodles = mainService.getDishesByCategory(Category.NOODLES);
        model.addAttribute("noodles", noodles);
        Set<DishResponseDTO> sideDish = mainService.getDishesByCategory(Category.SIDE_DISH);
        model.addAttribute("sideDish", sideDish);
        Set<DishResponseDTO> salads = mainService.getDishesByCategory(Category.SALADS);
        model.addAttribute("salads", salads);
        Set<DishResponseDTO> snacks = mainService.getDishesByCategory(Category.SNACKS);
        model.addAttribute("snacks", snacks);
        Set<DishResponseDTO> desserts = mainService.getDishesByCategory(Category.DESSERTS);
        model.addAttribute("desserts", desserts);
        return "menu";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model) {
        List<CommentResponseDTO> comments = mainService.getAllComments();
        model.addAttribute("comments", comments);
        return "aboutUs";
    }

    @PostMapping("/aboutUs")
    public String createTableOrder(@RequestParam String name, @RequestParam String phone, @RequestParam String email, Model model) {
        boolean isDelivery = false;
        boolean isTableOrder = true;
        String deliveryAddress = null;
        List<Long> dishes = null;
        Long userId = null;
        Double sum = null;
        String cutlery = null;
        String paymentKind = null;
        String notes = null;
        mainService.createOrder(new OrderRequestDTO(name, phone, email, isDelivery, deliveryAddress,
                null, cutlery, paymentKind, isTableOrder, notes, dishes, userId, sum));
        return "redirect:/aboutUs";
    }
}
