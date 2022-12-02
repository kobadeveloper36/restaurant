package com.progect.ui.controllers.adminControllers;

import com.progect.ui.UiApplication;
import com.progect.ui.rest.dto.user.UserRequestDTO;
import com.progect.ui.rest.dto.user.UserResponseDTO;
import com.progect.ui.services.RegistrationService;
import com.progect.ui.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final String uploadPath;

    public UserController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
        String location = UiApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        this.uploadPath = location.substring(0, location.indexOf("file:")) + "/restaurant/uploads/img/users/";
    }

    @PostMapping("/admin/user/add")
    public String addUser(@RequestParam String userName, @RequestParam String userPhone,
                          @RequestParam String userEmail, @RequestParam String userAddress,
                          @RequestParam String userFlat, @RequestParam String userEntrance,
                          @RequestParam String userFloor, @RequestParam String userLogin,
                          @RequestParam String userPassword, @RequestParam String userRole,
                          @RequestParam("imgFile") MultipartFile imgFile) {
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
                return "redirect:/admin/users";
            }
        }
        registrationService.register(new UserRequestDTO(userName, userPhone, userEmail, userAddress, userFlat, userEntrance,
                userFloor, userLogin, userPassword, null, null, userRole, resultFileName));
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/user/edit/{userId}")
    public String editUser(@PathVariable Long userId, @RequestParam String userName,
                           @RequestParam String userPhone, @RequestParam String userEmail,
                           @RequestParam String userAddress, @RequestParam String userFlat,
                           @RequestParam String userEntrance, @RequestParam String userFloor,
                           @RequestParam String userLogin, @RequestParam String userPassword,
                           @RequestParam String userRole, @RequestParam("imgFile") MultipartFile imgFile) {
        UserResponseDTO user = userService.getUserById(userId);
        String resultFileName;
        if (!imgFile.getOriginalFilename().equals(user.getImgFile())) {

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            resultFileName = uuidFile + "." + imgFile.getOriginalFilename();
            Path newPath = Paths.get(uploadPath + resultFileName);
            File newFile = new File(newPath.toFile().getAbsolutePath());
            File oldFile = new File(uploadPath + user.getImgFile());
           oldFile.delete();

            if (!newFile.exists()) {
                try {
                    imgFile.transferTo(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/admin/users";
                }
            }
        } else {
            resultFileName = imgFile.getOriginalFilename();
        }
        registrationService.register(userId, new UserRequestDTO(userName, userPhone, userEmail, userAddress, userFlat,
                userEntrance, userFloor, userLogin, userPassword, null, null, userRole, resultFileName));
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/delete/{userId}")
    public String deleteOrder(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/users";
    }
}
