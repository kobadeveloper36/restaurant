package com.progect.ui.controllers.entitiesControllers;

import com.progect.ui.UiApplication;
import com.progect.ui.rest.dto.dish.DishRequestDTO;
import com.progect.ui.services.DishService;
import com.progect.ui.services.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class DishController {
    private final MainService mainService;

    private final DishService dishService;

    private final String uploadPath;

    public DishController(MainService mainService, DishService dishService) {
        this.mainService = mainService;
        this.dishService = dishService;
        String location = UiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        this.uploadPath = location.substring(0, location.indexOf("/build")) + "/uploads/img/dishes/";
    }

    @PostMapping("/admin/dish/add")
    public String addDish(@RequestParam("imgFile") MultipartFile imgFile,
                          @RequestParam String name, @RequestParam Integer weight,
                          @RequestParam Double price, @RequestParam String category,
                          @RequestParam String composition, @RequestParam String description,
                          @RequestParam(defaultValue = "false") boolean isPopular) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + imgFile.getOriginalFilename();
        File file = new File(uploadDir + resultFileName);

        if (!file.exists()) {
            try {
                imgFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/" + category;
            }
        }

        Long orderId = null;
        DishRequestDTO dishRequestDTO = new DishRequestDTO(name, weight, composition, description,
                category, price, isPopular, resultFileName, orderId);
        dishService.createDish(dishRequestDTO);
        return "redirect:/admin/" + category;
    }

    @PostMapping("/admin/dish/edit/{dishId}")
    public String editDish(@PathVariable Long dishId,
                           @RequestParam("imgFile") MultipartFile imgFile,
                           @RequestParam String name, @RequestParam Integer weight,
                           @RequestParam Double price, @RequestParam String category,
                           @RequestParam String composition, @RequestParam String description,
                           @RequestParam(defaultValue = "false") boolean isPopular) {

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + imgFile.getOriginalFilename();
        File file = new File(uploadPath + resultFileName);

        if (!file.exists()) {
            try {
                imgFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/" + category;
            }
        }

        Long orderId = null;
        DishRequestDTO dishRequestDTO = new DishRequestDTO(name, weight, composition, description,
                category, price, isPopular, resultFileName, orderId);
        dishService.updateDishById(dishId, dishRequestDTO);
        return "redirect:/admin/" + category;
    }

    @GetMapping("/admin/dish/delete/{dishId}")
    public String addDish(@PathVariable Long dishId) {
        String category = dishService.getDishById(dishId).getCategory();
        dishService.deleteDishById(dishId);
        return "redirect:/admin/" + category;
    }
}
