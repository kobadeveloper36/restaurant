package com.progect.ui.controllers;

import com.progect.ui.UiApplication;
import com.progect.ui.rest.dto.dish.DishResponseDTO;
import com.progect.ui.rest.dto.order.OrderResponseDTO;
import com.progect.ui.rest.dto.user.UserRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.security.UserDetailsImpl;
import com.progect.ui.services.MainService;
import com.progect.ui.services.OrderService;
import com.progect.ui.services.RegistrationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
public class AccountController {
    private final OrderService orderService;
    private final MainService mainService;
    private Set<String> categories;

    private final RegistrationService registrationService;

    private final String uploadPath;

    public AccountController(OrderService orderService, MainService mainService, RegistrationService registrationService) {
        this.mainService = mainService;
        this.orderService = orderService;
        this.categories = mainService.getCategoriesSet();
        this.registrationService = registrationService;
        String location = UiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        this.uploadPath = location.substring(0, location.indexOf("/build")) + "/uploads/img/";
    }

    @GetMapping("/account/orders-account")
    public String ordersAccount(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", categories);

        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        model.addAttribute("name", userResponseDTO.getName());
        model.addAttribute("userRole", userResponseDTO.getRole());
        List<OrderResponseDTO> orders = orderService.getOrdersById(userResponseDTO.getUserId());
        model.addAttribute("orders", orders);
        return "account/orders-account";
    }

    @GetMapping("/account/orders-account/order-info/{orderId}")
    public String orderInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @PathVariable Long orderId, Model model) {
        model.addAttribute("categories", categories);

        UserResponseDTO userResponseDTO = userDetails.getUserResponseDTO();
        model.addAttribute("name", userResponseDTO.getName());
        model.addAttribute("userRole", userResponseDTO.getRole());
        OrderResponseDTO order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<DishResponseDTO> dishes = orderService.getOrderDishes(order);
        model.addAttribute("dishes", dishes);
        return "account/order-info";
    }

    @GetMapping("/account/info")
    public String info(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("categories", categories);

        UserResponseDTO user = userDetails.getUserResponseDTO();
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("name", user.getName());
        model.addAttribute("userPhone", user.getPhone());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userAddress", user.getAddress());
        model.addAttribute("userFlat", user.getFlat());
        model.addAttribute("userEntrance", user.getEntry());
        model.addAttribute("userFloor", user.getFloor());
        model.addAttribute("userLogin", user.getLogin());
        model.addAttribute("actionPath", "/account/user/edit");
        model.addAttribute("userRole", user.getRole());
        String imgFile = user.getImgFile();
        if (imgFile.equals("user.png")) {
            model.addAttribute("userImage", "user.png");
        } else {
            String path = uploadPath.substring(1) + "users/" + imgFile;
            if (new File(path).exists()) {
                model.addAttribute("userImage", imgFile);
            } else {
                model.addAttribute("userImage", "user.png");
            }
        }

        return "account/info-account";
    }

    @PostMapping("/account/user/edit")
    public String editUser(@AuthenticationPrincipal UserDetailsImpl userDetail,
                           @RequestParam String userName, @RequestParam String userPhone,
                           @RequestParam String userEmail, @RequestParam String userAddress,
                           @RequestParam String userFlat, @RequestParam String userEntrance,
                           @RequestParam String userFloor, @RequestParam String userLogin,
                           @RequestParam String userPassword, @RequestParam("imgFile") MultipartFile imgFile) {
        UserResponseDTO user = userDetail.getUserResponseDTO();
        String resultFileName;
        if (!imgFile.getOriginalFilename().equals(user.getImgFile())) {

            File uploadDir = new File(uploadPath + "users/");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            resultFileName = uuidFile + "." + imgFile.getOriginalFilename();
            File newFile = new File(uploadPath + "users/" + resultFileName);

            File oldFile = new File(uploadPath + "users/" + user.getImgFile());
            oldFile.delete();

            if (!newFile.exists()) {
                try {
                    imgFile.transferTo(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/account";
                }
            }
        } else {
            resultFileName = imgFile.getOriginalFilename();
        }
        userDetail.setUserResponseDTO(registrationService.register(user.getUserId(),
                new UserRequestDTO(userName, userPhone, userEmail, userAddress, userFlat, userEntrance, userFloor,
                        userLogin, userPassword, null, null, user.getRole(), resultFileName)));
        return "redirect:/account";
    }
}
